package xyz.yangzhe.crowd.service.api;

import xyz.yangzhe.crowd.entity.Authority;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Authority> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelathinship(Map<String, List<Integer>> map);
}
