package br.com.intelligym.service.authentication;

import br.com.intelligym.model.user.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("skillscan")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
           return JWT.require(algorithm)
                    .withIssuer("skillscan")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
           return "";
        }
    }

    private Date genExpirationDate() {
        Instant instant = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        return Date.from(instant);
    }
}
