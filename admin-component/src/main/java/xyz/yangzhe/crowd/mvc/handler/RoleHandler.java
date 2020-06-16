package xyz.yangzhe.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.yangzhe.crowd.entity.Role;
import xyz.yangzhe.crowd.service.api.RoleService;
import xyz.yangzhe.crowd.util.ResultEntity;

/**
 * @Description: 角色相关请求的handler
 * @Author: Yangzhe
 * @Data: 2020/6/15
 */
@Controller
public class RoleHandler {
    @Autowired
    private RoleService roleService;

    /**
     * @Description: 查看角色列表的请求handler
     * @Param:
     * @Return:
     */
    @ResponseBody
    @RequestMapping("/role/get/page/info.html")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        // 1. 调用Service方法获取分页数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        // 2. 封装到ResultEntity对象中返回（如果上面的操作抛出异常，交给异常映射机制处理）
        return ResultEntity.successWithData(pageInfo);
    }
}
