package com.nilangpatel.kotlin.nullcheck

fun main(args: Array<String>) {

    var msg:String// = "Sample Message !!"
    // Below line is not allowed.
    //msg = null

    /* This is not possible
    var anotherMsg :String = null
    println(msg)
    */
   /****************************************************/

    var nullableMsg : String? = " I have some value ..!! "
   // println(nullableMsg)
    nullableMsg=null

    /****************************************************/

    var nullableMsg2 : String? = " I have some value ..!! "
    // println(nullableMsg2.length //This will shows error.

    //Correct ways to call nullable variable
    if(nullableMsg2 !=null){
        println(nullableMsg2.length) // Option 1
    }

    println(nullableMsg2?.length) // Option 2

    //Third way - Elvis Operator - if you want to return value even though the variable is null.
    println(nullableMsg2?.length ?: -1)

}