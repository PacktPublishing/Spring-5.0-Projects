package com.nilangpatel.tms.controller

import com.nilangpatel.tms.constant.TaskMgmntConstant
import com.nilangpatel.tms.dto.UserDTO
import com.nilangpatel.tms.dto.UserRegistrationDTO
import com.nilangpatel.tms.model.Role
import com.nilangpatel.tms.model.User
import com.nilangpatel.tms.repository.RoleRepository
import com.nilangpatel.tms.repository.UserRepository
import com.sun.org.apache.xpath.internal.operations.Bool
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import java.util.*
import kotlin.collections.ArrayList


@RestController
@RequestMapping("/api")
class TaskMgmntRESTController {

    private var logger: Logger =  LoggerFactory.getLogger(this.javaClass)

    @Autowired
    var userRepository: UserRepository?=null

    @Autowired
    var roleRepository: RoleRepository?=null

    @Autowired
    var passwordEncoder: PasswordEncoder?=null

    @PostMapping(value = "/register", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerNewUser(@Valid @RequestBody userRegistrationDto: UserRegistrationDTO,
                                        errors: Errors): ResponseEntity<List<String>> {

        logger.info(" Registering the user ... !!!")

        if (userRegistrationDto.username != null) {
            var existingUser : User? = userRepository?.findByUsername(userRegistrationDto.username) // userRepository?.findById(1)?.get()
            if (existingUser != null) {
                errors.reject("Existing username",
                        "User is already exist with username '${userRegistrationDto.username}'. ")
            }
        }
        if(userRegistrationDto.roleList.isEmpty()){
            errors.reject("No Roles provided","Please provide roles")
        }else{
            var validRole = true
            var invalidRole:String?=null
            for(roleName in userRegistrationDto.roleList){
                if(!TaskMgmntConstant.getRolesLst().contains(roleName)){
                    validRole=false
                    invalidRole = roleName
                    break
                }
            }
            if(!validRole){
                errors.reject("Invalid Roles"," $invalidRole is not a valid role")
            }
        }

        if (errors.hasErrors()) {
            val errorMsg = ArrayList<String>()
            errors.allErrors.forEach { a ->
                errorMsg.add(a.defaultMessage ?: "")
            }
            return ResponseEntity(errorMsg, HttpStatus.BAD_REQUEST)
        } else {
            val userEntity = User()
            userEntity.setUsername(userRegistrationDto.username)
            userEntity.setEnabled(true)
            val encodedPassword = passwordEncoder?.encode(userRegistrationDto.password)
            userEntity.setPassword(encodedPassword ?: "")
            userEntity.setFirstname(userRegistrationDto.firstname)
            userEntity.setLastname(userRegistrationDto.lastname)

            var role:Role?=null
            var roles: MutableSet<Role> = mutableSetOf()
            for(roleName in userRegistrationDto.roleList){
                role = roleRepository?.findByRole(roleName)
                if(role !=null) {
                    roles.add(role)
                }
            }
            userEntity.setRoles(roles)
            userRepository?.save(userEntity)

            val msgLst = Arrays.asList("User registered successfully")
            return ResponseEntity(msgLst, HttpStatus.OK)
        }
    }

}