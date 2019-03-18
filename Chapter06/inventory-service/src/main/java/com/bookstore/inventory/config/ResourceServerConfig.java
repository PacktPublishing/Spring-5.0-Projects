package com.bookstore.inventory.config;

/* Enable this when you want to implement spring security with OAuth */
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
		.requestMatcher(new RequestHeaderRequestMatcher("Authorization"))
		.authorizeRequests()
		.antMatchers("/api/inventory/**").permitAll()
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
