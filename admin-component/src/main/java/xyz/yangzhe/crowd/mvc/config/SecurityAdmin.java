package xyz.yangzhe.crowd.mvc.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import xyz.yangzhe.crowd.entity.Admin;

import java.util.List;

/**
 * @Description: 考虑到User对象中仅仅包含账号和密码，为了能够获取到原始的Admin对象，专门创建这个类对User类进行扩展
 * @Author: Yangzhe
 * @Data: 2020/6/18
 */
public class SecurityAdmin extends User {

    private static final long serialVersionUID = 1L;

    // 原始的Admin对象，包含Admin对象的全部属性
    private Admin originalAdmin;

    /**
     * @Description:
     * @Param:
     *      Admin originalAdmin : 传入原始的Admin对象
     *      List<GrantedAuthority> authorities : 创建角色、权限信息的集合
     */
    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        // 调用父类构造器
        super(originalAdmin.getUsername(), originalAdmin.getPassword(), authorities);
        // 给本类的this.originalAdmin赋值
        this.originalAdmin = originalAdmin;
        // 将原始Admin对象中的密码擦除
        this.originalAdmin.setPassword(null);
    }

    // 对外提供的获取原始Admin对象的getXxx()方法
    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

    public void setOriginalAdmin(Admin originalAdmin) {
        this.originalAdmin = originalAdmin;
    }
}
