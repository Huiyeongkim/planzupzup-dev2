package travel.travel.common.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessMills;

    @Value("${jwt.refresh-expiration}")
    private long refreshMills;

    public String generateAccessToken(String id) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessMills);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken() {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessMills);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public ResponseCookie generateAccessTokenCookie(String accessToken) {
        return ResponseCookie.from("accessToken", accessToken)
                .path("/")
                .httpOnly(true)
                .sameSite("Lax")
                .build();
    }
}
