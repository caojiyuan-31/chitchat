package org.ganmuren.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogAspect {

    //切入点：待增强的方法 *1表示返回值 *2表示任意类 *3表示任意方法 (..)表示任意参数
    //表示匹配org.ganmuren包及其子包下的所有方法 execution(* org.ganmuren..*.*(..))
    @Pointcut("execution(* org.ganmuren..*.*(..))")
    public void globalCut(){}

    //表示不匹配@NoLog注释的方法
    @Pointcut("!@annotation(org.ganmuren.annotation.NoLog)")
    public void noLogCut(){}

    //环绕通知
    //入参加入相关注解，则可接收注解里的值
    @Around("globalCut() && noLogCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("CLASS_METHOD : " + pjp);
        log.info("环绕通知开始：.....");
        Object ret = pjp.proceed();
        log.info("环绕通知开始：.....");
        return ret;
    }

}
