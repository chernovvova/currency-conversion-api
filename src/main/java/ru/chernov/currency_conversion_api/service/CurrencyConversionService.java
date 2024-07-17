package ru.chernov.currency_conversion_api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.chernov.currency_conversion_api.client.ExchangeRateAPIClient;
import ru.chernov.currency_conversion_api.dto.ConversionRateAPIResponse;
import ru.chernov.currency_conversion_api.dto.ConversionRateUIResponse;
import ru.chernov.currency_conversion_api.entity.ConversionRateEntity;
import ru.chernov.currency_conversion_api.repository.ConversionRateRepository;

@Service
@Slf4j
@AllArgsConstructor
public class CurrencyConversionService {
    private static final int NUMBERS_SCALE = 4;

    private final ExchangeRateAPIClient exchangeRateAPIClient;
    private final ConversionRateRepository conversionRateRepository;

    public ConversionRateAPIResponse getConversionRates() {
        log.info("Getting currency rates from API");
        ConversionRateAPIResponse response;
        try {
            response = exchangeRateAPIClient.getConversionRates();
            log.info("API response: {}", response);
        } 
        catch(Exception exception) {
            log.error("Error calling API: ", exception);
            throw new RuntimeException("Error calling API", exception);
        }
        
        log.info("API response result: {}", response.getResult());
        if(response.getResult().equals("success")) {
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

    public void saveConversionRates(ConversionRateAPIResponse response) {
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

    public ConversionRateUIResponse convertCurrency(String baseCode, String targetCode, BigDecimal amount, Long time) {
        ConversionRateUIResponse response = new ConversionRateUIResponse();
        response.setBaseCode(baseCode);
        response.setTargetCode(targetCode);
        response.setCurrentTime(time);
        response.setAmount(amount);

        log.info("Converting {} {} to {}", amount, baseCode, targetCode);

        ConversionRateEntity actualConversionRate;
        BigDecimal result;
        // USD -> target: result = amount * conv_rate 
        // target -> USD: result = amount / conv_rate
        // base -> target, base -> USD -> target: result = amount / conv_rate1 * conv_rate2
        if(baseCode.equals("USD")) {
            actualConversionRate = findActualConvesionRate(targetCode, time);
            result = forwardConvertion(actualConversionRate.getConversionRate(), amount);

            log.info("{} USD converted to {} {}", amount, result, targetCode);
        }
        else if(targetCode.equals("USD")) {
            actualConversionRate = findActualConvesionRate(baseCode, time);
            result = backwardConvertion(actualConversionRate.getConversionRate(), amount);

            log.info("{} {} converted to {} USD", amount, baseCode, result);
        }
        else {
            actualConversionRate = findActualConvesionRate(baseCode, time);
            result = backwardConvertion(actualConversionRate.getConversionRate(), amount);

            log.info("{} {} converted to {} USD", amount, baseCode, result);

            actualConversionRate = findActualConvesionRate(targetCode, time);
            result = forwardConvertion(actualConversionRate.getConversionRate(), result);

            log.info("{} USD converted to {} {}", amount, result, targetCode);
        }

        response.setResult(result);
        response.setTimeLastUpdate(actualConversionRate.getTimeLastUpdate());
        response.setTimeNextUpdate(actualConversionRate.getTimeNextUpdate());

        log.info("Conversion result: {} {}", result, targetCode);
        return response;
    }
    
    public ConversionRateEntity findActualConvesionRate(String code, Long time) {
        Optional<ConversionRateEntity> convertionRate = conversionRateRepository.findByTargetCodeAndTimeInterval(code, time);
        
        if(convertionRate.isEmpty()) {
            ConversionRateAPIResponse entities = getConversionRates();
            saveConversionRates(entities);
            
            convertionRate = conversionRateRepository.findByTargetCodeAndTimeInterval(code, time);
        }
        
        return convertionRate.get();
    }
    //covert USD -> code
    public BigDecimal forwardConvertion(BigDecimal conversionRate, BigDecimal amount) { 
        BigDecimal result = conversionRate;
        result = amount.multiply(result);
        result = result.setScale(NUMBERS_SCALE, RoundingMode.HALF_UP);

        return result;
    }

    //convert code -> USD
    public BigDecimal backwardConvertion(BigDecimal conversionRate, BigDecimal amount) {
        BigDecimal result = conversionRate;
        result = amount.divide(result, NUMBERS_SCALE, RoundingMode.HALF_UP);
        result = result.setScale(NUMBERS_SCALE, RoundingMode.HALF_UP);
        
        return result;
    }
}
