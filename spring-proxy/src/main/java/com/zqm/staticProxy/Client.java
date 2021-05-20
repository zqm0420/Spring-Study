package com.zqm.staticProxy;

public class Client {
    public static void main(String[] args) {
        Proxy proxy = new Proxy(new Host());
        proxy.rent();
    }

}
