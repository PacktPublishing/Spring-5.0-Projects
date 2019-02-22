package com.nilangpatel.kotlin.function.returnfromotherfun

import java.io.PrintWriter

interface WildAnimal{

    fun setName(name:String)
    fun bark():String
}

class Dog : WildAnimal{
    private var dogName: String = ""

    override fun bark(): String {
        print(" Bhao bhao ...")
        return "${dogName} Dog is barking ..."
    }

    override fun setName(name: String) {
        this.dogName = name
    }
}

class Fox : WildAnimal{

    private var foxName: String = ""

    override fun bark(): String {
        print(" Haaaaoooooo...")
        return "${foxName} Fox is barking ..."
    }

    override fun setName(name: String) {
        this.foxName = name
    }

}

class Lion : WildAnimal{

    private var lionName: String = ""

    override fun bark(): String {
        print(" HHHHHAAAAAAAAAAA...")
        return "${lionName} Lion is Barking ..."
    }

    override fun setName(name: String) {
        this.lionName = name
    }
}

fun main(args: Array<String>) {

    PrintWriter("test").use {  }

    val getAnimalVoice: (WildAnimal) ->(String)-> String = {
        animal:WildAnimal -> {
                   animal.setName(it)
                   animal.bark()
             }
    }

   println(getAnimalVoice(Lion())("Lio"))
   println(getAnimalVoice(Dog())("Tommy"))
   println(getAnimalVoice(Fox())("Chiku"))

   println(" ***************** With function *****************  ")
   println(getAnimalVoiceFun(Lion())("Jake"))
   println(getAnimalVoiceFun(Dog())("Boomer"))
   println(getAnimalVoiceFun(Fox())("Lilli"))

}

fun getAnimalVoiceFun(animal: WildAnimal):(String) -> String{
    return {
        animal.setName(it)
        animal.bark()
    }
}


/*  second way of returning function from another function
    fun getAnimalVoiceFun(animal: WildAnimal):(name:String) -> String{
    return {
                animal.setName(name=it)
         animal.bark()
        }
} */

/*  Third way of returning function from another function
    fun getAnimalVoiceFun(animal: WildAnimal):(String) -> String{
    return fun(name:String):String {
        animal.setName(name)
        return animal.bark()
    }
} */



