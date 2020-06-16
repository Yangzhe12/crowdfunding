package xyz.yangzhe.crowd.service.api;

import com.github.pagehelper.PageInfo;
import xyz.yangzhe.crowd.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByUsername(String username, String password);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void remove(Integer adminId);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

}
