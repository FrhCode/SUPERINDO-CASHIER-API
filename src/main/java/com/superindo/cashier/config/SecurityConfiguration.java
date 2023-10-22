package com.superindo.cashier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(crsf -> crsf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/api/v1/auth/**").permitAll()
						.requestMatchers("/images/**").permitAll()
						.requestMatchers("/uploads/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/checkout/*/download").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/product_categories").hasAuthority("administrator")
						.requestMatchers(HttpMethod.PUT, "/api/v1/product_categories").hasAuthority("administrator")
						.requestMatchers(HttpMethod.PUT, "/api/v1/product_variants").hasAuthority("administrator")
						.requestMatchers(HttpMethod.POST, "/api/v1/products/*/variants").hasAuthority("administrator")
						.requestMatchers(HttpMethod.PUT, "/api/v1/products/*/variants").hasAuthority("administrator")
						.requestMatchers(HttpMethod.POST, "/api/v1/products").hasAuthority("administrator")
						.requestMatchers(HttpMethod.POST, "/api/v1/upload/image").hasAuthority("administrator")
						.requestMatchers(HttpMethod.GET, "/api/v1/overview/total-transaction-ammount").hasAuthority("administrator")
						.requestMatchers(HttpMethod.GET, "/api/v1/overview/total-product").hasAuthority("administrator")
						.requestMatchers(HttpMethod.GET, "/api/v1/overview/total-variant").hasAuthority("administrator")
						.requestMatchers(HttpMethod.GET, "/api/v1/overview/latest-transaction").hasAuthority("administrator")
						.requestMatchers(HttpMethod.GET, "/api/v1/overview/total-transaction-count").hasAuthority("administrator")
						.anyRequest().authenticated())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
