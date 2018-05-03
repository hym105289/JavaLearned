[TOC]



# 一、IOC 和 DI

1、什么是 IOC？

Inversion of Controll 控制反转，对象之间的关系由容器来建立。

2、什么是 DI？

Dependency Injection 依赖注入。

**容器可以通过调用 setter 或者构造器来建立对象之间的依赖关系。**

**IOC 是目标，DI 是方法。**



# 二、依赖注入的两种方式

## 2.1 set 方法注入

### 2.1.1 步骤

1、添加 set 方法；

2、在配置文件中使用 <property> 元素来配置。



### 2.1.2 案例实现

A a = new A();  a.fun()，fun()是 B类的方法。

1、spring 配置文件：

```xml
<bean id="c" class="ioc.C"></bean>

<bean id="a" class="ioc.A">
    <property name="b" ref="c"></property>
</bean>
```

property：让容器调用 setter 方法来注入依赖关系。

其中 name属性指定属性名（也是 setter 方法除 set 外首字母小写的那个！）；

ref 属性指定被注入 bean 的id（也就是 setter 方法需要传入的参数！）



2、B接口：

```java
package ioc;
public interface B {
    public void funB();
}
```

3、C 类实现 B 接口：

```java
package ioc;

public class C implements B {
    @Override
    public void funB() {
        System.out.println("C里的 funB 方法");
    }
}
```

4、A类：

```java
public class A {
    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public void execute(){
        b.fun();
    }
}
```

5、测试方法：

```java
@Test
public void test3(){
    String config = "second.xml";
    ApplicationContext ac= new ClassPathXmlApplicationContext(config);
    A a = ac.getBean("a",A.class);
    a.execute();
}
```



## 2.2 构造器注入

### 2.1.1 步骤

1、添加构造器；

2、在配置文件中使用 <constructor-arg> 元素来配置。



### 2.1.2 案例实现

配置文件：

```xml
<bean id="water" class="ioc.Waiter"></bean>
<bean id="restaurant" class="ioc.Restaurant">
    <constructor-arg index="0" ref="water"></constructor-arg>
</bean>
```

​	constructor-arg 容器会采用构造器来建立依赖关系，其中 index 指定参数的下标。

Restaurant 类添加构造器：

```java
package ioc;
public class Restaurant {
    private Waiter waiter;
    public Restaurant() {
    }

    public Restaurant(Waiter waiter) {
        this.waiter = waiter;
    }
    public void execute(){
        waiter.executWaiter();
    }
}
```



## 2.3 自动装配

​	自动装配：容器依据某些规则，自动建立对象之间的依赖关系

1、默认情况下，容器不会自动装配；

2、设置 autowire 属性，byName 依据属性名查找对应的 bean。

配置文件：

```xml
<bean id="water" class="ioc.Waiter"></bean>
<bean id="restaurant" class="ioc.Restaurant">
	<autowire="byName"></autowire>
</bean>
```

​	autowire 容器会自动建立对象之间的依赖关系。byName 容器会依据属性名，逐一查找spring 配置文件里的 bean（就是根据属性名作为 bean 的 id去查找，找到后调用 setter 方法来建立依赖关系，如果找不到则不注入）。如 Restaurant里有属性 waiter，然后属性名作为 bean 的 id 去配置文件里查找，查到后调用setter 方法来建立依赖。

​	类似 byName，还有 byteType，容器会依据属性类型查找对应的 bean，找到之后调用 setter 方法来建立依赖关系。



# 三、注入基本类型的值

​	使用 value 属性来注入，spring 容器会帮忙做一些类型的转换工作，如下面将 age 由String 类型转为 int 类型。

- 在类中定义好属性，并设置好 setter 方法，然后在配置文件中配置。

Engineer 类：**先调用无参构造器创建对象，然后调用 setter 方法将值注入。**

```java
package ioc;
public class Engineer {
    private String name;
    private int age;
    public Engineer() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        System.out.println(" Engineer toString 方法");
        return "Engineer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

配置文件中配置：

```xml
<bean id="engineer" class="ioc.Engineer">
    <property name="name" value="金花"></property>
    <property name="age" value="18"></property>
