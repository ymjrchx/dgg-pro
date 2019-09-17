package net.dgg.tmd.foundation.platform.annotation.service;

import net.dgg.tmd.foundation.platform.annotation.AuthOpt;
import net.dgg.tmd.foundation.platform.common.exception.AuthOptException;
import net.dgg.tmd.foundation.platform.role.service.RoleService;
import net.dgg.tmd.foundation.platform.session.CommonSession;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by wu on 2018-03-13.
 */
@Aspect
@Component
public class AuthOptService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    SessionManager sessionManager;

    @Resource
    RoleService roleService;

    //配置接入点
    @Pointcut("execution(* net.dgg.tmd.foundation.platform.*.controller..*.*(..))")
    private void AuthOptAspect() {
        //定义一个切入点
    }

    @Around("AuthOptAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获得被拦截的方法
        Method method = null;
        try {
            Object target = pjp.getTarget();
            // 拦截的方法名称。当前正在执行的方法
            String methodName = pjp.getSignature().getName();
            // 拦截的放参数类型
            Signature sig = pjp.getSignature();
            MethodSignature msig = null;
            if (!(sig instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }
            msig = (MethodSignature) sig;
            Class[] parameterTypes = msig.getMethod().getParameterTypes();
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e1) {
            logger.error("未找到方法", e1);
        } catch (SecurityException e1) {
            logger.error("权限错误", e1);
        }
        Object object = null;
        if (null != method) {
            // 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
            if (method.isAnnotationPresent(AuthOpt.class)) {
                AuthOpt authOpt = method.getAnnotation(AuthOpt.class);
                CommonSession commonSession=sessionManager.getCurrentSession();
                Long userId = commonSession.getValue("userId", Long.class);
                if (!roleService.exitsOperate4User(userId, authOpt.code())) {
                    //没有权限
                    throw new AuthOptException("用户操作权限验证不通过");
                }
                object = pjp.proceed();
            } else {//没有包含注解
                object = pjp.proceed();
            }
        } else { //不需要拦截直接执行
            object = pjp.proceed();
        }

        return object;
    }
}
