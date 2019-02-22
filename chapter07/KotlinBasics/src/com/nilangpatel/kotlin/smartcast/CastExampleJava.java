package com.nilangpatel.kotlin.smartcast;

public class CastExampleJava {

    public static void  main(String[] args){

        Object name = "Nilang";

        if(name instanceof String){
            greetingMsg((String) name);
        }
    }

    private static void greetingMsg(String name){
        System.out.print(" Welcome "+name+" ..!!");
    }
}
