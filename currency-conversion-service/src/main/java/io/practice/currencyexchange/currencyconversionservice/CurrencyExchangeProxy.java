package io.practice.currencyexchange.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    ResponseEntity<CurrencyConversion> getExchangeValue(
            @PathVariable String from,
            @PathVariable String to);
}
