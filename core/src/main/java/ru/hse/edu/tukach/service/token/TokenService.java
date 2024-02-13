package ru.hse.edu.tukach.service.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${rest-security.jwt-key}")
    private String jwtKey;

    public String generateJwt() {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, jwtKey)
            .setExpiration(DateUtils.addMinutes(new Date(), 60))
            .compact();
    }
}
