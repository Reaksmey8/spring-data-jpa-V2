package co.istad.springdatajpa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // exspoke methods filterChain
    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) {
        // TODO: Define security configuration
        // 1. security Mechanism -> Http Basic Authentication (username, password)
        http.httpBasic(Customizer.withDefaults());

        // 2. Session stateLess
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 3. Endpoint security
        http.authorizeHttpRequests(endpoint -> endpoint
                .requestMatchers(HttpMethod.GET, "/api/v1/products", "/api/v1/products/**").permitAll()
                .anyRequest().authenticated()
        );

        //4. Disable CSRF token
        http.csrf(token -> token.disable());
//        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // Configuration security user with database
    @Bean
    public DaoAuthenticationProvider daoAuthProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
}
