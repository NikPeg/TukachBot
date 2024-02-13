package ru.hse.edu.tukach.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final String jwtKey;
    private final Duration jwtDuration;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthToken jwtAuthToken = (JwtAuthToken) authentication;
        Claims jwtBody;
        try {
            jwtBody = Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(jwtAuthToken.getJwt())
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("JWT has expired");
        } catch (Exception e) {
            logger.error("JWT is not valid", e);
            throw new BadCredentialsException("JWT is not valid");
        }

        validateExpirationDate(jwtBody.getExpiration());

        logger.debug("Authentication is successful");

        jwtAuthToken.setTokenClaims(jwtBody);
        jwtAuthToken.setAuthenticated(true);
        return jwtAuthToken;
    }

    @Override
    public boolean supports(Class<?> authClass) {
        return JwtAuthToken.class.isAssignableFrom(authClass);
    }

    private void validateExpirationDate(Date expirationDate) {
        if (expirationDate == null) {
            throw new BadCredentialsException("JWT does not contain expiration date");
        }

        LocalDateTime exp = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (LocalDateTime.now().plus(jwtDuration).isBefore(exp)) {
            throw new BadCredentialsException("JWT expiration date should not exceed %s".formatted(jwtDuration));
        }
    }

}
