package com.sbzl.framework.learn.controllerParamLogAspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class ControllerParamLogAspect {

    private static final Logger logger = LoggerFactory.getLogger("controller");

    @Pointcut("execution(* com.sbzl.framework.learn..*(..))")
    public void controllerLog(){}

    @Around("controllerLog()")
    public Object authLogin(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        logger.info("请求开始, 各个参数, userId: {}, url: {}, method: {}, uri: {}, params: {}", request.getHeader("accountId"), url, method, uri, queryString);
        return pjp.proceed(pjp.getArgs());
    }
}

