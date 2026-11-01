package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenJwtService {

    @Value("${api.security.token.secret}")
    private String palavraSecreta;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritimo = Algorithm.HMAC256(palavraSecreta);
            String tokenJWT = JWT.create()
                    .withIssuer("CorteAqui-Backend")
                    .withSubject(usuario.getNomeUsuario())
                    .withExpiresAt(Instant.now().plusSeconds(1000))
                    .sign(algoritimo);

            return tokenJWT;
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Erro durante a geração do token", e);
        }
    }


    public String validarToken(String token) {
        try {
            Algorithm algoritimo = Algorithm.HMAC256(palavraSecreta);
            return JWT.require(algoritimo)
                    .withIssuer("CorteAqui-Backend")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Erro durante a geração do token", e);
        }
    }
}
