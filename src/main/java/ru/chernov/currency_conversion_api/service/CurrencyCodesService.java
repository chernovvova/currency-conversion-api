package ru.chernov.currency_conversion_api.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.chernov.currency_conversion_api.client.ExchangeRateAPIClient;
import ru.chernov.currency_conversion_api.dto.CurrencyCodesAPIResponse;

@Service
@Slf4j
@AllArgsConstructor
public class CurrencyCodesService {
    private final ExchangeRateAPIClient exchangeRateAPIClient;

    public CurrencyCodesAPIResponse getCurrencyCodes() {
        log.info("Getting currency codes from API");

        CurrencyCodesAPIResponse response;

        try {
            response = exchangeRateAPIClient.getCurrencyCodes();
            log.info("API response {}", response);
        }
        catch(Exception exception) {
            log.error("Error calling API: ", exception);
            throw new RuntimeException("Error calling API", exception);
        }
        log.info("API response result: {}", response.getResult());

        if(response.getResult().equals("success")) {
            for(Map.Entry<String, String> entry : response.getSupportedCurrencies().entrySet()) {
                log.info("Code: {}\t Name: {}", entry.getKey(), entry.getValue());
            }
        }
        else {
            logResponseError(response.getErrorType());
        }

        return response;
    }

    public void logResponseError(String errorType) {
        switch (errorType) {
            case "invalid-key":
                log.error("API key is not valid");
                break;
            case "inactive-account":
                log.error("Email address is not confirmed");
                break;
            case "quota-reached":
                log.error("Account has reached the the number of requests");
                break;
            default:
                log.error("Unknown error");
                break;
        }
    }
}
