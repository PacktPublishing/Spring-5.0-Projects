package com.nilangpatel.kotlin.interoperability

fun main(args: Array<String>) {
    var total = JavaFile.add(5,6)
    print("Value from Java is $total")
}

fun multiply(a:Int, b:Int):Int{
    print("Calling multiply function From Kotlin....")
    return a * b
}
