package io.practice.currencyexchange.currencyexchangeservice;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j(topic = "CurrencyExchangeController")
@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final Environment environment;

    @GetMapping("/from/{from}/to/{to}")
    public ResponseEntity<CurrencyExchange> getExchangeValue(
            @PathVariable String from,
            @PathVariable String to) {

        logger.info("getExchangeValue called with: from: {}, to: {}", from, to);

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to)
                .orElseThrow();

        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);

        return ResponseEntity.ok(currencyExchange);
    }
}
