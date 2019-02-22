package com.nilangpatel.tms.controller

import org.springframework.web.bind.annotation.GetMapping
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ui.Model
import com.nilangpatel.tms.constant.TaskMgmntConstant
import com.nilangpatel.tms.constant.TaskStatus
import com.nilangpatel.tms.dto.CommentDTO
import com.nilangpatel.tms.dto.TaskDTO
import com.nilangpatel.tms.model.Comments
import com.nilangpatel.tms.model.CustomUserPrinciple
import com.nilangpatel.tms.model.Task
import com.nilangpatel.tms.model.User
import com.nilangpatel.tms.repository.CommentRepository
import com.nilangpatel.tms.repository.TaskRepository
import com.nilangpatel.tms.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping

@Controller
class TaskMgmtSystemController {
	
	private var logger: Logger  =  LoggerFactory.getLogger(this.javaClass)

	@Autowired
	var userRepository: UserRepository?=null

	@Autowired
	var taskRepository: TaskRepository?=null

	@Autowired
	var commentRepository: CommentRepository?=null
	
	@GetMapping("/")
	fun showHomePage(model: Model): String  {
		logger.info("This will show home page ")
		setProcessingData(model, TaskMgmntConstant.TITLE_HOME_PAGE)
		return "home"
	}
	
	@GetMapping("/controlPage")
	fun showControlPage(model:Model):String {
		logger.info("Showing control page ")
	    setProcessingData(model, TaskMgmntConstant.TITLE_LANDING_CONTROL_PAGE)
	    return "control-page"
	}
	
	@GetMapping("/login")
	fun showLoginPage(@RequestParam(name = "error",required = false) error:String? ,
			@RequestParam(name = "logout",	required = false) logout:String?, model:Model):String  {
		logger.info("This is login page URL   ")
		
		if (error != null) {
			model.addAttribute("error", "Invalid Credentials provided.")
		}
		if (logout != null) {
			model.addAttribute("message", "Logged out")
		}
		setProcessingData(model, TaskMgmntConstant.TITLE_LOGIN_PAGE)

		return "login"
	}

	@GetMapping("/showAddTask")
	fun showAddTask(model:Model):String {
		logger.info("Going to show Add task page")
		setProcessingData(model, TaskMgmntConstant.TITLE_ADD_TASK_PAGE)
		return "task-add"
	}

	@GetMapping("/showEditTask")
	fun showEditTask(@RequestParam(name = "taskId",required = true) taskId: Int,
			model:Model):String {
		val task:Task? = taskRepository?.findById(taskId)?.get()
		if(task !=null){
			val userId: Int = task.getAssignedTo() ?: 0
			val user:User? = userRepository?.findById(userId)?.get()
			val taskDto = TaskDTO(task.getId(),task.getTitle(),
					task.getDetail(),userId,(user?.getFirstname() + " "+user?.getLastname()),task.getStatus(),null)
			model.addAttribute("task",taskDto)
		}
		logger.info("Going to show Edit task page")
		setProcessingData(model, TaskMgmntConstant.TITLE_UPDATE_TASK_PAGE)
		model.addAttribute("screenTitle","Edit Task")
		return "task-edit"
	}

	@PostMapping("/addTask")
	fun addTask(@RequestParam(name = "title",required = true) title:String,
				@RequestParam(name = "detail",required = true) detail:String,
				@RequestParam(name = "selectedUserId", required = true) selectedUserId:Int,
			model:Model):String {
		val task = Task()
		task.setTitle(title)
		task.setDetail(detail)
		task.setAssignedTo(selectedUserId)
		task.setStatus(TaskStatus.PENDING.getStatus())
		taskRepository?.save(task)

		logger.info("Goint to show Add task page")
		setProcessingData(model, TaskMgmntConstant.TITLE_ADD_TASK_PAGE)
		model.addAttribute("screenTitle","Add new Task")
		return "redirect:allTaskList"
	}
	@PostMapping("/updateTask")
	fun updateTask(@RequestParam(name = "taskId",required = true) taskId:Int,
				@RequestParam(name = "title",required = true) title:String,
				@RequestParam(name = "detail",required = true) detail:String,
				@RequestParam(name = "selectedUserId", required = true) selectedUserId:Int,
				model:Model):String {
		val task:Task? = taskRepository?.findById(taskId)?.get()
		if(task !=null) {
			task.setTitle(title)
			task.setDetail(detail)
			task.setAssignedTo(selectedUserId)
			taskRepository?.save(task)
		}

		logger.info("Going to show Add task page")
		model.addAttribute("screenTitle","Edit Task")
		setProcessingData(model, TaskMgmntConstant.TITLE_ADD_TASK_PAGE)
		return "redirect:allTaskList"
	}

