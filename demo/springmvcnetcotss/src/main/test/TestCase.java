import dao.AdminDao;
import dao.AdminDaoJDBCImpl;
import entity.Admin;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.LoginService;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by jinhua.chen on 2018/5/1.
 */
public class TestCase {
    /* 测试连接池 */
    @Test
    public void test() throws SQLException {
        String config = "spring-mvc.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        DataSource ds = ac.getBean("ds",DataSource.class);
        System.out.println("conn :" + ds.getConnection());
    }

    /* 测试持久层 */
    @Test
    public void test2() throws SQLException {
        String config = "spring-mvc.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        AdminDao adminDao = ac.getBean("adminDao",AdminDao.class);
        Admin admin = adminDao.findByName("zh64");
        System.out.println(admin);
    }

    /* 测试业务层 */
    @Test
    public void test3() throws SQLException {
        String config = "spring-mvc.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        LoginService loginService = ac.getBean("loginService",LoginService.class);
        Admin admin = loginService.checkLogin("mjjzh64","041115");
        System.out.println(admin);
    }
}
