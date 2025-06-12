package kr.tennispark.common.annotation;

import static kr.tennispark.common.constant.JwtConstants.ADMIN_ROLE_VALUE;
import static kr.tennispark.common.constant.JwtConstants.ROLE_PREFIX;

import kr.tennispark.auth.admin.application.exception.UnauthorizedRoleAccessException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminAuthorizationAspect {

    @Before("@within(kr.tennispark.common.annotation.AdminOnly) || " +
            "@annotation(kr.tennispark.common.annotation.AdminOnly)")
    public void checkAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> (ROLE_PREFIX + ADMIN_ROLE_VALUE).equals(a.getAuthority()));
        if (!isAdmin) {
            throw new UnauthorizedRoleAccessException();
        }
    }
}
