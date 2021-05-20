package com.zqm.dynamicProxy;


public class Test {
    //方法执行后，打印日志的工作全部交给代理完成
    public static void main(String[] args) {
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        pih.setObject(new CalImpl());
        Cal proxy = (Cal) pih.getProxy();
        proxy.add(1,1);
        proxy.sub(1,1);
        proxy.mul(1,2);
        proxy.div(2,1);
    }
}
