package net.dgg.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

@Component
public class DggSpringContext implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DggSpringContext.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("dgg-core-spring DggSpringContext执行初始化!");
        DggSpringContext.applicationContext = applicationContext;
    }

    /**
     * 上下文对象
     */
    public static ApplicationContext applicationContext = null;

    /**
     * 获取上下文对象
     *
     * @return
     */
    public static ApplicationContext getSpringContext() {
        return applicationContext;
    }

    /**
     * 根据beanid获取对象实例
     *
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId) {
        Object obj = null;
        if (applicationContext != null) {
            obj = applicationContext.getBean(beanId);
        }
        return obj;
    }

    /**
     * 根据beanid和class获取对象实例
     *
     * @param beanId
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanId, Class<T> beanClass) {
        T t = null;
        if (applicationContext != null) {
            t = applicationContext.getBean(beanId, beanClass);
        }
        return t;
    }

    /**
     * 根据class获取对象实例
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanClass) {
        T t = null;
        if (applicationContext != null) {
            t = applicationContext.getBean(beanClass);
        }
        return t;
    }

    /**
     * 根据class获取对象实例集合
     *
     * @param beanClass
     * @param <T>
     */
    public static <T> Map<String, T> getBeansByClass(Class<T> beanClass) {
        Map<String, T> tMap = null;
        if (applicationContext != null) {
            tMap = applicationContext.getBeansOfType(beanClass);
        }
        return tMap;
    }

    /**
     * 获取多实例bean
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getInstanceBean(Class<T> beanClass) {
        T tBean = null;
        try {
            String beanName = beanClass.getSimpleName() + UUID.randomUUID();
            // 获取bean工厂并转换为DefaultListableBeanFactory
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            //创建bean信息
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
            // 注册bean
            beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getRawBeanDefinition());
            //获取动态注册的bean.
            tBean = applicationContext.getBean(beanName, beanClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tBean;
    }

    /**
     * 动态根据Class获取实例
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public synchronized static <T> T getDynamicBean(Class<T> beanClass) {
        T tBean = null;
        boolean hasNoInjectionBean = false;
        try {
            tBean = applicationContext.getBean(beanClass);
            if (tBean == null) {
                hasNoInjectionBean = true;
            }
        } catch (Exception e) {
            hasNoInjectionBean = true;
        }
        //判断是否注入
        if (hasNoInjectionBean) {
            try {
                String beanName = beanClass.getSimpleName();
                // 获取bean工厂并转换为DefaultListableBeanFactory
                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
                //创建bean信息
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(beanClass);
                // 注册bean
                beanFactory.registerBeanDefinition(beanName, beanDefinition);
                //注册实例
                beanFactory.registerSingleton(beanName, beanClass.newInstance());
                //获取动态注册的bean.
                tBean = applicationContext.getBean(beanClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tBean;
    }

    /**
     * 根据全路径获取类实例
     *
     * @param clazzFullName
     * @return
     */
    public static Object getInstance(String clazzFullName) {
        Object obj = null;
        try {
            Class clazz = Class.forName(clazzFullName);
            if (clazz != null) {
                obj = clazz.newInstance();
            }
        } catch (Exception e) {
            logger.info("dgg-core-spring 全路径：" + clazzFullName + "类没有找到!");
        }
        return obj;
    }

    /**
     * 执行无参方法
     *
     * @param clazzFullName
     * @param methodName
     */
    public static void executeMethod(String clazzFullName, String clazzMethodName, String methodName) {
        try {
            Class clazz = Class.forName(clazzFullName);
            if (clazz != null) {
                Method clazzMethod = clazz.getDeclaredMethod(clazzMethodName);
                if (clazzMethod != null) {
                    Object obj = clazzMethod.invoke(null);
                    if (obj != null) {
                        Method method = clazz.getMethod(methodName);
                        if (method != null) {
                            method.invoke(obj);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("dgg-core-spring执行无参方法" + clazzFullName + "-" + methodName + "没有找到!");
        }
    }

}
