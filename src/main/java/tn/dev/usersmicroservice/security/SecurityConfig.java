package tn.dev.usersmicroservice.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationManager authManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Cross-Site Request Forgery (CSRF) protection is a security feature that helps prevent unauthorized commands from being transmitted from a user that the web application trusts.
            .csrf(csrf -> csrf.disable())
            // Disable Cross-Origin Resource Sharing (CORS) protection
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration corsConfig = new CorsConfiguration();
                corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                corsConfig.setAllowedMethods(Collections.singletonList("*"));
                corsConfig.setAllowedHeaders(Collections.singletonList("*"));
                corsConfig.setExposedHeaders(Collections.singletonList("Authorization"));
                return corsConfig;
            }))
            .authorizeHttpRequests( requests -> requests
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/register").permitAll()
                    .requestMatchers("/all").hasAuthority("ADMIN")
                    .anyRequest().authenticated())
            // Add a JWT authentication filter
            .addFilterBefore(new JWTAuthenticationFilter(authManager), UsernamePasswordAuthenticationFilter.class)
            // Add a JWT authorization filter
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
