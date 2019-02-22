package com.nilangpatel.tms.constant

object TaskMgmntConstant {
    const val ROLE_USER :String = "ROLE_USER"
	const val ROLE_ADMIN :String = "ROLE_ADMIN"

	const val TITLE_HOME_PAGE: String = "Home"
	const val TITLE_LOGIN_PAGE: String = "Login"
	const val TITLE_LANDING_CONTROL_PAGE:String = "Control Page"
	const val TITLE_ADD_TASK_PAGE:String = "Add Task"
	const val TITLE_UPDATE_TASK_PAGE:String = "Update Task"
	const val PAGE_TITLE: String = "pageTitle"

	fun getRolesLst():List<String>{
		return listOf(ROLE_ADMIN, ROLE_USER)
	}
}


	

