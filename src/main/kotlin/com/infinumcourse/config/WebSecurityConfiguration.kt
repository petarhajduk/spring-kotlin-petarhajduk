package com.infinumcourse.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
class WebSecurityConfiguration {

    @Bean
    fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain{

        http {
            cors{}
            csrf{ disable() }
            authorizeRequests {
                authorize("/add-car", hasAuthority("SCOPE_admin"))
                authorize("/add-car", hasAuthority("SCOPE_user"))
                authorize("/get-all-cars", hasAuthority("SCOPE_admin"))
                authorize("/get-car/{id}", hasAuthority("SCOPE_admin"))
                authorize("/get-car/{id}", hasAuthority("SCOPE_user"))
                authorize("/add-check-up", hasAuthority("SCOPE_admin"))
                authorize("/get-all-checkups-paged", hasAuthority("SCOPE_admin"))
                authorize("/get-all-checkups-paged", hasAuthority("SCOPE_user"))
                authorize("/delete-car", hasAuthority("SCOPE_admin"))
                authorize("/delete-check-up", hasAuthority("SCOPE_admin"))
            }
            oauth2ResourceServer {
                jwt {}
            }
        }

        return http.build()
    }
}