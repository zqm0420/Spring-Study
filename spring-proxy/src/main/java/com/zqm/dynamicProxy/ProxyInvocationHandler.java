package com.zqm.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyInvocationHandler implements InvocationHandler {
    Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    //生成得到代理对象
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    //处理代理实例，并返回结果
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log1(method, args);
        Object result = method.invoke(object, args);
        log2(method, result);
        return result;
    }

    public void log1(Method method, Object[] args){
        System.out.println("执行了"+method.getName()+"方法，参数是"+ Arrays.toString(args));
    }

    public void log2(Method method, Object object){
        System.out.println("执行了"+method.getName()+"方法，结果是"+ object);
    }
}
