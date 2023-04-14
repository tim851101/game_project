package webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
            .disable();
//            .authorizeRequests()
//            .requestMatchers("/foreground/my-account.html")
//            .authenticated()
//            .and()
//            .oauth2Login();
        http.authorizeHttpRequests().anyRequest().permitAll();
        return http.build();
    }
}