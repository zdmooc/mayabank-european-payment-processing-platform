package com.mayabank.processing.api;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
  @Bean
  @Profile("secured")
  SecurityFilterChain secured(HttpSecurity http) throws Exception {
    return http.csrf(csrf->csrf.disable())
      .authorizeHttpRequests(a->a
        .requestMatchers(HttpMethod.GET,"/actuator/**").permitAll()
        .requestMatchers(HttpMethod.GET,"/api/v1/card-payments/**").hasAuthority("SCOPE_payments.read")
        .requestMatchers("/api/v1/card-payments/**").hasAuthority("SCOPE_payments.write")
        .anyRequest().authenticated())
      .oauth2ResourceServer(o->o.jwt(jwt->{})).build();
  }

  @Bean
  @Profile("!secured")
  SecurityFilterChain local(HttpSecurity http) throws Exception {
    return http.csrf(csrf->csrf.disable()).authorizeHttpRequests(a->a.anyRequest().permitAll()).build();
  }
}