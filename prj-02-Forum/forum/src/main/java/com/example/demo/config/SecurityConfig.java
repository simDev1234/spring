package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// 외부에서 호출이 되기 위해서는 아래 두가지 설정이 필요하다.
		// - HTTP 요청 시, CSRF 토큰 생성 해제
		http.csrf().disable();
		// - HTTP 요청 헤더에, "X-Forwarded-For" 처럼 사용자 정보를 얻을 수 있는 옵션 유지
		http.headers().frameOptions().sameOrigin();

		http.authorizeHttpRequests()
			.anyRequest()
			.permitAll();

	}
	
}
