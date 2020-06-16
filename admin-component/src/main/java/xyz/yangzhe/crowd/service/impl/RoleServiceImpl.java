package xyz.yangzhe.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.javaws.IconUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.yangzhe.crowd.entity.Role;
import xyz.yangzhe.crowd.entity.RoleExample;
import xyz.yangzhe.crowd.mapper.RoleMapper;
import xyz.yangzhe.crowd.service.api.RoleService;
import xyz.yangzhe.crowd.util.ResultEntity;

import java.util.List;

/**
 * @Description: 接口RoleService的实现类
 * @Author: Yangzhe
 * @Data: 2020/6/15
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 1. 开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        // 2. 执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);

        // 3. 封装为PageInfo对象返回
        return new PageInfo<>(roleList);
    }

    @Override
    public void saveRole(Role role) {
        // 1. 调用mapper中的插入方法，保存角色数据
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRole(List<Integer> roleIdList) {
        RoleExample example = new RoleExample();

        RoleExample.Criteria criteria = example.createCriteria();

        //delete from role where id in (5,8,12)
        criteria.andIdIn(roleIdList);

        roleMapper.deleteByExample(example);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }
}
