package sdapi.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sdapi.entities.User;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class JWTAuthentication implements Authentication {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getProfile() == 2)
            return List.of(new SimpleGrantedAuthority("ADMIN"));
        else
            return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return null;
    }
    // rest of the code omitted
}
