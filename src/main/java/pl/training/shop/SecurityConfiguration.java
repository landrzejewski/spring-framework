package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDetails userDetails() {
        return User
                .withUsername("jan")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN", "USER")
                .authorities("read", "write")
                .build();

    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var user = userDetails();
            if (username.equals(user.getUsername())) {
                return user;
            } else {
                throw new UsernameNotFoundException(username);
            }
        };
    }*/

   /* @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        // return new InMemoryUserDetailsManager(userDetails());
        var manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select username, password, enabled from users where username = ?");
        manager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username = ?");
        return manager;
    }*/

}
