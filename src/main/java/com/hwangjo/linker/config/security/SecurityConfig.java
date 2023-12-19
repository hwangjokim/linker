package com.hwangjo.linker.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hwangjo.linker.service.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)  throws Exception{
		httpSecurity
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/test").authenticated()
				.requestMatchers("/admin").hasRole("ADMIN")
				.anyRequest().permitAll())
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll().defaultSuccessUrl("/", true))
			.csrf((csrf) -> csrf.disable())
			.userDetailsService(userDetailsService);
		return httpSecurity.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
