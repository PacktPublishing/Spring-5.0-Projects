package com.nilangpatel.tms.service

import com.nilangpatel.tms.model.CustomUserPrinciple
import com.nilangpatel.tms.model.Role
import com.nilangpatel.tms.repository.RoleRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import com.nilangpatel.tms.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.xml.ws.ServiceMode


@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private val userRepository: UserRepository? = null

    /*@Autowired
    private val roleRepository: RoleRepository? = null*/

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository?.findByUsername(username) ?: throw UsernameNotFoundException(username)
        /*val roles:Set<Role>? = roleRepository?.findRolesByUsername(username) ?: null
        user.setRoles(roles)*/
        return CustomUserPrinciple(user)
    }
}