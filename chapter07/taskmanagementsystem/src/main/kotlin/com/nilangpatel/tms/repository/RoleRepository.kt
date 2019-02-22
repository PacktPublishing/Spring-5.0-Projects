package com.nilangpatel.tms.repository

import com.nilangpatel.tms.model.Role
import org.springframework.data.jpa.repository.JpaRepository
// import org.springframework.data.jpa.repository.Query

interface RoleRepository : JpaRepository<Role, Int> {


    fun findByRole(role:String):Role?

    /*@Query("select r.id, r.role from role r inner join user_role ur on r.id = ur.role_id inner join users u on u.id = ur.user_id where u.username = :username",nativeQuery = true)
    fun findRolesByUsername(username: String): Set<Role>?*/
}