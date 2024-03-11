package ru.hse.edu.tukach.service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.hse.edu.tukach.model.user.User;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    @Value("${rest-security.jwt-key}")
    private String jwtKey;

    public Optional<User> getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(auth)
            .map(Authentication::getPrincipal)
            .map(User.class::cast);
    }

    public User getAbstractUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String generateJwt() {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, jwtKey)
            .setExpiration(DateUtils.addMinutes(new Date(), 60))
            .compact();
    }

}
