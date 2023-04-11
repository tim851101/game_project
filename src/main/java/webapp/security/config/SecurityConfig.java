package webapp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
        "/test/**",
        "/auth/**",
        "/background/**"
    };

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(PUBLIC).permitAll()
            .requestMatchers("/free.html", "/login.html", "/home.html", "/management").permitAll()
            .requestMatchers("/charge.html").hasAuthority("USER")
            .requestMatchers("/admin.html").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.DELETE)
            .hasAuthority("ADMIN")    // If UserDetails.getAuthorities return [ADMIN, ...]

            .anyRequest().authenticated()
//            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/login.html")
            .defaultSuccessUrl("/home.html")
            .permitAll()
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    private AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}