	@PostMapping("/addTaskComment")
	fun addTask(@RequestParam(name = "taskId",required = true) taskId:Int,
				@RequestParam(name = "taskComment",required = true) taskComment:String,
				model:Model):String {
		val currentTask:Task? = taskRepository?.findById(taskId)?.get()
		if(currentTask !=null) {

			val principal = SecurityContextHolder.getContext().authentication.principal
			if (principal is CustomUserPrinciple) {
				val user = principal.getUser()

				var existingComments: MutableSet<Comments>? = currentTask.getComments()
				var comment:Comments?

				if(existingComments == null || existingComments.isEmpty()) {
					existingComments = mutableSetOf() // Inmitialize empty hash set
				}
					comment = Comments()
					comment.setTask(currentTask)
					if(user !=null) comment.setUser(user)
					comment.setComment(taskComment)
					comment = commentRepository?.save(comment)

					if(comment !=null) {
						existingComments.add(comment)
					}
					currentTask.setComments(existingComments)

					taskRepository?.save(currentTask)
			}
		}
		return "redirect:viewTask?taskId=$taskId"
	}



	@GetMapping("/getAllUsers")
	fun getUsers(model:Model):String{
		val users: List<User>? = userRepository?.findAll()
		model.addAttribute("users",users)
		return "users"
	}

	@GetMapping("/allTaskList")
	fun showAllTaskList(@RequestParam(name = "myTask",required = false) myTask:String?,
						model:Model):String{
		var taskLst:  List<Array<Any>>? = null

		if("true" == myTask){
			//get current user Id from Spring context
			val principal = SecurityContextHolder.getContext().authentication.principal
			if (principal is CustomUserPrinciple) {
				val user = principal.getUser()
				if(user !=null){
					taskLst = taskRepository?.findMyTasks(user.getId() ?: 0)
				}
				model.addAttribute("screenTitle","My Tasks")
			}
		}else {
			taskLst = taskRepository?.findAllTasks()
			model.addAttribute("screenTitle","All Tasks")
		}

		val taskDtoLst:MutableList<TaskDTO> = ArrayList()

		var taskDto:TaskDTO?

		for(row in taskLst.orEmpty()){
			taskDto = TaskDTO(row[0] as Int,row[1] as String,row[2] as String,null, row[3] as String,row[4] as String,null)
			taskDtoLst.add(taskDto)
		}

		model.addAttribute("tasks",taskDtoLst)
		return "task-list"
	}

	@GetMapping("/viewTask")
	fun viewTask(@RequestParam(name = "taskId",required = true) taskId:Int,model:Model):String{
		val selectedTask:Task? = taskRepository?.findById(taskId)?.get()
		val user:User? = userRepository?.findById(selectedTask?.getAssignedTo() ?: 0)?.get()

		val taskDto= TaskDTO(selectedTask?.getId(),selectedTask?.getTitle(),
				selectedTask?.getDetail(),selectedTask?.getAssignedTo(),
				(user?.getFirstname() + " "+ user?.getLastname()),selectedTask?.getStatus(),selectedTask?.getComments())

		val commentLst:  List<Array<Any>>? = commentRepository?.findByTaskId(taskId)
		val commentDtoLst:MutableList<CommentDTO> = ArrayList()

		var commentDto:CommentDTO?

		for(row in commentLst.orEmpty()){
			commentDto = CommentDTO(row[0] as Int,row[1] as String,row[2] as String)
			commentDtoLst.add(commentDto)
		}

		model.addAttribute("task",taskDto)
		model.addAttribute("taskComments",commentDtoLst)
		model.addAttribute("screenTitle","Add Task Comment")
		return "task-view"
	}

	@PostMapping("/deleteTask")
	fun deleteTask(@RequestParam(name = "taskId",required = true) taskId:Int,model:Model):String{
		val selectedTask:Task? = taskRepository?.findById(taskId)?.get()
		if(selectedTask !=null) {
			taskRepository?.delete(selectedTask)
		}
		return "redirect:allTaskList"
	}

	/**
	 * This method will check if current logged in user has given role
	 * @param roleName
	 * @return true or false - if user has given role
	 */
	private fun checkIfUserHasRole(roleName: String): Boolean {
		return SecurityContextHolder.getContext().authentication.authorities.stream()
				.anyMatch { r -> r.authority == roleName }
	}

	/**
	 * This method will check if valid user is logged in.
	 * @return boolean if user is logged In
	 */
	@ModelAttribute("validUserLogin")
	fun isUserLoggedIn(): Boolean {
		return SecurityContextHolder.getContext().authentication != null && SecurityContextHolder.getContext().authentication.isAuthenticated &&
				//when Anonymous Authentication is enabled
				SecurityContextHolder.getContext().authentication !is AnonymousAuthenticationToken
	}

	/**
	 * This method will return current user name
	 * @return
	 */
	@ModelAttribute("currentUserName")
	fun getCurrentUserName(): String {
		return SecurityContextHolder.getContext().authentication.name

	}

	@ModelAttribute("hasAdminRole")
	fun hasAdminRole(): Boolean {
		return checkIfUserHasRole(TaskMgmntConstant.ROLE_ADMIN)

	}

	/**
	 * This method stores various data which are required on presentation layer.
	 * 
	 */
	private fun setProcessingData(model:Model,pageTitle:String ) {
		model.addAttribute(TaskMgmntConstant.PAGE_TITLE, pageTitle)
	}
}