package com.molveno.restaurantReservation.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers( header -> header.frameOptions( options -> options.sameOrigin()));
        http.csrf( csrf -> csrf.disable())
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/i18n/**").permitAll()
                        .requestMatchers(
                                "/",
                                "/login",
                                "/error",
                                "/login**"
                                , "/h2-console/**"
                                , "/console/**"
                                , "/js/**"
                                , "/css/**"
                                , "/images/**"
                                , "/api/**"
                                ,"/v3/api-docs/**"
                                ,"/swagger-ui.html"
                                ,"/swagger-ui/**",
                                "/reset-password*",
                                "/password-request*",
                                "/webjars/**"

                                ).permitAll()
                        .requestMatchers("/orders*","/home").hasAnyAuthority("Front desk", "Admin", "Waiter", "Chef")
                        .requestMatchers("/reservation*").hasAnyAuthority( "Front desk", "Admin")
                        .requestMatchers( "/tables*","/users*").hasAnyAuthority("Admin")
                        .requestMatchers("/stock*", "/menu*", "category*", "menuItemStock*").hasAnyAuthority("Chef")
                        .anyRequest().authenticated()
                ).formLogin( login -> login
                        .loginPage("/login")
                        .usernameParameter("username")
                        .defaultSuccessUrl("/home",true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout( logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .permitAll())
        ;

        return http.build();
    }
//    //@Bean
//    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
//        http.headers( header -> header.frameOptions( options -> options.sameOrigin()));
//        http.csrf( csrf -> csrf.disable())
//                .authorizeHttpRequests( auth -> auth
//                        .requestMatchers("/"
//                                ,"/home"
//                                , "/h2-console/**"
//                                , "/console/**"
//                                , "/contact"
//                                , "/blog"
//                                , "/js/**"
//                                , "/css/**"
//                                , "/images/**"
//                                , "/api/**"
//                                ,"/v3/api-docs/**",
//                                "/swagger-ui.html",
//                                "/swagger-ui/**",
//                                "/webjars/**"
//                        ).permitAll()
//                        .requestMatchers("/admin*").hasAnyAuthority( "Admin")
//                        .requestMatchers("/chef*").hasAnyAuthority("Chef","Admin")
//                        .requestMatchers("/frontDesk*").hasAnyAuthority("front desk","Chef","Admin")
//                        .requestMatchers("/waiter*").hasAnyAuthority("Waiter","Admin")
//                        .anyRequest().authenticated()
//                         )
//                .formLogin( login -> login
//                        .loginPage("/login")
//                        .usernameParameter("username")
//                        .defaultSuccessUrl("/home",false)
//                        .failureUrl("/login-fail")
//                        .permitAll())
//                .logout( logout -> logout
//                        .logoutSuccessUrl("/home")
//                        .invalidateHttpSession(true)
//                        .permitAll())
//        ;
//
//        return http.build();
//    }
}