</bean>
```

​	name 是属性名，value 是要注入的值。

# 四、注入集合类型的值List/Set/Map/Properties

## 4.1 直接注入

如 Engineer 类增加属性：

```java
package ioc;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Engineer {
    private String name;
    private int age;
    private List<String> interests;
    private Set<String> cities;
    private Map<String,Double> score;
    private Properties db;

    public Properties getDb() {
        return db;
    }

    public void setDb(Properties db) {
        this.db = db;
    }

    public Map<String, Double> getScore() {
        return score;
    }

    public void setScore(Map<String, Double> score) {
        this.score = score;
    }

    public Set<String> getCities() {
        return cities;
    }

    public void setCities(Set<String> cities) {
        this.cities = cities;
    }

    public Engineer() {
        System.out.println("Engineer 构造方法");
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Engineer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", interests=" + interests +
                ", cities=" + cities +
                ", score=" + score +
                ", db=" + db +
                '}';
    }
}
```

为注入集合类型的数据的配置如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="engineer" class="ioc.Engineer">
        <property name="name" value="金花"></property>
        <property name="age" value="18"></property>
        <property name="interests">
            <list>
                <value>钓鱼</value>
                <value>看书</value>
                <value>打球</value>
                <value>打球</value>
            </list>
        </property>
        <property name="cities">
            <set>
                <value>北京</value>
                <value>杭州</value>
                <value>上海</value>
                <value>上海</value>
            </set>
        </property>
        <property name="score">
            <map>
                <entry key="语文" value="100"></entry>
                <entry key="语文" value="100"></entry>
                <entry key="数学" value="99"></entry>
                <entry key="英语" value="80"></entry>
            </map>
        </property>
        <property name="db">
            <props>
                <prop key="username">梦佳</prop>
                <prop key="password">1234</prop>
            </props>
        </property>
    </bean>
</beans>
```

## 4.2 引用的方式注入

步骤：

1、将集合类型的值先配置成一个 bean；

2、再将这个 bean 注入到对应的 bean 里。

Student 类：

```java
package ioc;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by jinhua.chen on 2018/4/29.
 */
public class Student {
    private List<String> interest;
    private Set<String> city;
    private Map<String,Double> score;
    private Properties db;

    public Student() {}

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public Set<String> getCity() {
        return city;
    }

    public void setCity(Set<String> city) {
        this.city = city;
    }

    public Map<String, Double> getScore() {
        return score;
    }

    public void setScore(Map<String, Double> score) {
        this.score = score;
    }

    public Properties getDb() {
        return db;
    }

    public void setDb(Properties db) {
        this.db = db;
    }

    @Override
    public String toString() {
        return "Student{" +
                "interest=" + interest +
                ", city=" + city +
                ", score=" + score +
                ", db=" + db +
                '}';
    }
}
```

spring 配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">

    <util:list id="interestBean">
        <value>喝酒</value>
        <value>抽烟</value>
        <value>打架</value>
    </util:list>
    <util:set id="cityBean">
        <value>北京</value>
        <value>杭州</value>
        <value>上海</value>
        <value>上海</value>
    </util:set>
    <util:map id="scoreBean">
        <entry key="语文" value="100"></entry>
        <entry key="语文" value="100"></entry>
        <entry key="数学" value="99"></entry>
        <entry key="英语" value="80"></entry>
    </util:map>
    <util:properties id="dbBean">
            <prop key="username">梦佳</prop>
            <prop key="password">1234</prop>
    </util:properties>
    <bean id="student" class="ioc.Student">
        <property name="interest" ref="interestBean"></property>
        <property name="city" ref="cityBean"></property>
        <property name="score" ref="scoreBean"></property>
        <property name="db" ref="dbBean"></property>
    </bean>
</beans>
```

​	util:list 表示使用的是util 命令空间（为了区分同名元素而添加的前缀）下的 list 元素。

### 4.2.1 补充：Spring 容器读取 Properties 文件内容

1、在 resources 目录下增加 db.properties 文件：

```
pageSize=10
```

2、设置 sprint 配置文件：

```xml
<util:properties id="properties" location="classpath:db.properties"></util:properties>
```

id： 为 bean 的 id 可随意填写，只要唯一；

location：指定文件的位置，classpath 表示让容器依据类路径去查找文件，容器会读取指定位置文件的内容，并将这些内容存放到 Properties 对象里！

3、获取 Properties 对象

```java
public void test4(){
    String config = "second.xml";
    ApplicationContext ac = new ClassPathXmlApplicationContext(config);
    Properties prop = ac.getBean("properties",Properties.class);
    System.out.println(prop);
}
```

# 五、spring 表达式

作用：读取 bean 的属性。

```xml
<!--使用 spring 表达式读取其他 bean 的属性-->
    <bean id="student" class="ioc.Student">
        <!--1.读取其他 bean 的属性之基本类型、List、Set、Map集合-->
        <property name="name" value="#{engineer.name}"></property>
        <property name="interest" value="#{engineer.interests[1]}"></property>
        <property name="scores" value="#{engineer.score['English']}"></property>
        <!--2. 读取其他bean 的属性之Properties 集合---->
        <property name="pageSize" value="#{propertiesBean.pageSize}">
        </property>
    </bean>

    <util:properties id="propertiesBean" location="classpath:db.properties"></util:properties>

    <bean id="engineer" class="ioc.Engineer">
        <property name="name" value="金花"></property>
        <property name="age" value="18"></property>
        <property name="interests">
            <list>
                <value>钓鱼</value>
                <value>看书</value>
                <value>打球</value>
                <value>打球</value>
            </list>
        </property>
        <property name="cities">
            <set>
                <value>北京</value>
                <value>杭州</value>
                <value>上海</value>
                <value>上海</value>
            </set>
        </property>
        <property name="score">
            <map>
                <entry key="语文" value="100"></entry>
                <entry key="语文" value="100"></entry>
                <entry key="数学" value="99"></entry>
                <entry key="English" value="80"></entry>
            </map>
        </property>
        <property name="db">
            <props>
                <prop key="username">梦佳</prop>
                <prop key="password">1234</prop>
            </props>
        </property>
    </bean>

