package com.fastcampus.deliverystoreadmin.config.security

import com.fastcampus.deliverystoreadmin.common.HttpConstants
import com.fastcampus.deliverystoreadmin.domain.store.StoreRole
import com.fastcampus.deliverystoreadmin.security.CustomAccessDeniedHandler
import com.fastcampus.deliverystoreadmin.security.CustomAuthenticationEntryPoint
import com.fastcampus.deliverystoreadmin.security.JwtAuthenticationFilter
import com.fastcampus.deliverystoreadmin.security.JwtExceptionFilter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider,
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter,
        jwtExceptionFilter: JwtExceptionFilter,
    ): DefaultSecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/hello", "/home", "/",
                        "/auth/**",
                        "/refresh",
                        "/error",
                        "/onboarding/**",
                        "/js/**", "/css/**", "/images/**", "/plugins/**",
                        "favicon.ico",
                        "/kafka/publish",
                    )
                    .permitAll()
                    .requestMatchers("/store/**")
                    .hasRole(StoreRole.OWNER.name)
                    .anyRequest()
                    .fullyAuthenticated()
            }
            .formLogin {
                it.usernameParameter("email")
                    .passwordParameter("password")
                    .loginPage("/auth/login")
                    .defaultSuccessUrl("/dashboard")
                    .failureUrl("/auth/login?error=true")
            }
            .logout {
                it.logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth/login")
                it.invalidateHttpSession(true).deleteCookies(HttpConstants.COOKIE_NAME_ACCESS_TOKEN)
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling { it.accessDeniedPage("/error") }
            .exceptionHandling { it.accessDeniedHandler(CustomAccessDeniedHandler()) }
            .exceptionHandling { it.authenticationEntryPoint(CustomAuthenticationEntryPoint()) }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().applyPermitDefaultValues()
        configuration.allowedHeaders = listOf("*")
        configuration.allowedOrigins = listOf("http://localhost:20000")
        configuration.allowedMethods = listOf("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}