package xyz.yangzhe.crowd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.yangzhe.crowd.constant.CrowdConstant;
import xyz.yangzhe.crowd.entity.Admin;
import xyz.yangzhe.crowd.entity.AdminExample;
import xyz.yangzhe.crowd.entity.AdminExample.Criteria;
import xyz.yangzhe.crowd.exception.LoginFailedException;
import xyz.yangzhe.crowd.mapper.AdminMapper;
import xyz.yangzhe.crowd.service.api.AdminService;
import xyz.yangzhe.crowd.util.CrowdUtil;

import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @Author: Yangzhe
 * @Data: 2020/6/12
 */

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    public Admin getAdminByUsername(String username, String password){
        // 1. 根据用户名查询Admin对象
        // 1.1 创建AdminExample对象
        AdminExample adminExample = new AdminExample();

        // 1.2 创建Criteria对象
        Criteria criteria = adminExample.createCriteria();

        // 1.3 在Criteria中封装查询条件
        criteria.andLoginAcctEqualTo(username);

        // 1.4 调用AdminMapper的方法进行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);

        // 2. 判断Admin对象是否为空
        if (list == null || list.size() == 0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 如果list中有多余一个的对象，也是错误的，抛出异常
        if (list.size() > 1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = list.get(0);

        // 3. 如果admin对象为空，则抛出异常
        if (admin == null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4. 如果admin不为null，则对比用户传入密码和数据库密码，首先获取数据库密码
        String passwordDB = admin.getUserPswd();

        // 5. 将表单提交的明文密码加密
        String passwordForm = CrowdUtil.md5(password);

        // 6. 对密码进行比较
        if(!Objects.equals(passwordDB, passwordForm)){
            // 7. 如果比较结果是不一致，则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 8. 如果一致，则返回Admin对象
        return admin;
    }
}
