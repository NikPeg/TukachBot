package ru.hse.edu.tukach.config.security;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class JwtAuthToken implements Authentication {

    private String jwt;
    private Claims tokenClaims;
    private boolean authenticated;

    public JwtAuthToken(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String getName() {
        return tokenClaims == null ? null : tokenClaims.getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return tokenClaims == null ? null : tokenClaims.toString();
    }

    @Override
    public Object getPrincipal() {
        return tokenClaims == null ? null : tokenClaims.getSubject();
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

}
