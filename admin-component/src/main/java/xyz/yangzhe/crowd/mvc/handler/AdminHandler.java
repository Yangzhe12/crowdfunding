package xyz.yangzhe.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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
    private UserDetailsService userDetailsService;

    @Autowired
    private AdminService adminService;

    /**
     * @Description: 处理登陆请求,使用SpringSecurity后不再使用该处理器
     */
    @Deprecated
    @RequestMapping(value = "/admin/do/login.html", method = RequestMethod.POST)
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpSession session) {
        // 1. 调用Service方法执行登录检查
        // 这个方法如果能够返回admin对象说明登录成功，如果账号、密码不正确则会抛出异常
        Admin admin = adminService.getAdminByUsername(username, password);

        // 2. 将登陆成功返回的admin对象存入session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

        return "redirect:/admin/to/main/page.html";
    }

    /**
     * @Description: 处理退出登陆请求
     */
    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
        System.out.println("===================================");
        System.out.println("===================================");
        // 强制Session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    /**
     * @Description: 用户管理页面handler
     * @Param: keyword: 关键字
     * pageNum: pageNum默认值使用1
     * pageSize: pageSize默认值使用5
     * @Return:
     */
    @PreAuthorize("hasAuthority('user:get')")
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ) {
        // 1. 调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 2. 将PageInfo对象存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);

        return "admin-page";
    }

    /**
     * @Description: 添加用户handler
     */
    @PreAuthorize("hasAuthority('user:save') OR hasRole('管理员')")
    @RequestMapping(value = "/admin/save.html", method = RequestMethod.POST)
    public String saveAdmin(Admin admin) {
        // 1. 保存
        adminService.saveAdmin(admin);
        // 2. 重定向到用户管理页面最后一页,使用重定向是为了避免刷新浏览器重复提交表单
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    /**
     * @Description: 跳转到用户信息编辑页面的handler
     */
    @RequestMapping(value = "/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
        // 1. 根据id（主键）查询待更新的Admin对象
        Admin admin = adminService.getAdminById(adminId);
        // 2. 将Admin对象存入模型
        modelMap.addAttribute("admin", admin);

        return "admin-edit";
    }

    /**
     * @Description: 更新编辑好的用户信息
     * @Param: admin: 修改后的admin
     * pageNum: 点击“编辑”按钮时，所在列表的页码数
     * keyword: 点击“编辑”按钮时，查询的关键字
     * @Return: 页面名
     */
    @RequestMapping(value = "/admin/update.html")
    public String updateUserInfo(Admin admin, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword) {
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }


    /**
     * @Description: 删除单条用户信息
     */
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
    ) {
        // 1. 执行删除
        adminService.remove(adminId);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

}
