package com.nilangpatel.tms.dto

data class UserRegistrationDTO(var username:String, var password:String,
                               var firstname:String, var lastname:String,
                               var roleList:List<String>)