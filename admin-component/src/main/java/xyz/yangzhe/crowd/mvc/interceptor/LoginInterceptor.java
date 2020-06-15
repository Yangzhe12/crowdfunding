package xyz.yangzhe.crowd.mvc.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xyz.yangzhe.crowd.constant.CrowdConstant;
import xyz.yangzhe.crowd.entity.Admin;
import xyz.yangzhe.crowd.exception.AccessForbiddenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Handler;

/**
 * @Description: 登陆状态检查拦截器
 * @Author: Yangzhe
 * @Data: 2020/6/13
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 通过request对象获取session对象
        HttpSession session = request.getSession();

        // 2. 尝试从Session域中获取Admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);

        // 3. 判断admin对象是否为空
        if (admin == null) {
            // 4. 抛出异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }

        // 5. 如果admin不为null，则返回true放行
        return true;
    }
}
