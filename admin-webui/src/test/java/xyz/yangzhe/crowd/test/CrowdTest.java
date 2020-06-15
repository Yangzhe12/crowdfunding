package xyz.yangzhe.crowd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.yangzhe.crowd.entity.Admin;
import xyz.yangzhe.crowd.mapper.AdminMapper;
import xyz.yangzhe.crowd.service.api.AdminService;
import xyz.yangzhe.crowd.util.CrowdUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author: Yangzhe
 * @Data: 2020/6/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testDataSources() throws SQLException{
        // 1. 通过数据源对象获取数据源连接
        Connection connection = dataSource.getConnection();

        // 2. 打印数据连接
        System.out.println("---------------" + connection + "-------------");
    }

    @Test
    public void testTx() {
        String password = CrowdUtil.md5("2012");
        Admin admin = new Admin(null, "admin", password, "汤姆", "tom@163.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testAdminAutowired(){
        List<Admin> admins = adminService.getAll();
        System.out.println(admins);
    }

    @Test
    public void addTestUserData() {
        for(int i = 0; i < 238; i++) {
            String password = "userPswd" + i;
            password = CrowdUtil.md5(password);
            adminMapper.insert(new Admin(null, "loginAcct"+i, password, "userName"+i, "email"+i, null));
        }
    }
}
