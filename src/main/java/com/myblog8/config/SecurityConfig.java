package com.myblog8.config;

import com.myblog8.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static java.util.Base64.getEncoder;
import static org.springframework.security.config.Customizer.withDefaults;
//implementing basic authentication.
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true),alternate for this.
@EnableMethodSecurity(prePostEnabled = true)//which method can be accessed by whom?in controller layer,and before adding @preauthorize ensure that this annotation is enabled.
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    //  @Autowired
//   private AuthenticationManager authenticationManager;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
               //.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        //.requestMatchers( "/api/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                        .anyRequest()
                        .authenticated()


                )
                .httpBasic(withDefaults());
        DefaultSecurityFilterChain build = http.build();
        return build;
    }

    //    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        AuthenticationManager authenticationManager = configuration.getAuthenticationManager();
        return authenticationManager;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails user = User.builder().username("pankaj").password(getEncoder().encode("password")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(getEncoder().encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }
//
//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//
//
//    }
}





