package kr.tennispark.auth.common.application;

import static kr.tennispark.common.constant.JwtConstants.ROLE_CLAIM;

import java.time.Duration;
import java.time.Instant;
import kr.tennispark.auth.user.application.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${security.jwt.token.access.expire-length}")
    private Duration accessTtl;

    @Value("${security.jwt.token.refresh.expire-length}")
    private Duration refreshTtl;

    public String createAccessToken(String subject, String role) {
        return createToken(subject, role, accessTtl);
    }

    public String createRefreshToken(String subject, String role) {
        return createToken(subject, role, refreshTtl);
    }

    private String createToken(String subject, String role, Duration ttl) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(now)
                .expiresAt(now.plus(ttl))
                .claim(ROLE_CLAIM, role)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        JwtEncoderParameters params = JwtEncoderParameters.from(jwsHeader, claims);

        return jwtEncoder.encode(params)
                .getTokenValue();
    }

    public String getSubject(String token) {
        try {
            return jwtDecoder.decode(token).getSubject();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

    public Instant getExpires(String token) {
        try {
            return jwtDecoder.decode(token).getExpiresAt();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

    public String getRole(String token) {
        try {
            return jwtDecoder.decode(token).getClaim(ROLE_CLAIM);
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }
}
