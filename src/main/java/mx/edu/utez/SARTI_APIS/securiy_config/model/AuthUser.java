package mx.edu.utez.SARTI_APIS.securiy_config.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import mx.edu.utez.SARTI_APIS.modules.user.model.User;

import java.util.Collection;
import java.util.Set;

public class AuthUser implements UserDetails {
    private final String username;
    private final String password;
    private final boolean blocked;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUser(String username, String password, boolean blocked, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.blocked = blocked;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static AuthUser build(User user){
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.getRole().name()));

        return new AuthUser(
                user.getEmail(), user.getPassword(),
                user.getBlocked(), user.getStatus(), authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
