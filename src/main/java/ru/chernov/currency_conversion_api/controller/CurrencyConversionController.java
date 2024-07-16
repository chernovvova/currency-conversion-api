package ru.chernov.currency_conversion_api.controller;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.chernov.currency_conversion_api.service.CurrencyConversionService;

@RestController 
@AllArgsConstructor
public class CurrencyConversionController {
    private final CurrencyConversionService conversionRateService;
    
    @GetMapping("/convert")
    public BigDecimal convertCurrency(@RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount) {
        return conversionRateService.convertCurrency(from, to, amount, Instant.now().toEpochMilli() / 1000L);
    }
}
