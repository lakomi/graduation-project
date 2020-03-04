package com.edu.graduation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * controller 日志
 * @author q
 */
@Aspect
@Component
@Slf4j
public class ControllerLogger {

    /**
     * 定义一个切入点表达式。确定那些类需要代理。以下切入点表示，controller包下的所有类的所有方法都会被代理
     */
    @Pointcut(value = "execution(public * com.edu.graduation.controller.*.*(..))")
    public void controllerLog(){}

    /**
     * 前置方法。执行在controller之前。
     * JoinPoint对象
     * @param point  封装了代理方法信息的对象，若用不到则可以忽略不写。
     */
    @Before("controllerLog()")
    public void before(JoinPoint point){
        log.info("[controller]————beginning");

        /** point.getSignature().getName()——目标方法名
         *  point.getSignature().getDeclaringTypeName()——目标方法所属类的类名
         *  point.getArgs()——获取传入目标方法的参数
         * **/

        Object[] args = point.getArgs();
        for (Object arg : args){
            /* 所带参数 */
            log.info("arg:"+arg);
        }
        log.info("————————————————————————————————————————————");
    }

    /**
     * 后置增强。方法退出时执行。
     * @param ret
     */
    @AfterReturning(pointcut = "controllerLog()",returning = "ret")
    public void afterReturing(Object ret){
        System.out.println("[controller]————return "+ret);
    }

    /**
     * 异常抛出增长。
     * @param throwable
     */
    @AfterThrowing(pointcut = "controllerLog()",throwing = "throwable")
    public void afterThrowing(Throwable throwable){
        System.out.println("[controller]————throw "+ throwable.getMessage());
    }

    /**
     * @After 不管是抛出异常或者正常退出都会执行。
     * @Around 环绕增强 （@Around参数必须为ProceedingJoinPoint）
     */
}
