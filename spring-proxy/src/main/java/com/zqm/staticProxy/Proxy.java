package com.zqm.staticProxy;

public class Proxy implements Rent {
    private Host host;

    public Proxy() {
    }

    public Proxy(Host host) {
        this.host = host;
    }


    public void rent() {
        seeHouse();
        host.rent();
        signContract();
        fare();
    }

    public void seeHouse(){
        System.out.println("中介带看房了！");
    }

    public void signContract(){
        System.out.println("中介和房客签合同！");
    }

    public void fare(){
        System.out.println("收中介费！");
    }
}
