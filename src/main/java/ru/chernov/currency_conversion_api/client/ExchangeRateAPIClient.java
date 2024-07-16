package ru.chernov.currency_conversion_api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import ru.chernov.currency_conversion_api.dto.ConversionRateAPIResponse;
import ru.chernov.currency_conversion_api.dto.CurrencyCodesAPIResponse;

@FeignClient(name = "currency-conversion-client", url = "${exchange-rate-api.url}")
public interface ExchangeRateAPIClient {
    @GetMapping("/${exchange-rate-api.key}/latest/USD")
    public ConversionRateAPIResponse getConversionRates();

    @GetMapping("/${exchange-rate-api.key}/codes")
    public CurrencyCodesAPIResponse getCurrencyCodes();
}
