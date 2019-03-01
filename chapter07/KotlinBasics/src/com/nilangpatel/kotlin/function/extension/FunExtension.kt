package com.nilangpatel.kotlin.function.extension

fun main(args: Array<String>) {
    //Un comment this to see Extension Function on String
    print("this is just for sample".camelCase())
    print(3.square())
}

fun String.camelCase():String{
    var camelCaseStr = StringBuffer()
    var wordLst : List<String> = this.trim().split(" ")

    for(word in wordLst){
        camelCaseStr.append(word.replaceFirst(word[0], word[0].toUpperCase())).append(" ")
    }
    return camelCaseStr.trim().toString()
}

fun Int.square():Int{
    return this * this
}