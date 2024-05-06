package tech.lostgame.test.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tech.lostgame.test.security.filter.LostGameAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val lostGameAuthenticationFilter: LostGameAuthenticationFilter
) {

    private val apiPath = "/open-api-games/v1/games-processor/**"

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(lostGameAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.POST, apiPath).authenticated()
                    .anyRequest().permitAll()
            }
            .build()
    }
}