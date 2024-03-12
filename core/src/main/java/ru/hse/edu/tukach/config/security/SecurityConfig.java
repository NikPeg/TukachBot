package ru.hse.edu.tukach.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.hse.edu.tukach.service.security.UserAuthDetailsService;

import java.time.Duration;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;
    private final UserAuthDetailsService userDetailsService;

    @Value("${rest-security.jwt-key}")
    private String jwtKey;
    @Value("${rest-security.jwt-duration}")
    private Duration jwtDuration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/api/**").authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(characterEncodingFilter(), JwtAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web
            .ignoring()
            .antMatchers("/api/openapi.yaml")
            .antMatchers("/api/api-docs")
            .antMatchers("/api/v1/security/generate-jwt");
    }

    @Override
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider()));
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(authenticationManager(), restAuthenticationFailureHandler());
    }

    private CharacterEncodingFilter characterEncodingFilter() {
        return new CharacterEncodingFilter("UTF-8", true);
    }

    private RestAuthenticationFailureHandler restAuthenticationFailureHandler() {
        return new RestAuthenticationFailureHandler(objectMapper);
    }

    private JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtKey, jwtDuration);
    }

    /*@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, NoOpPasswordEncoder noOpPasswordEncoder)
        throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(noOpPasswordEncoder);
        return builder.build();
    }

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }*/
}
