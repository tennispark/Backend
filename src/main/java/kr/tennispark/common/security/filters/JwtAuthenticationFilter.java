package kr.tennispark.common.security.filters;

import static kr.tennispark.common.constant.JwtConstants.AUTHORIZATION_HEADER;
import static kr.tennispark.common.constant.JwtConstants.BEARER_PREFIX;
import static kr.tennispark.common.constant.JwtConstants.ROLE_PREFIX;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import kr.tennispark.auth.application.JwtTokenProvider;
import kr.tennispark.auth.application.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isPermitAllRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        authenticateRequest(request);
        filterChain.doFilter(request, response);

    }

    private void authenticateRequest(HttpServletRequest request) {
        Claims claims = tokenProvider.getClaims(extractToken(request));
        String memberId = claims.getSubject();
        String role = claims.get("roles", String.class);

        validateRole(role);

        var authority = new SimpleGrantedAuthority(
                ROLE_PREFIX + role);
        var authentication = new UsernamePasswordAuthenticationToken(memberId, null, List.of(authority));
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(bearer)) {
            throw new InvalidTokenException("Authorization 헤더가 없습니다.");
        }
        if (!bearer.startsWith(BEARER_PREFIX)) {
            throw new InvalidTokenException("토큰 형식이 잘못되었습니다.");
        }
        String token = bearer.substring(BEARER_PREFIX.length());
        if (!StringUtils.hasText(token)) {
            throw new InvalidTokenException("토큰이 비어있습니다.");
        }
        return token;
    }

    private void validateRole(String role) {
        if (!StringUtils.hasText(role)) {
            throw new InvalidTokenException("사용자 역할 정보가 없습니다.");
        }
    }

    private boolean isPermitAllRequest(HttpServletRequest request) {
        // 나중에 수정 필요 -> whiteList 선언하면 헤더는?
        return true;
    }

}
