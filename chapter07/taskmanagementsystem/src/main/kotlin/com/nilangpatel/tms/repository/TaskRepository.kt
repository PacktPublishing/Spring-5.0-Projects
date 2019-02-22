package com.nilangpatel.tms.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.nilangpatel.tms.model.Task
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task,Int>{

    @Query("SELECT t.id, t.title, t.detail, concat(u.firstname,' ',u.lastname) as assignedTo ,t.status FROM task t inner join users u on t.assigned_to = u.id",nativeQuery = true)
    fun findAllTasks(): List<Array<Any>>

    @Query("SELECT t.id, t.title, t.detail, concat(u.firstname,' ',u.lastname) as assignedTo ,t.status FROM task t inner join users u on t.assigned_to = u.id and u.id =:userId",nativeQuery = true)
    fun findMyTasks(userId : Int): List<Array<Any>>
}