package io.practice.currencyexchange.limitsservice.controller;

import io.practice.currencyexchange.limitsservice.bean.Limits;
import io.practice.currencyexchange.limitsservice.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LimitsController {

    private final Config config;

    @GetMapping("/limits")
    public ResponseEntity<Limits> getLimits() {
        return ResponseEntity.ok(
                new Limits(config.getMinimum(), config.getMaximum())
        );
    }
}
