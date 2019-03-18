package com.nilangpatel.tms.model

import javax.persistence.*

@Entity
@Table(name="comments",catalog="task_mgmt_system")
class Comments {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private var id :Int?=null

	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private var user : User? = null

	@ManyToOne
	@JoinColumn(name = "task_id", nullable = false)
	private var task : Task? = null;

	private var comment:String? = null

	fun setId(id:Int){
		this.id=id
	}

	fun getId():Int?{
		return this.id
	}

	fun setTask(task:Task){
		this.task = task
	}

	fun getTask():Task?{
		return this.task
	}

	fun setUser(user:User){
		this.user=user
	}

	fun getUser():User?{
		return this.user
	}

	fun setComment(comment:String){
		this.comment=comment
	}

	fun getComment():String?{
		return this.comment
	}
	
}