</beans>
```



# 六、使用注解来简化配置文件

## 6.1 什么是组件扫描

​	容器会扫描指定的包及其子包下面所有的类，如果该类类含有特定的注解如@Component，则容器会将其纳入容器进行管理（相当于在配置文件里有一个 bean 元素）。



## 6.2 如何进行组件扫描

步骤：

1、在配置文件中添加配置扫描

```xml
<context:component-scan base-package="ioc"></context:component-scan>
```

​	base-package 指定要扫描的包。

2、在类前增加特定的注解，如@Component，默认的 id 是类名首字母小写之后的名字，如果需要重新设置 id，则在注解后面操作如：@Component("stu1")。



## 6.3 自动扫描的注解标记

​	只有在组件类定义前面有以下**注解标记**时，才会扫描到 Spring 容器：

@Component、@Repository、@Service、@Controller。

```java
@Component("stu1")  	// 自定义 id 名
@Scope("prototype")	    // 修改作用域
@Lazy(true) 		   //  延迟加载
public class ExampleBean {
    public ExampleBean(){}

    @PostConstruct     // 设置初始化方法
    public void init(){
        System.out.println("init()");
    }
    @PreDestroy        // 设置销毁方法
    public void destory(){
        System.out.println("destory()");
    }
}
```



## 6.4 依赖注入相关的注解

​	具有依赖关系的bean 对象，利用下面任一种注解都可以实现依赖注入。

- @AutoWired/@Qualifier：可以处理构造器注入和 setter 注入；


- **@Resource**：只能处理 setter 注入，但大部分都是 setter 注入。

所以：setter 注入推荐使用 @Resource 注解；构造器注入推荐使用 @AutoWired/@Qualifier 注解。



### 6.4.1 @AutoWired/@Qualifier实现依赖注入

#### 6.4.1.1 setter 方法注入

```java
@Component("rest")
public class Restaurant {
    private Waiter wt;

    public Waiter getWt() {
        return wt;
    }
    @Autowired
    public void setWt(@Qualifier("wt") Waiter wt) {  // wt 是要注入的 bean 的 id。
        this.wt = wt;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "wt=" + wt +
                '}';
    }
}
```

也可以这样写：将这两个注解添加到属性前，等价于加在 setter 方法前。二者区别是前者除了赋值操作（this.wt = wt），还可以做其他操作。

```java

@Component("rest")
public class Restaurant {
    @Autowired
    @Qualifier("wt") // wt 是要注入的 bean 的 id。此种方法不需要setter 方法。
    private Waiter wt;

    public Waiter getWt() {
        return wt;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "wt=" + wt +
                '}';
    }
}
```

#### 6.4.1.2 构造器注入

```java
package ioc;

@Component("bar")
public class Bar {
    private Waiter waiter;

    public Bar() {
    }
    @Autowired
    public Bar(@Qualifier("wt") Waiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "waiter=" + waiter +
                '}';
    }
}
```



### 6.4.2 @Resource 实现依赖注入

```java
package ioc;

@Component("leader")
public class Leader {
    private Waiter wat;

    public Waiter getWat() {
        return wat;
    }

    @Resource(name = "wt")  // name 属性值是被注入的 bean 的 id.
    public void setWat(Waiter wat) {
        this.wat = wat;
    }

    @Override
    public String toString() {
        return "Leader{" +
                "wat=" + wat +
                '}';
    }
}
```

类似的也可以将注解加到属性前：

```java
package ioc;

@Component("leader")
public class Leader {
    @Resource(name = "wt")
    private Waiter wat;

    public Waiter getWat() {
        return wat;
    }

//    @Resource(name = "wt")  // name 属性值是被注入的 bean 的 id.
//    public void setWat(Waiter wat) {
//        this.wat = wat;
//    }

    @Override
    public String toString() {
        return "Leader{" +
                "wat=" + wat +
                '}';
    }
}
```



## 6.5 注入基本类型的值、spring 表达式值

```java
package ioc;

@Component("user")
public class UserBean {
    @Value("安迪")
    private String name;
    @Value("#{dbProp.pageSize}")
    private int pageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", pageSize=" + pageSize +
                '}';
    }
}
```

配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="ioc"></context:component-scan>
    <!--读取属性文件-->
    <util:properties id="dbProp" location="classpath:db.properties"></util:properties>

</beans>
```