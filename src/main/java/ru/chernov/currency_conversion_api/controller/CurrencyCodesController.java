package ru.chernov.currency_conversion_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.chernov.currency_conversion_api.dto.CurrencyCodesAPIResponse;
import ru.chernov.currency_conversion_api.service.CurrencyCodesService;

@RestController
@AllArgsConstructor
public class CurrencyCodesController {
    private final CurrencyCodesService currencyCodesService;
    
    @GetMapping("/codes")
    public CurrencyCodesAPIResponse convertCurrency() {
        return currencyCodesService.getCurrencyCodes();
    }
}
