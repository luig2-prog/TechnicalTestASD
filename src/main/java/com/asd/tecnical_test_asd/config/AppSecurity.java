package com.asd.tecnical_test_asd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * Esta clase define objetos que contienen tantos enteros aleatorios entre 0 y 1000 como se le definen al crear un objeto
 * @author: Luis Hernandez
 * @version: 22/09/2016/A
 * @see <a href = "http://www.aprenderaprogramar.com" /> aprenderaprogramar.com – Didáctica en programación </a>

 */


@Configuration
@EnableWebSecurity
public class AppSecurity extends WebSecurityConfigurerAdapter {

	/**
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.cors().and()
				.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
//				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/user/login").permitAll()
//				.antMatchers(HttpMethod.PUT, "/**").permitAll()
//				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//				.antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
//				.antMatchers(HttpMethod.GET, "/").permitAll()
//				.antMatchers(HttpMethod.GET, "/api/user/my-ip").permitAll()
				.anyRequest().authenticated()
//				.and()
//				.headers()
//			    .frameOptions().disable()
//				.contentTypeOptions()
//				.and()
//				.xssProtection()
//				.and()
//				.cacheControl()
//				.and()
//				.httpStrictTransportSecurity()
//				.and()
//				.contentSecurityPolicy("script-src, style-src, img-src, conecta-src, frame-src, frame-ancestors, font-src, media-src, object-src, manifest-src, prefetch-src, form-action 'self'")
//				.and()
//	            .addHeaderWriter(new StaticHeadersWriter("Feature-Policy", "vibrate 'none'; usermedia 'none'"))
				;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
