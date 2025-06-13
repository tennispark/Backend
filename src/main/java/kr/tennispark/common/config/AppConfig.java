package kr.tennispark.common.config;

import kr.tennispark.auth.user.infrastructure.sms.SmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class AppConfig {
}
