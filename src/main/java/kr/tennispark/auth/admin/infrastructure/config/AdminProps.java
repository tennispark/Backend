package kr.tennispark.auth.admin.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.admin")
public record AdminProps(String id, String password) {
}
