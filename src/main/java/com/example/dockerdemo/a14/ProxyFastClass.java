package com.example.dockerdemo.a14;

import org.springframework.cglib.core.Signature;

public class ProxyFastClass {
    static Signature s0=new Signature("saveSuper","()V");
    static Signature s1=new Signature("saveSuper","(I)V");
    static Signature s2=new Signature("saveSuper","(J)V");
    //获取代理方法的编号
    // signature 包括方法的名字，参数返回值
    public int getIndex(Signature signature){
        if(s0.equals(signature)){
            return 0;
        } else if(s1.equals(signature)){
            return 1;
        }else if(s2.equals(signature)){
            return 2;
        }else  return -1;
    }
    // 根据方法编号，正常调用目标对象方法
    public Object invoke(int index,Object proxy,Object[] args){
        if(index==0){
            ((Proxy )proxy).saveSuper();
            return null;
        } else if(index==1){
            ((Proxy)proxy).saveSuper((int)args[0]);
            return null;
        }
        else if(index==2){
            ((Proxy)proxy).saveSuper((long)args[0]);
            return null;
        } else {
            throw new RuntimeException("method not exist");
        }

    }

    public static void main(String[] args) {
        ProxyFastClass proxyFastClass = new ProxyFastClass();
        int index = proxyFastClass.getIndex(new Signature("saveSuper", "()V"));
        System.out.println(index);
        proxyFastClass.invoke(index,new Proxy(),new Object[0]);
    }
}
