package com.example.course_project_2023.config;

import com.example.course_project_2023.service.security.JwtRequestFilter;
import com.example.course_project_2023.service.security.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.example.course_project_2023.service.security.Permission.ADMIN_SPECIFIC_PERMISSION;
import static com.example.course_project_2023.service.security.Permission.USER_SPECIFIC_PERMISSION;


@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            UserDetailServiceImpl userService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8077", "http://localhost:80", "https://knowyourteacher.online"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Location");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/knowyourteacher-api/v1/v1/security_checks/authorized_as_Admin_get").hasAuthority(ADMIN_SPECIFIC_PERMISSION.toString());
                    auth.requestMatchers("/knowyourteacher-api/v1/v1/security_checks/authorized_as_Admin_post").hasAuthority(ADMIN_SPECIFIC_PERMISSION.toString());
                    auth.requestMatchers("/knowyourteacher-api/v1/v1/security_checks/authorized_as_User_get").hasAuthority(USER_SPECIFIC_PERMISSION.toString());
                    auth.requestMatchers("/knowyourteacher-api/v1/v1/security_checks/authorized_as_User_post").hasAuthority(USER_SPECIFIC_PERMISSION.toString());
                    auth.requestMatchers("/knowyourteacher-api/v1/reviews/{id:\\d+}/like").authenticated();
                    auth.requestMatchers("/knowyourteacher-api/v1/reviews/{id:\\d+}/unlike").authenticated();
                    auth.requestMatchers("/knowyourteacher-api/v1/reviews/{id:\\d+}/dislike").authenticated();
                    auth.requestMatchers("/knowyourteacher-api/v1/reviews/{id:\\d+}/undislike").authenticated();
                    auth.requestMatchers("/knowyourteacher-api/v1/teachers/{id:\\d+}/review").authenticated();
                    auth.requestMatchers("/knowyourteacher-api/v1/teachers/create").authenticated();
                    auth.anyRequest().permitAll();

                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(
//                        ex->ex.defaultAuthenticationEntryPointFor(
//                                new LoginUrlAuthenticationEntryPoint("/login"),
//                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                        ))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

}
