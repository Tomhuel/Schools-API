package crosso.workshop.schools_api.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails teacher = User.builder()
                .roles("TEACHER")
                .username("John")
                .password(passwordEncoder.encode("Doe123"))
                .build();

        UserDetails student = User.builder()
                .roles("STUDENT")
                .username("MiniJohn")
                .password(passwordEncoder.encode("Doe123"))
                .build();

        return new InMemoryUserDetailsManager(teacher, student);
    }
}
