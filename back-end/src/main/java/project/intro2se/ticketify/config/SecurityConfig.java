package project.intro2se.ticketify.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.intro2se.ticketify.config.filter.JwtFilter;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        });
        http.authorizeHttpRequests().requestMatchers(
                "topic/public",
                "chat.sendMessage",
                "/auth/**",
                "/tickets/**",
                "/theaters/**",
                "/swagger-ui/**",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/webjars/**",
                "/swagger-ui.html").permitAll();
        http.authorizeHttpRequests().requestMatchers(HttpMethod.GET, "showtimes").hasAnyRole("ADMIN", "TICKET_MANGER", "CUSTOMER", "ANONYMOUS");
        http.authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "showtimes").hasRole("TICKET_MANAGER");



        http.authorizeHttpRequests().requestMatchers(HttpMethod.DELETE, "showtimes/**").hasRole("TICKET_MANAGER");
        http.authorizeHttpRequests().requestMatchers("transactions/history", "transactions/confirm-booking").hasRole("CUSTOMER");
        http.authorizeHttpRequests().requestMatchers(   HttpMethod.GET, "movies").hasAnyRole("CUSTOMER", "ANONYMOUS");
        http.authorizeHttpRequests().requestMatchers("movies/manager/**").hasRole("TICKET_MANAGER");
        http.authorizeHttpRequests().requestMatchers(HttpMethod.POST, "movies").hasRole("TICKET_MANAGER");
        http.authorizeHttpRequests().requestMatchers("movies/**").hasRole("TICKET_MANAGER");
        http.authorizeHttpRequests().requestMatchers(HttpMethod.GET, "comments").permitAll();
        http.authorizeHttpRequests().requestMatchers(HttpMethod.POST, "comments").hasRole("CUSTOMER");
        http.authorizeHttpRequests().requestMatchers("/checkout/**").hasRole("CUSTOMER");
        http.authorizeHttpRequests().requestMatchers("/users/**").hasRole("ADMIN");
        http.authorizeHttpRequests().requestMatchers("rooms/**").hasAnyRole("ADMIN", "STAFF", "TICKET_MANAGER");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.authenticationProvider(authenticationProvider).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
