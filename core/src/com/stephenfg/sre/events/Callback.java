package com.stephenfg.sre.events;

public class Callback {
    public Subscriber owner;
    public String methodName;

    public Callback(Subscriber owner, String methodName){
        this.owner = owner;
        this.methodName = methodName;
    }
}
