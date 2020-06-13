package xyz.yangzhe.crowd.service.api;

import xyz.yangzhe.crowd.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);
    List<Admin> getAll();

    Admin getAdminByUsername(String username, String password);
}
