package com.goganesh.bookshop.common.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
public class LoggingAspect {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Around("@annotation(com.goganesh.bookshop.common.aop.annotation.LogExecutionTime)")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.log(Level.INFO, String.format("Enter: %s.%s() with argument[s] = %s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));

        Object result = joinPoint.proceed();
        logger.log(Level.INFO, String.format("Exit: %s.%s() with result = %s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result));
    }
}