package com.ojas.security.gateway;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ojas.security.common.JwtAuthenticationConfig;
import com.ojas.security.common.JwtTokenAuthenticationFilter;

/**
 * Config role-based auth.
 *
 * 
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationConfig config;

	private static final String ALLROLES = "hasRole('USER') or hasRole('ADMIN') or hasRole('HR') or hasRole('MANAGER') or hasRole('SALES') or hasRole('BDM') or hasRole('BUHEAD') or hasRole('SBUHEAD') or hasRole('DM') or hasRole('FINANCE') or hasRole('BDMHEAD') or hasRole('STAFFAUGHEAD')";

	@Bean
	public JwtAuthenticationConfig jwtConfig() {
		return new JwtAuthenticationConfig();
	}


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and()
				.csrf()
				.disable().logout().disable().formLogin().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().anonymous().and().exceptionHandling()
				.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
				.addFilterAfter(new JwtTokenAuthenticationFilter(config), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers(config.getUrl()).permitAll()
				/*
				 * .antMatchers("/obs/projectDetails/set").permitAll()
				 * .antMatchers("/obs/projectDetails/get").permitAll()
				 * .antMatchers("/obs/employmentDetails/set").permitAll()
				 * .antMatchers("/obs/employmentDetails/get").permitAll()
				 */
				.antMatchers("/obs/forgot/set").permitAll()
				.antMatchers("/obs/master/*/set").access("hasRole('ADMIN') or hasRole('HR')")
				.antMatchers("/obs/master/*/get").access(ALLROLES).antMatchers("/obs/*/set").access(ALLROLES)
				.antMatchers("/obs/*/get").access(ALLROLES).antMatchers("/backend/admin").hasRole("ADMIN")
				.antMatchers("/backend/user").hasRole("USER").antMatchers("/backend/guest").permitAll();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
