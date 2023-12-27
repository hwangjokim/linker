package com.hwangjo.linker.develop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AopLogger {
    @Around("execution(* com.hwangjo.linker..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("=================={} start==============", joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            log.info("=================={} end==============", joinPoint.toString());
        }
    }
}
