package com.nilangpatel.tms.model

import javax.persistence.*
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany




@Entity
@Table(name="users",catalog="task_mgmt_system")
class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private var id:Int? = null

    @Column(name="username")
    private var username : String? = null

    @Column(name="password")
    private var password : String? = null

    @Column(name="firstname")
    private var firstname : String? = null

    @Column(name="lastname")
    private var lastname : String? = null

    @Column(name="enabled")
    private var enabled : Boolean = false

    @ManyToMany(cascade = [CascadeType.PERSIST],fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = [JoinColumn(name = "user_id",referencedColumnName = "id") ],
            inverseJoinColumns = [JoinColumn(name = "role_id",referencedColumnName = "id")]
    )
    private var roles: Set<Role>? = null

    @OneToMany
    @JoinColumn(name ="user_id")
    private var comments : MutableSet<Comments>? = null

    fun setId(id:Int){
        this.id = id
    }

    fun getId():Int?{
        return this.id
    }

    fun setUsername(username:String?){
        this.username = username
    }

    fun getUsername():String?{
        return this.username
    }

    fun setPassword(password:String){
        this.password = password
    }

    fun getPassword():String?{
        return this.password
    }

    fun setFirstname(firstname:String?){
        this.firstname = firstname
    }

    fun getFirstname():String?{
        return firstname
    }

    fun setLastname(lastname:String?){
        this.lastname = lastname
    }

    fun getLastname():String?{
        return this.lastname
    }

    fun setEnabled(enabled:Boolean){
        this.enabled = enabled
    }

    fun isEnabled():Boolean{
        return this.enabled
    }
    fun setRoles(roles:Set<Role>?){
        this.roles = roles
    }

    fun getRoles():Set<Role>?{
        return this.roles
    }
}
