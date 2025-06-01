package kr.tennispark.common.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/api")
public class BaseController {

    @GetMapping("health-check")
    public String healthCheck() {
        return "OK";
    }
}
