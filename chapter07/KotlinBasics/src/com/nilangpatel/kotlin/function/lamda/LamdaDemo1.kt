package com.nilangpatel.kotlin.function.lamda

fun main(args: Array<String>) {

    //This is functional literal
    { println(" This is function literal ")}

    /***********************************************************/
    /*
    Normal way to declare above function
    *
    fun printMsg(message:String){
        println(message)
    }
    */

    //Functional literal assigned to variable
    var greetingMsg = { println("Hello World ...!!!")}

    //Calling the function through literal
    greetingMsg()

   /***********************************************************/

    //Lambda with parameter
    var showWarning = {message : String -> println(message)}

    //Calling Lambda expression with parameter
    showWarning(" Warning 1 occurred ..!!!")
    showWarning(" Warning 2 occurred ..!!!")

    /***********************************************************/
    //Multiple parameters
    var addition = { num1: Int, num2: Int ->
        println("sum of $num1 and $num2 is ..${num1+num2}")
    }
    addition(3, 5)

    /***********************************************************/
    //Another way to write above code .
    var addition2 : (Int,Int)-> Unit = { num1, num2 ->
        println("sum of $num1 and $num2 is ..${num1+num2}")
    }
    addition2(3, 5)

}