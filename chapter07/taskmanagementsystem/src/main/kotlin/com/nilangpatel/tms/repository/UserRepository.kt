package com.nilangpatel.tms.repository

import com.nilangpatel.tms.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {

    fun findByUsername(username: String): User?
}