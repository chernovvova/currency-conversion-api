package ru.chernov.currency_conversion_api.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.chernov.currency_conversion_api.client.ConversionRateClient;
import ru.chernov.currency_conversion_api.repository.ConversionRateRepository;

@Service
@Slf4j
@AllArgsConstructor
public class CurrencyConversionService {
    private final ConversionRateClient conversionRateClient;
    private final ConversionRateRepository conversionRateRepository;
}
