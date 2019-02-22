package com.nilangpatel.tms.dto

import com.nilangpatel.tms.model.Comments

class TaskDTO( var id :Int?, var title : String?,
               var detail : String?, var assignedTo : Int?, var assignedPerson:String?,
               var status : String?, var comments : Set<Comments>?)
