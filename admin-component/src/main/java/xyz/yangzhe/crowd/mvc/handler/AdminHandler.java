package xyz.yangzhe.crowd.mvc.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.yangzhe.crowd.constant.CrowdConstant;
import xyz.yangzhe.crowd.entity.Admin;
import xyz.yangzhe.crowd.service.api.AdminService;

import javax.servlet.http.HttpSession;

/**
 * @Description: 处理admin相关的handler
 * @Author: Yangzhe
 * @Data: 2020/6/12
 */
@Controller
public class AdminHandler {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/admin/do/login.html", method = RequestMethod.POST)
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpSession session){
        // 1. 调用Service方法执行登录检查
        // 这个方法如果能够返回admin对象说明登录成功，如果账号、密码不正确则会抛出异常
        Admin admin = adminService.getAdminByUsername(username, password);

        // 2. 将登陆成功返回的admin对象存入session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session){
        // 强制Session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
}
