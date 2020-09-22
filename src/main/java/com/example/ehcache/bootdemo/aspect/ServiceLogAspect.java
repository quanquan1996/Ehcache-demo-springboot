package com.example.ehcache.bootdemo.aspect;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

/**
 * @author QiuPengJun
 * @version 1.0
 * @date 2020/8/4 11:27
 */
@Component
@Aspect
public class ServiceLogAspect {
    private static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);
    @Pointcut("execution(* com.example.ehcache.bootdemo.controller.*.*(..))")
    private void pointCut() {}

    @Around("pointCut()")
    public Object entry(ProceedingJoinPoint joinpoint) throws Throwable {

        // 方法名
        String method = joinpoint.getSignature().getName();
        String name=joinpoint.getClass().getName();
        // 接口入参
        Object[] args = joinpoint.getArgs();

        // 调用实际接口耗时
        long start = System.currentTimeMillis();
        Object result = joinpoint.proceed();
        long end = System.currentTimeMillis();
        long cost = end - start;
        // 记录日志
        log.info("LogAspect serviceName={},method={}, cost={}ms, parameter={},result={}", name,method,cost, object2JsonString(args), object2JsonString(result));

        // 返回调用接口结果
        return result;
    }
    public static String object2JsonString(Object obj) {
        StringWriter strWriter = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        String content = "";
        JsonGenerator g = null;
        try {
            g = mapper.getJsonFactory().createJsonGenerator(strWriter);
            g.writeObject(obj);
            content = strWriter.toString();
        } catch (Exception e) {
            if (obj != null) {

            } else {

            }
        } finally {
            try {
                if (g != null) {
                    g.close();
                }
                strWriter.close();
            } catch (Exception e2) {

            }
        }
        return content;
    }


}
