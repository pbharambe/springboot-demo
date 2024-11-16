package com.techlearning.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.techlearning.controller.PingController.*(..))")
    public void bankTransactionMethods() {
        // Pointcut for bank transaction methods
    }

    @Before("bankTransactionMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Starting method: " + joinPoint.getSignature().getName());
    }

    @After("bankTransactionMethods()")
    public void logAfterMethod(JoinPoint joinPoint) {
        logger.info("Completed method: " + joinPoint.getSignature().getName());
    }

    @Pointcut("execution(* com.techlearning.service.StudentService.*(..))")
    public void serviceMethods() {
        // Pointcut for bank transaction methods
    }

    @Before("serviceMethods()")
    public void logServiceBeforeMethod(JoinPoint joinPoint) {
        logger.info("Starting method: " + joinPoint.getSignature().getName());
    }

    @After("serviceMethods()")
    public void logServiceAfterMethod(JoinPoint joinPoint) {
        logger.info("Completed method: " + joinPoint.getSignature().getName());
    }
}
