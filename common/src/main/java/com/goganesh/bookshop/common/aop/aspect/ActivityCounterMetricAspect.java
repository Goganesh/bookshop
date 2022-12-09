package com.goganesh.bookshop.common.aop.aspect;

import com.goganesh.bookshop.common.service.ActivityCounterService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@AllArgsConstructor
public class ActivityCounterMetricAspect {

    ActivityCounterService activityCounterService;

    @Pointcut("within(@org.springframework.stereotype.Controller *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {

    }

    @AfterReturning("springBeanPointcut()")
    public void logAround(JoinPoint joinPoint) {
        activityCounterService.incrementCounterByActivityName(joinPoint.getSignature().getDeclaringTypeName() + " " + joinPoint.getSignature().getName());
    }

}
