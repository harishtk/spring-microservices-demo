package io.practice.currencyexchange.limitsservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("limits-service")
public class Config {
    private int minimum;
    private int maximum;
}
