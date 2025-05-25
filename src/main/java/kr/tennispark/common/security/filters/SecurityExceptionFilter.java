package kr.tennispark.common.security.filters;


import static kr.tennispark.common.constant.JwtConstants.CONTENT_TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.tennispark.auth.application.exception.InvalidTokenException;
import kr.tennispark.common.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response); // 다음 필터 실행
        } catch (InvalidTokenException ex) {
            log.warn("Security Error: {}", ex.getMessage());
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (SecurityException ex) {
            log.warn("Security-related error: {}", ex.getMessage());
            sendErrorResponse(response, HttpStatus.FORBIDDEN, "접근이 거부되었습니다.");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(CONTENT_TYPE);
        String json = objectMapper.writeValueAsString(ApiUtils.error(status, message));
        response.getWriter().write(json);
    }
}
