package net.dgg.zqq.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.controller.seo.dto.PageCode;
import net.dgg.zqq.controller.seo.dto.UserAccessEventWrapper;
import net.dgg.zqq.services.seo.MessageQueueService;
import net.dgg.zqq.utils.ThreadHelper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 李程 on 2018/11/8.
 */
@Aspect
@Component
@Slf4j
public class ControllerAccessAspectAdvice {

    @Value("${nature.messagequeue.host}")
    String host;

    @Value("${nature.messagequeue.username}")
    String username;

    @Value("${nature.messagequeue.password}")
    String password;

    @Value("${nature.messagequeue.port}")
    Integer port;

    @Value("${nature.messagequeue.vhost}")
    String vhost;

    @Value("${app.user.access.event}")
    String userAccessEvent;

    Gson gson;

    JsonParser jsonParser;

    MessageQueueService messageQueueService;

    ExecutorService executorService = Executors.newFixedThreadPool(100);

    @SneakyThrows
    @PostConstruct
    public void init() {
        jsonParser = new JsonParser();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        messageQueueService = MessageQueueService.builder().queue(userAccessEvent).host(host).password(password).port(port).username(username).vhost(vhost).build();
    }

    @Pointcut("execution(* net.dgg.zqq..*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void restMethod() {
    }

    @Around("restMethod()")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object result = proceedingJoinPoint.proceed();
            String requestURI = ThreadHelper.getThreadContextVar("requestURI");
            byte[] requestBody = ThreadHelper.getThreadContextVar("requestBody");
            HttpServletRequest request = ThreadHelper.getThreadContextVar("request");
            if (request != null) {
                String contentType = request.getContentType();
                if (StringUtils.isNotEmpty(requestURI)) {
                    if (request.getMethod().equalsIgnoreCase("POST")) {
                        if (StringUtils.isNotEmpty(contentType)) {
                            if (contentType.toUpperCase().contains("APPLICATION/JSON")) {
                                switch (requestURI.toUpperCase()) {
                                    case "/BRANDSEARCH/SEARCHLIST": {
                                        String xFrom = request.getHeader("X-FROM");
                                        if (StringUtils.isEmpty(xFrom) || !xFrom.equalsIgnoreCase("INNER")) {
                                            UserAccessEventWrapper userAccessEvent = new UserAccessEventWrapper();
                                            userAccessEvent.setCurrentTime(Clock.systemUTC().millis());
                                            userAccessEvent.setActiveKey("/BRANDSEARCH/SEARCHLIST");
                                            userAccessEvent.setPageCode(PageCode.TRADE_MARK_SEARCH);
                                            userAccessEvent.setUrl(request.getRequestURL().toString());
                                            userAccessEvent.setParameter(toJsonMap(requestBody));
                                            executorService.execute(() -> {
                                                try {
                                                    messageQueueService.putMessage(this.userAccessEvent, gson.toJson(userAccessEvent));
                                                } catch (Exception e) {
                                                    log.error("{}", e);
                                                }
                                            });
                                        }
                                    }
                                    break;
                                    case "/PATENTSEARCH/SEARCHLIST": {
                                        String xFrom = request.getHeader("X-FROM");
                                        if (StringUtils.isEmpty(xFrom) || !xFrom.equalsIgnoreCase("INNER")) {
                                            UserAccessEventWrapper userAccessEvent = new UserAccessEventWrapper();
                                            userAccessEvent.setCurrentTime(Clock.systemUTC().millis());
                                            userAccessEvent.setActiveKey("/PATENTSEARCH/SEARCHLIST");
                                            userAccessEvent.setPageCode(PageCode.PATENT_SEARCH);
                                            userAccessEvent.setUrl(request.getRequestURL().toString());
                                            userAccessEvent.setParameter(toJsonMap(requestBody));
                                            executorService.execute(() -> {
                                                try {
                                                    messageQueueService.putMessage(this.userAccessEvent, gson.toJson(userAccessEvent));
                                                } catch (Exception e) {
                                                    log.error("{}", e);
                                                }
                                            });
                                        }
                                    }
                                    break;
                                    default:
                                        break;
                                }
                            } else if (contentType.toUpperCase().contains("APPLICATION/X-WWW-FORM-URLENCODED")) {

                            }
                        }
                    } else if (request.getMethod().equalsIgnoreCase("GET")) {
                        switch (requestURI.toUpperCase()) {
                            case "/BRANDSEARCH/BRANDDETAIL": {
                                String xFrom = request.getHeader("X-FROM");
                                if (StringUtils.isEmpty(xFrom) || !xFrom.equalsIgnoreCase("INNER")) {
                                    UserAccessEventWrapper userAccessEvent = new UserAccessEventWrapper();
                                    userAccessEvent.setCurrentTime(Clock.systemUTC().millis());
                                    userAccessEvent.setActiveKey("/BRANDSEARCH/BRANDDETAIL");
                                    userAccessEvent.setPageCode(PageCode.TRADE_MARK_DETAIL);
                                    userAccessEvent.setUrl(request.getRequestURL().toString());
                                    userAccessEvent.setParameter(getParameter(request));
                                    executorService.execute(() -> {
                                        try {
                                            messageQueueService.putMessage(this.userAccessEvent, gson.toJson(userAccessEvent));
                                        } catch (Exception e) {
                                            log.error("{}", e);
                                        }
                                    });
                                }
                            }
                            break;
                            case "/PATENTSEARCH/DETAIL": {
                                String xFrom = request.getHeader("X-FROM");
                                if (StringUtils.isEmpty(xFrom) || !xFrom.equalsIgnoreCase("INNER")) {
                                    UserAccessEventWrapper userAccessEvent = new UserAccessEventWrapper();
                                    userAccessEvent.setCurrentTime(Clock.systemUTC().millis());
                                    userAccessEvent.setActiveKey("/PATENTSEARCH/DETAIL");
                                    userAccessEvent.setPageCode(PageCode.PATENT_DETAIL);
                                    userAccessEvent.setUrl(request.getRequestURL().toString());
                                    userAccessEvent.setParameter(getParameter(request));
                                    executorService.execute(() -> {
                                        try {
                                            messageQueueService.putMessage(this.userAccessEvent, gson.toJson(userAccessEvent));
                                        } catch (Exception e) {
                                            log.error("{}", e);
                                        }
                                    });
                                }
                            }
                            break;
                            default:
                                break;
                        }
                    }
                }
            }
            return result;
        } catch (Throwable throwable) {
            throw new IllegalStateException(throwable);
        }
    }

    @SneakyThrows
    private Map<String, Object> toJsonMap(byte[] requestBody) {
        String parameter = new String(requestBody, "UTF-8");
        JsonObject params = jsonParser.parse(parameter).getAsJsonObject();
        Optional<Map<String, Object>> optionalParameter = params.entrySet().stream().map(entry -> {
            Map<String, Object> ent = new LinkedHashMap<>();
            ent.put(entry.getKey(), entry.getValue());
            return ent;
        }).reduce((a, b) -> {
            a.putAll(b);
            return a;
        });

        Map<String, Object> parameterMap;
        if (optionalParameter.isPresent()) {
            parameterMap = optionalParameter.get();
        } else {
            parameterMap = new LinkedHashMap<>();
        }
        return parameterMap;
    }

    private Map<String, Object> getParameter(HttpServletRequest request) {
        Enumeration<String> pn = request.getParameterNames();
        Map<String, Object> param = new LinkedHashMap<>();
        while (pn.hasMoreElements()) {
            String p = pn.nextElement();
            param.put(p, request.getParameter(p));
        }
        return param;
    }

}
