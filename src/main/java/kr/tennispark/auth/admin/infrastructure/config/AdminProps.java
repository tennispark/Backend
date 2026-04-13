package kr.tennispark.auth.admin.infrastructure.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public record AdminProps(List<AdminAccount> admins) {
    public record AdminAccount(String id, String password, String role, String region) {}
}