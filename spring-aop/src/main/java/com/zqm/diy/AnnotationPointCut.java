package com.zqm.diy;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect //标记这个类是个切面
public class AnnotationPointCut {
    @Before("execution(* com.zqm.service.UserServiceImpl.*(..))")
    public void before(){
        System.out.println("====方法执行前====");
    }

    @After("execution(* com.zqm.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("====方法执行后====");
    }
}
