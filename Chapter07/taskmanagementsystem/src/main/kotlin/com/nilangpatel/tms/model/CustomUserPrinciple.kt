package com.nilangpatel.tms.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserPrinciple : UserDetails {

    constructor(user: User?) {
        this.user = user
    }
    private var user:User? = null

    fun setUser(user:User?){
        this.user=user
    }

    fun getUser():User?{
        return this.user
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return this.user?.getUsername() ?: ""
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
       return this.user?.getPassword() ?: ""
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
         var userRoles:Set<Role>? = user?.getRoles() ?: null

         var authorities:MutableSet<GrantedAuthority> = HashSet<GrantedAuthority>()

         for(role in userRoles.orEmpty()){
             authorities.add(CustomGrantedAuthority(role))
         }
         return authorities
    }

}