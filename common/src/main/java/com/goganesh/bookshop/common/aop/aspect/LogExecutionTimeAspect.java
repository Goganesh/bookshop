package com.goganesh.bookshop.common.aop.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

import java.util.logging.Logger;

@Aspect
public class LogExecutionTimeAspect {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Around("@annotation(com.goganesh.bookshop.common.aop.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        logger.info(String.format("\"%s\" executed in %s ms", joinPoint.getSignature(), stopWatch.getTotalTimeMillis()));

        return proceed;
    }
}
