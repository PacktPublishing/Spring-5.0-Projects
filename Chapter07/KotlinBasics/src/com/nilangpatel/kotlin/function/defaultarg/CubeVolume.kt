package com.nilangpatel.kotlin.function.defaultarg

fun main(args: Array<String>) {
    var volume : Int = getVolume(2,4)
    print("get Volume $volume")
}

fun getVolume(length:Int, width:Int,height:Int =10):Int{
    return length * width * height;
}

/* Without default argument
fun getVolume(length:Int, width:Int,height:Int):Int{
    return length * width * height;
} */