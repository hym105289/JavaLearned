[TOC]

# 一、系统分层

1、为什么要分层？

​	为了系统好维护，系统的设计应该要做到 ”高内聚，低耦合“。

​	高内聚：指的是类的职责要单一，这样一个类就可以拆分成多个类，这样就形成了分层的概念；

​	低耦合：指的是类与类之间不要直接依赖，应该使用依赖注入。

2、如何分层？

- 表示层：数据展现和操作界面，以及请求分发；
- 业务层：封装了业务逻辑；
- 持久层：封装了数据访问逻辑。

表示层调用业务层，业务层调用持久层。上一层通过接口来调用下一层提供的服务（这样下一层的实现发生了改变，不影响上一层）。



# 二、用分层思想实现登录功能



![ 系统分层-登](/Users/chenjinhua/ziliao/JavaLearned/doc/45 Spring/6 系统分层-登录.png)



## 2.1 写连接池

- 引入2个 jar：jdbc 和 BasicDataSource
- 2个配置文件设置

db.properties 同之前；spring-mvc.xml 配置：

```xml
<!-- 配置连接池-->
<util:properties id="db" location="classpath:db.properties"></util:properties>

<bean id="ds" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="#{db.driver}"></property>
    <property name="url" value="#{db.url}"></property>
    <property name="username" value="#{db.username}"></property>
    <property name="password" value="#{db.password}"></property>
    <property name="initialSize" value="#{db.initSize}"></property>
</bean>
```

测试连接池

```java
@Test
public void test() throws SQLException {
    String config = "spring-mvc.xml";
    ApplicationContext ac = new ClassPathXmlApplicationContext(config);
    DataSource ds = ac.getBean("ds",DataSource.class);
    
    Connection conn = ds.getConnection();
    System.out.println("conn: " + conn);
}
```



## 2.2 持久层实现

写 entity 、dao（AdminDao、AdminDaoJdbcImpl）

AdminDao 接口：

```java
public interface AdminDao {
    public Admin findByLoginName(String loginName);
}
```

AdminDaoJdbcImpl 

// 持久层用注解,容器会先创建该对象,再注入依赖.

```java
@Repository("adminDao")

// 持久层用如上的注解,容器会先创建该对象,再注入依赖.
public class AdminDaoJdbcImpl implements Serializable,AdminDao{

    @Resource(name = "ds")
    private DataSource ds;

    public Admin findByLoginName(String loginName){
        Connection conn = null;
        Admin admin = null;
        try {
            conn = ds.getConnection();
            String sqlFind = "select * from account where login_name = ?";
            PreparedStatement ps = conn.prepareStatement(sqlFind);
            ps.setString(1,loginName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                admin = new Admin();
                admin.setId(rs.getInt("account_id"));
                admin.setRecommenderId(rs.getInt("recommender_id"));

                admin.setLoginName(rs.getString("login_name"));
                admin.setLoginPasswd(rs.getString("login_passwd"));
                admin.setStatus(rs.getString("status"));
                admin.setPauseDate(rs.getTimestamp("pause_date"));
                admin.setCloseDate(rs.getTimestamp("close_date"));
                admin.setRealName(rs.getString("real_name"));
                return admin;
            }
        } catch (SQLException e) {
            // 1. 记日志(保留现场)
            e.printStackTrace();
            // 2. 看异常能否恢复,若不能恢复则上层需要提示用户稍后重试,所以下层要抛出异常.
            throw new RuntimeException(e);
        }finally {
            if (conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return admin;
    }
}
```

测试方法：

```java
/* 测试持久层 */
@Test
public void test() throws SQLException {
    String config = "spring-mvc.xml";
    ApplicationContext ac = new ClassPathXmlApplicationContext(config);
    AdminDao aDao = ac.getBean("adminDao", AdminDao.class);
    Admin a = aDao.findByLoginName("taiji001");
    System.out.println( a.getLoginName() + ", "+ a.getRealName());
}
```



## 2.3 业务层实现

LoginService 接口：

```java
public interface LoginService {
    public Admin checkLogin(String name,String pwd);
}
```

LoginServiceImpl 类：

```java
@Service("loginService")
public class LoginServiceImpl implements LoginService{

    @Resource(name = "adminDao")
    private AdminDao aDao;

    @Override
    public Admin checkLogin(String name, String pwd) {
        Admin admin = aDao.findByLoginName(name);
        if (admin == null){
            
            // 账号不存在,抛一个应用异常(自定义异常).应用异常指的是因为用户不正确的操作而引起的异常(如账号\密码错误),需要上层明确提示用户正确操作.
            throw new ApplicationException("账号不存在");
        }
        if (!admin.getLoginPasswd().equals(pwd)){
            throw new ApplicationException("密码错误");
        }

        // 账号密码正确,登录成功
        return admin;
    }
}
```

自定义异常类（在业务层类所在的包定义）：

```java
public class ApplicationException extends RuntimeException {
    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }
}
```



## 2.4 表示层实现

LoginController 类

```java
@Controller
public class LoginController {

    @Resource(name = "loginService")
    private LoginService loginService;

    @Resource(name = "adminParam")
    private AdminParam adminParam;

    @RequestMapping("/toLogin.do")
    public String toLogin(ModelMap mm){

        // 获取请求参数
        String name = adminParam.getUsername();
        String pwd = adminParam.getPassword();

        // 要将此行放入 try catch 里,因为持久层\服务层都向上抛出异常了,在此处需要处理.
        try{
            Admin admin = loginService.checkLogin(name,pwd);
            return "index";
        }catch (Exception e){
            // 应用异常
            if (e instanceof ApplicationException){
                // 向页面传值
                mm.addAttribute("error",e.getMessage());  // getMessage()获取抛出的异常内容.
                return "login";
            }else {
                // 系统异常,提示用户重试
                return "error";
            }
        }
    }
}

```

​	注意：表示层要处理异常

```java
// 要将此行放入 try catch 里,因为持久层\服务层都向上抛出异常了,在此处需要处理.
        try{
            Admin admin = loginService.checkLogin(name,pwd);
            return "index";
        }catch (Exception e){
            // 应用异常
            if (e instanceof ApplicationException){
                // 向页面传值
                mm.addAttribute("error",e.getMessage());  // getMessage()获取抛出的异常内容.
                return "login";
            }else {
                // 系统异常,提示用户重试
                return "error";
            }
```


## 2.5 总结

![ 系统分](/Users/chenjinhua/ziliao/JavaLearned/doc/45 Spring/7 系统分层.png)