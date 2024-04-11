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

    public UserDetails user() {
        return User.withUsername("jan")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN", "TESTER") // ROLE_ADMIN
                .authorities("read", "write")
                .build();
    }

   /* @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (!username.equals("jan")) {
                throw new UsernameNotFoundException(username);
            }
            return user();
        };
    }*/

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        // return new InMemoryUserDetailsManager(user());

        var manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select username, password, enabled from app_users where username = ?");
        manager.setAuthoritiesByUsernameQuery("select username, authority from app_users_authorities where username = ?");
        return manager;
    }

}
