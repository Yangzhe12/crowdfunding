package xyz.yangzhe.crowd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.yangzhe.crowd.entity.Authority;
import xyz.yangzhe.crowd.entity.AuthorityExample;
import xyz.yangzhe.crowd.mapper.AuthorityMapper;
import xyz.yangzhe.crowd.service.api.AuthService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 权限相关服务
 * @Author: Yangzhe
 * @Data: 2020/6/16
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthorityMapper authorityMapper;
    @Override
    public List<Authority> getAll() {
        return authorityMapper.selectByExample(new AuthorityExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authorityMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelathinship(Map<String, List<Integer>> map) {
        // 1.获取roleId的值
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);

        // 2.删除旧关联关系数据
        authorityMapper.deleteOldRelationship(roleId);

        // 3.获取authIdList
        List<Integer> authIdList = map.get("authIdArray");

        // 4.判断authIdList是否有效
        if(authIdList != null && authIdList.size() > 0) {
            authorityMapper.insertNewRelationship(roleId, authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authorityMapper.selectAssignedAuthNameByAdminId(adminId);
    }


}
