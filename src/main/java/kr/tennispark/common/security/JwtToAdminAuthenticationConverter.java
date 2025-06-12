package kr.tennispark.common.security;

import static kr.tennispark.common.constant.JwtConstants.ROLE_CLAIM;
import static kr.tennispark.common.constant.JwtConstants.ROLE_PREFIX;

import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtToAdminAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String ADMIN_PRINCIPAL = "admin";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String role = jwt.getClaimAsString(ROLE_CLAIM);

        return new UsernamePasswordAuthenticationToken(
                ADMIN_PRINCIPAL,
                "N/A",
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + role)));
    }
}
