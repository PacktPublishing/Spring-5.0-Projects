package com.nilangpatel.tms.model

import org.springframework.security.core.GrantedAuthority

class CustomGrantedAuthority : GrantedAuthority{

    private var role:Role?=null

    constructor( role:Role ){
        this.role = role
    }

    override fun getAuthority(): String {
        return role?.getRole() ?: ""
    }
}