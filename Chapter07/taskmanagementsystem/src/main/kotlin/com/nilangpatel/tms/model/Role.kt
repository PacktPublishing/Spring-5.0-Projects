package com.nilangpatel.tms.model

import javax.persistence.*
import javax.persistence.ManyToMany




@Entity
@Table(name="role",catalog="task_mgmt_system")
class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private var id:Int? = null

    @Column(name="role")
    private var role : String? = null

    @ManyToMany(mappedBy = "roles",cascade = [CascadeType.PERSIST])
    private var users:Set<User>? = null

    fun setId(id:Int?){
        this.id=id
    }
    fun getId():Int?{
        return id
    }
    fun setRole(role:String){
        this.role = role
    }
    fun getRole():String?{
        return role
    }

    fun setUsers(users:Set<User>?){
        this.users = users
    }

    fun getUsers():Set<User>?{
        return this.users
    }
}