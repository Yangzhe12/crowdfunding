package xyz.yangzhe.crowd.mvc.config;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import xyz.yangzhe.crowd.constant.CrowdConstant;
import xyz.yangzhe.crowd.exception.AccessForbiddenException;
import xyz.yangzhe.crowd.exception.LoginAcctAlreadyInUseException;
import xyz.yangzhe.crowd.exception.LoginFailedException;
import xyz.yangzhe.crowd.util.CrowdUtil;
import xyz.yangzhe.crowd.util.ResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 基于注解的异常处理器类
 * @Author: Yangzhe
 * @Data: 2020/6/13
 */
@ControllerAdvice
public class CrwodExceptionResolver {
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            LoginFailedException exception,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView resolveAccessForbiddenException(
            AccessForbiddenException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String viewName = "admin-login";

        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(
            LoginAcctAlreadyInUseException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String viewName = "admin-add";

        return commonResolve(viewName, exception, request, response);
    }


    /**
     * @Description: 处理自定义异常的共用方法；
     * @Param:
     *      viewName:   异常处理完成后要去的页面
     *      exception:  实际捕获到的异常类型
     *      request:    当前请求对象
     *      response:   当前响应对象
     * @Return:
     */
    private ModelAndView commonResolve(
            String viewName,
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException{
        // 1. 判断当前请求类型
        boolean isAjaxRequest = CrowdUtil.judgeRequestType(request);

        // 2. 如果是Ajax请求Json数据
        if (isAjaxRequest){
            // 3. 创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

            // 4. 创建Gson对象
            Gson gson = new Gson();

            // 5. 将ResultEntity对象转换为json字符串
            String jsonData = gson.toJson(resultEntity);

            // 6. 将Json字符串作为响应体返回给浏览器
            response.getWriter().write(jsonData);

            // 7. 由于上面已经通过原生的response对象返回了响应，所以不提供ModelAndView对象
            return null;
        }

        // 8. 如果不是Ajax请求则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();

        // 9. 将Exception对象存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);

        // 10. 设置对应的视图名称
        modelAndView.setViewName(viewName);

        // 11. 返回modelAndView对象
        return modelAndView;
    }
}
