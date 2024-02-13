package ru.hse.edu.tukach.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "bearer ";
    private static final Integer BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   RestAuthenticationFailureHandler restAuthenticationFailureHandler) {
        super("/api/**", authenticationManager);
        setAuthenticationFailureHandler(restAuthenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        validateAuthorizationHeader(authorizationHeader);

        var jwtAuthToken = new JwtAuthToken(stripBearerPrefix(authorizationHeader));
        return getAuthenticationManager().authenticate(jwtAuthToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    private void validateAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.toLowerCase().startsWith(BEARER_PREFIX)) {
            throw new BadCredentialsException("Missing Bearer token");
        }
    }

    private String stripBearerPrefix(String authorizationHeader) {
        return authorizationHeader.substring(BEARER_PREFIX_LENGTH);
    }

}
