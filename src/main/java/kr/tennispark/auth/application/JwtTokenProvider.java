package kr.tennispark.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import kr.tennispark.auth.application.dto.TokenDTO;
import kr.tennispark.auth.application.exception.InvalidTokenException;
import kr.tennispark.members.common.domain.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final static String ROLE_KEY = "roles";

    private final SecretKey key;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.access.expire-length}") final long accessTokenValidityInMilliseconds,
                            @Value("${security.jwt.token.refresh.expire-length}") final long refreshTokenValidityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    public TokenDTO issueTokensFor(Member member) {
        // 논의필요
        return TokenDTO.builder()
                .accessToken(createAccessToken(String.valueOf(member.getId()), "USER"))
                .refreshToken(createRefreshToken(String.valueOf(member.getId()), "USER"))
                .build();
    }

    // TODO : authority는 enum으로 변경
    private String createAccessToken(final String payload, final String authority) {
        return createToken(payload, accessTokenValidityInMilliseconds, authority.toString());
    }

    private String createRefreshToken(final String payload, final String authority) {
        return createToken(payload, refreshTokenValidityInMilliseconds, authority.toString());
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    private String createToken(String payload, Long validityInMilliseconds, String roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(payload)
                .claim(ROLE_KEY, roles)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String getPayload(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRole(final String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get(ROLE_KEY, String.class);
    }


    public void validateToken(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

}
