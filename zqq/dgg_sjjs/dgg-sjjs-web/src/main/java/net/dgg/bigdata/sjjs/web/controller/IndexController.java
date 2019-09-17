package net.dgg.bigdata.sjjs.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(value = "首页数据", description = "首页数据接口")
@RestController
@RequestMapping("/")
public class IndexController {

    @SneakyThrows
    @ApiOperation("获取首页数据")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletResponse response) throws Exception{
        response.sendRedirect("/swagger-ui.html");
    }

}
