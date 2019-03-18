package com.nilangpatel.tms.repository

import com.nilangpatel.tms.model.Comments
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CommentRepository: JpaRepository<Comments, Int> {

    @Query("SELECT c.id, c.comment, concat(u.firstname,' ',u.lastname) FROM comments c inner join users u on c.user_id=u.id inner join task t on t. id = c.task_id and t.id =:taskId",nativeQuery = true)
    fun findByTaskId(taskId: Int):List<Array<Any>>
}