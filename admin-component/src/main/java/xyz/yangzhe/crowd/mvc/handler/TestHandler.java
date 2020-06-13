package xyz.yangzhe.crowd.mvc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: Yangzhe
 * @Data: 2020/6/12
 */

@Controller
public class TestHandler {
    @RequestMapping("/test/a/b/c.html")
    public String doTest(){
        Logger logger = LoggerFactory.getLogger(TestHandler.class);
        logger.error("-----------------------------------");
        return "target";
    }
}
