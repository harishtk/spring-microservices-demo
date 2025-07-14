package io.practice.currencyexchange.currencyconversionservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CurrencyConversionController {

    private final CurrencyExchangeProxy currencyExchangeProxy;
    private final RestTemplate restTemplate;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<?> getExchangeValue(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {
        var pathVariables = new HashMap<String, Object>();
        pathVariables.put("from", from);
        pathVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity(
                "http://currency-exchange-service:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                pathVariables);

        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            var error = Map.of("error", "could not retrieve exchange value");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

        CurrencyConversion currencyConversion = responseEntity.getBody();

        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(currencyConversion.getQuantity()
                .multiply(currencyConversion.getConversionMultiple()));
        currencyConversion.setEnvironment(
                currencyConversion.getEnvironment() + " " + "rest template");
        return ResponseEntity.ok(currencyConversion);
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<?> getExchangeValueFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {
        var currencyConversion = currencyExchangeProxy.getExchangeValue(from, to)
                .getBody();
        if (currencyConversion == null) {
            var error = Map.of("error", "could not retrieve exchange value");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(currencyConversion.getQuantity()
                .multiply(currencyConversion.getConversionMultiple()));
        currencyConversion.setEnvironment(
                currencyConversion.getEnvironment() + " " + "feign");
        return ResponseEntity.ok(currencyConversion);
    }
}
