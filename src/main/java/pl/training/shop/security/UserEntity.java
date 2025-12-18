package pl.training.shop.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Table(name = "users")
@Entity(name = "user")
@Setter
@Getter
@Builder
@ToString(of = {"username", "enabled"})
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails, CredentialsContainer {

    private static final String ROLE_SEPARATOR = ",";

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private boolean verified;
    private String roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(ROLE_SEPARATOR))
                .map(String::trim)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public void eraseCredentials() {
        password = "";
    }

}
