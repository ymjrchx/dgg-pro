package net.dgg.zqq.component;


import net.dgg.zqq.constant.WebConfKeyConstant;
import net.dgg.zqq.framework.redis.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author 刘阳
 * @ClassName <WebConfAop>
 * @despriction
 * @create 2018/08/09 15:21
 **/
@Aspect
@Component
public class WebConfAop implements WebConfUtil {

    @Pointcut("execution(public * net.dgg.zqq.component.WebConf.get*())")
    public void pointCut() {
    }


    @Around("pointCut()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String return_type = methodSignature.getReturnType().getSimpleName();
        String methodName = methodSignature.getName();
        String code = methodName.replaceFirst("get", "");
        code = code.substring(0, 1).toLowerCase().concat(code.substring(1));

        String val = RedisUtils.get(WebConfKeyConstant.WEB_CONF_KEY.concat(code));
        return StringUtils.isEmpty(val) ? joinPoint.proceed() : this.transToType(val, return_type);
    }


}
