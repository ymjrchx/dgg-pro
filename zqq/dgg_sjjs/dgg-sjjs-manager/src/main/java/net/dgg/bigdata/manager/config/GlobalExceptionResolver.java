package net.dgg.bigdata.manager.config;


import net.dgg.bigdata.manager.common.exception.AuthOptException;
import net.dgg.core.utils.DggJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 作用范围 ->它只能处理请求过程中抛出的异常，异常处理本身所抛出的异常和视图解析过程中抛出的异常它是不能处理的
 * 
 * 生命周期 ->这个handlerExceptionResolvers异常解析器列表是DispatcherServlet初始化的时候创建的 
 * SpringMVC加载的时候会将所有的异常处理HandlerExceptionResolver实现类存放到handlerExceptionResolvers一个Map结构中
 * DispatcherServlet里面会继续遍历handlerExceptionResolvers异常解析器列表，寻找下一个异常解析器来处理当前的handler。
 * Spring停止它便停止 
 * 
 *  项目中 ->package net.dgg.bigdata.manager.config.CustomWebMvcConfigurerAdapter下 向Spring注入    效果同上的作用范围
 *  @Bean
    public GlobalExceptionResolver GlobalExceptionResolver(){
         return new GlobalExceptionResolver();
     }
        
    -----------------------------------------------------------------------------------------------------------------------  
    -----------------------------------------------------------------------------------------------------------------------
    -----------------------------------------------------------------------------------------------------------------------
    -----------------------------------------------------------------------------------------------------------------------  
        
        推荐使用注解方式方式 ->  和handlerExceptionResolvers同理 
    @ExceptionHandler(XXX.class) 捕获XXX异常所抛出的一切信息 在方法体里面做异常逻辑处理  XXX继承于ava.lang.Exception
    @ResponseBody
    public ResponseEntity<String> handleServiceException(HttpServletResponse response, XXX se) {
        ........
        .......
        ........
        errLogger(se);
        return ResponseEntity.badRequest().body(se.getMessage());
    }
   
 * 
 * Created by wu on 2018-03-13.
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object object, Exception exception) {
        logger.error("异常:", exception);
        // 判断是否ajax请求
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || 
                (request.getHeader("X-Requested-With") != null && request.getHeader(
                "X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            // 如果不是ajax，JSP格式返回
            // 为安全起见，只有业务异常我们对前端可见，否则否则统一归为系统异常
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", false);
            if (exception instanceof AuthOptException) {
                map.put("msg", exception.getMessage());
                map.put("code", 1);
                return new ModelAndView("/error/403", map);
            } else {
                map.put("msg", exception.getMessage());
                map.put("code", 1);
                map.put("msg", exception);
                return new ModelAndView("/error/500", map);
            }

        } else {
            // 如果是ajax请求，JSON格式返回
            try {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("success", false);
                // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
                map.put("msg", exception.getMessage());
                map.put("code", 1);
                writer.write(DggJsonUtils.obj2Json(map));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
