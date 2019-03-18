package com.nilangpatel.kotlin.function.expression

fun main(args: Array<String>) {

    print(min(4, 7))
}

/*  Original Function.
    fun min(numberA: Int, numberB:Int) : Int {
    if(numberA < numberB){
        return numberA
    }else{
        return numberB
    }
}*/

/* Function as an expression version 1
fun min(numberA: Int, numberB:Int) : Int = if(numberA < numberB){ numberA }else{ numberB } */

/* Function as an expression version 2 */

fun min(numberA: Int, numberB:Int) : Int
   =  if(numberA < numberB){
            numberA
        }else{
            numberB
        }