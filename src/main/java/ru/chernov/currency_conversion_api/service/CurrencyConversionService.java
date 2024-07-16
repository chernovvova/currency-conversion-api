package ru.chernov.currency_conversion_api.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.chernov.currency_conversion_api.client.ConversionRateClient;
import ru.chernov.currency_conversion_api.dto.ConversionRateResponse;
import ru.chernov.currency_conversion_api.entity.ConversionRateEntity;
import ru.chernov.currency_conversion_api.repository.ConversionRateRepository;

@Service
@Slf4j
@AllArgsConstructor
public class CurrencyConversionService {
    private final ConversionRateClient conversionRateClient;
    private final ConversionRateRepository conversionRateRepository;

    public ConversionRateResponse getConversionRates() {
        ConversionRateResponse response;
        try {
            response = conversionRateClient.getConversionRates();
            log.info("API response: {}", response);
        } 
        catch(Exception exception) {
            log.error("Error calling API", exception);
            throw new RuntimeException("Error calling API", exception);
        }
        
        log.info("API response result: {}", response.getResult());
        if(response.getResult().equalsIgnoreCase("success")) {
            log.info("Last update: {}", response.getTimeLastUpdate());
            log.info("Next update: {}", response.getTimeNextUpdate());
            log.info("Base code: {}", response.getBaseCode());
            
            for(Map.Entry<String, BigDecimal> entry : response.getConversionRates().entrySet()) {
                log.info("Target code: {}\t Conversion rate: {}", entry.getKey(), entry.getValue());
            }
        }
        else {
            logResponseError(response.getErrorType());
        }

        return response;
    }

    public void logResponseError(String errorType) {
        switch (errorType) {
            case "unsupported-code":
                log.error("Supplied currency code is not supported");
                break;
            case "malformed-request":
                log.error("Wrong request structure");
                break;
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

    public void saveConversionRates(ConversionRateResponse response) {
        for(Map.Entry<String, BigDecimal> entry : response.getConversionRates().entrySet()) {
            ConversionRateEntity entity = new ConversionRateEntity();

            entity.setBaseCode(response.getBaseCode());
            entity.setTargetCode(entry.getKey());
            entity.setTimeLastUpdate(response.getTimeLastUpdate());
            entity.setTimeNextUpdate(response.getTimeNextUpdate());
            entity.setConversionRate(entry.getValue());

            conversionRateRepository.save(entity);
        }
    }
}
