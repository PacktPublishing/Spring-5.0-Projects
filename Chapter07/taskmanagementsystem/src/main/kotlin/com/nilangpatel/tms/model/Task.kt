package com.nilangpatel.tms.model

import java.util.*
import javax.persistence.*


@Entity
@Table(name="task",catalog="task_mgmt_system")
class Task {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private var id :Int?=null

	@Column(name="title")
	private var title : String? = null
	
	@Column(name="detail")
	private var detail : String? = null

	@Column(name="assigned_to")
	private var assignedTo : Int? = null

	@Column(name="status")
	private var status : String? = null

	@OneToMany
	@JoinColumn(name ="task_id")
	private var comments : MutableSet<Comments>? = null
	

	/*@OneToMany
	@JoinTable(
			name="",
			joinColumns=@JoinColumn(columnDefinition="bookId"),
			inverseJoinColumns=@JoinColumn(columnDefinition="categoryId")
    )*/

	fun setId(id:Int){
		this.id=id
	}

	fun getId():Int?{
		return this.id
	}

	fun setTitle(title:String){
		this.title=title
	}

	fun getTitle():String?{
		return this.title
	}

	fun setDetail(detail:String){
		this.detail=detail
	}

	fun getDetail():String?{
		return this.detail
	}

	fun setAssignedTo(assignedTo:Int){
		this.assignedTo = assignedTo
	}

	fun getAssignedTo():Int?{
		return this.assignedTo
	}

	fun setStatus(status:String){
		this.status = status
	}

	fun getStatus():String?{
		return this.status
	}

	fun setComments(comments:MutableSet<Comments>){
		this.comments = comments
	}

	fun getComments():MutableSet<Comments>?{
		return this.comments
	}

}