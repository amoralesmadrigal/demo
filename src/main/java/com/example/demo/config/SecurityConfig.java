package com.example.demo.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
	
	@Value("${allowed.origin}")
    private String allowedOrigin;
	
	 @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configura CORS
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF si no lo necesitas (por ejemplo, en APIs REST)
	            .headers(headers -> headers
	                .xssProtection(xss -> xss.disable() // Activa X-XSS-Protection con modo "block"
	                )
	                .contentTypeOptions(contentType -> contentType.disable() // Configura X-Content-Type-Options: nosniff
	                )
	                .frameOptions(frame -> frame
	                    .sameOrigin() // Configura X-Frame-Options: SAMEORIGIN
	                )
	                .contentSecurityPolicy(csp -> csp
	                    .policyDirectives("default-src 'self'; connect-src 'self'") // Configura Content-Security-Policy
	                )
	            );
	        return http.build();
	    }
	 
	 /**
     * Configuración personalizada de CORS
     */
    @Bean
   CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(allowedOrigin)); // Dominios permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization")); // Encabezados permitidos
        configuration.setMaxAge(3600L); // Cache de preflight por 3600 segundos (1 hora)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
        return source;
    }
}
