package com.nilangpatel.tms.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import com.nilangpatel.tms.constant.TaskMgmntConstant
import com.nilangpatel.tms.service.CustomUserDetailsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import javax.sql.DataSource
import org.springframework.security.authentication.dao.DaoAuthenticationProvider




@Configuration
@EnableWebSecurity
@ComponentScan("com.nilangpatel.tms.security")
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    private var logger: Logger =  LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private var dataSource: DataSource? = null

    @Autowired
    private var userDetailService: CustomUserDetailsService? = null

    @Value("\${spring.queries.users-query}")
    private val usersQuery: String? = null

    @Value("\${spring.queries.roles-query}")
    private val rolesQuery: String? = null

    @Throws(Exception::class)
    override fun configure(web: WebSecurity){
        web.ignoring().antMatchers("/js/**")
        web.ignoring().antMatchers("/css/**")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        logger.info("************************ Configuring security *****************************")
        http.authorizeRequests()
            .antMatchers("/","/login","/api/register").permitAll()
            .antMatchers("/controlPage/**","/getAllUsers/**",
                    "/allTaskList/**","/addTaskComment/**","/viewTask/**")
                    .hasAnyAuthority(TaskMgmntConstant.ROLE_USER,TaskMgmntConstant.ROLE_ADMIN)
            .antMatchers("/showAddTask/**","/showEditTask/**",
                        "/addTask/**","/updateTask/**","/deleteTask/**")
                .hasAnyAuthority(TaskMgmntConstant.ROLE_ADMIN)
            .and()
         .formLogin().loginPage("/login").permitAll()
            .defaultSuccessUrl("/controlPage",true)
            .failureUrl("/login?error=true")
         .and().csrf().disable()
            .logout()
            .permitAll().logoutSuccessUrl("/login?logout=true")
    }

    // Use this method for Query based approach to fetch user details
    /*@Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
    }*/

    /* Use below two methods for custom user detail service approach to fetch user details */
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(authenticationProvider())
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
	
}