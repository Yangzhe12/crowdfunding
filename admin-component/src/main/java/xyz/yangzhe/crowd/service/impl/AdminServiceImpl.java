package xyz.yangzhe.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import xyz.yangzhe.crowd.constant.CrowdConstant;
import xyz.yangzhe.crowd.entity.Admin;
import xyz.yangzhe.crowd.entity.AdminExample;
import xyz.yangzhe.crowd.entity.AdminExample.Criteria;
import xyz.yangzhe.crowd.exception.LoginAcctAlreadyInUseException;
import xyz.yangzhe.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import xyz.yangzhe.crowd.exception.LoginFailedException;
import xyz.yangzhe.crowd.mapper.AdminMapper;
import xyz.yangzhe.crowd.service.api.AdminService;
import xyz.yangzhe.crowd.util.CrowdUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public void saveAdmin(Admin admin) {
        // 1. 密码加密
        String password = admin.getUserName();
        System.out.println("____________________________");
        System.out.println(admin);
        password = CrowdUtil.md5(password);
        admin.setUserPswd(password);

        // 2. 生成创建时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);

        // 3. 执行保存
        try{
            adminMapper.insert(admin);

        } catch (Exception e){
            e.printStackTrace();
            logger.info("异常全类名：" + e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }

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

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1. 调用PageHelper的静态方法开启分页功能
        // 这里充分体现了PageHelper的“非侵入式”设计：原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum, pageSize);

        // 2. 执行查询
        List<Admin> list = adminMapper.selectAdminByKeyword(keyword);

        // 3. 封装到PageInfo对象中
        return new PageInfo<>(list);
    }

    /**
     * @Description: 通过用户ID来获取admin的信息
     * @Param: adminId 用户信息的ID
     * @Return:  Admin对象
     */
    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        // “Selective”表示有选择的更新，对于null值的字段不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e){
            e.printStackTrace();
            logger.info("异常全类名：" + e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    /**
     * @Description: 根据id删除单条数据
     * @Param:
     * @Return:
     */
    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }
}
