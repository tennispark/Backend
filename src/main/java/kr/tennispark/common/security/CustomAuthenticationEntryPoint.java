package kr.tennispark.common.security;

import static kr.tennispark.common.constant.JwtConstants.CONTENT_TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.tennispark.common.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(CONTENT_TYPE);

        String message = switch (authException.getClass().getSimpleName()) {
            case "BadCredentialsException" -> "잘못된 인증 정보입니다.";
            case "JwtException", "JwtValidationException" -> "유효하지 않은 토큰입니다.";
            default -> "인증이 필요한 요청입니다.";
        };

        String json = objectMapper.writeValueAsString(
                ApiUtils.error(HttpStatus.UNAUTHORIZED, message)
        );

        response.getWriter().write(json);
    }
}

