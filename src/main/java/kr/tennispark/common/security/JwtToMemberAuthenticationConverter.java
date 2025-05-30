package kr.tennispark.common.security;

import static kr.tennispark.common.constant.JwtConstants.ROLE_CLAIM;
import static kr.tennispark.common.constant.JwtConstants.ROLE_PREFIX;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtToMemberAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserDetailsService memberDetailsService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String subject = jwt.getSubject();
        String role = jwt.getClaim(ROLE_CLAIM);

        UserDetails user = memberDetailsService.loadUserByUsername(subject);

        return new UsernamePasswordAuthenticationToken(
                user,
                "N/A",
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + role)));
    }
}
