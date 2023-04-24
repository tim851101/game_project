package webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import webapp.security.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] PUBLIC = new String[] {
        "/auth/**",
        "/management",
        "/background/**",
        "foreground/**"
    };

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(PUBLIC).permitAll()
            // .anyRequest().authenticated()
            .requestMatchers("/mem/ls-dtos", "/mem/save-all-auth").authenticated()
            .anyRequest().permitAll();


        httpSecurity
            .formLogin()
            .loginPage("/background/login")
            .defaultSuccessUrl("/management")
            .permitAll();

        httpSecurity
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    private AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}
