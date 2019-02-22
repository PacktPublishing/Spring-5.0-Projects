package com.nilangpatel.tms.constant

enum class TaskStatus() {

    PENDING("pending"), COMPLETED("completed"); // need semicolon at the last ...

    private var status:String=""

    constructor(status: String){
        this.status = status
    }

    fun getStatus():String{
        return this.status
    }
}