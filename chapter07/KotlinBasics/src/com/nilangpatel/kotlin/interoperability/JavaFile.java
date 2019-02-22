package com.nilangpatel.kotlin.interoperability;

public class JavaFile {

    public  static void  main(String args[]){

        System.out.print(KotlinFileKt.multiply(3,4));
    }

    public static int add(int num1, int num2){
        return num1+num2;
    }
}
