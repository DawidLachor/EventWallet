package pl.skorpjdk.walletproject.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {

    private final static String KEY = "VERY_SECURITY_PASSWORD_VERY_SECURITY_PASSWORD_VERY_SECURITY_PASSWORD_VERY_SECURITY_PASSWORD";

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }
}
