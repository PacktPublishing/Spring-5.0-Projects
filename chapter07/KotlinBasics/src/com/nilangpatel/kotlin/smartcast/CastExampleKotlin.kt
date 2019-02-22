package com.nilangpatel.kotlin.smartcast

fun main(args: Array<String>) {
    val name: Any = "Nilang"
    if(name is String) {
        greetingMsg(name)
    }
}

private fun greetingMsg(name: String) {
    print(" Welcome $name ..!!")
}