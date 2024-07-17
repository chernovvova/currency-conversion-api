package ru.chernov.currency_conversion_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import ru.chernov.currency_conversion_api.dto.CurrencyCodesAPIResponse;
import ru.chernov.currency_conversion_api.service.CurrencyCodesService;

@RestController
@AllArgsConstructor
@Tag(name="Controller for currency codes", description = "API для получения кодов и названий валют")
public class CurrencyCodesController {
    private final CurrencyCodesService currencyCodesService;
    
    @GetMapping("/codes")
    @Operation(summary = "Получение кодов и названий валют")
    public CurrencyCodesAPIResponse getCurrencyCodes() {
        return currencyCodesService.getCurrencyCodes();
    }
}
