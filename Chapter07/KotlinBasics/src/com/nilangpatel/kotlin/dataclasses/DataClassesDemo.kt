package com.nilangpatel.kotlin.dataclasses

fun main(args: Array<String>) {
    var student = StudentVOKotlin("Nilang",10,5,"M")
    println("Student is  $student")// This will call toString()
    //This will call getter of respective properties
    println("age of ${student.name} is ${student.age}")
}