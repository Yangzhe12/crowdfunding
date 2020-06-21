package xyz.yangzhe.crowd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.yangzhe.crowd.entity.Admin;
import xyz.yangzhe.crowd.entity.Role;
import xyz.yangzhe.crowd.mapper.AdminMapper;
import xyz.yangzhe.crowd.service.api.AdminService;
import xyz.yangzhe.crowd.service.api.RoleService;
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

    @Autowired
    private RoleService roleService;

    @Test
    public void testDataSources() throws SQLException{
        // 1. 通过数据源对象获取数据源连接
        Connection connection = dataSource.getConnection();

        // 2. 打印数据连接
        System.out.println("---------------" + connection + "-------------");
    }

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "admin", "2012", "汤姆", "tom@163.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testAddRole(){
        Role role = new Role(null, "超级管理员");
        roleService.saveRole(role);
    }

    @Test
    public void addTestRoleData(){
        for(int i = 0; i < 238; i++) {
            roleService.saveRole(new Role(null, "role"+i));
        }
    }

    @Test
    public void testAdminAutowired(){
        List<Admin> admins = adminService.getAll();
        System.out.println(admins);
    }

    @Test
    public void addTestUserData() {
        for(int i = 0; i < 238; i++) {
            adminMapper.insert(new Admin(null, "loginAcct"+i, "userPswd" + i, "userName"+i, "email"+i, null));
        }
    }




}
