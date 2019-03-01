package com.bookstore.catalog.cofig;

/* To test Dynamic configuration, we commented out this class. If you want to implement OAuth, just uncomment this */
/*@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	private static final String RESOURCE_ID = "oauth2-server";
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources
		.tokenStore(tokenStore())
		.resourceId(RESOURCE_ID);
	}
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/api/catalog/**").permitAll()
		.antMatchers("/api/test/**").permitAll()
		.antMatchers("/api/**").authenticated()
		.and().exceptionHandling().accessDeniedHandler(new
				OAuth2AccessDeniedHandler());
	}
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}
}*/
