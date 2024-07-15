package ru.chernov.currency_conversion_api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import ru.chernov.currency_conversion_api.dto.ConversionRateResponse;

@FeignClient(name = "conversion-rate-client", url = "${exchange-rate-api.url}")
public interface ConversionRateClient {
    @GetMapping("/${exchange-rate-api.key}/latest/USD")
    public ConversionRateResponse getConversionRates();
}
