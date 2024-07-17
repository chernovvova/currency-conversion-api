package ru.chernov.currency_conversion_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import ru.chernov.currency_conversion_api.client.ExchangeRateAPIClient;
import ru.chernov.currency_conversion_api.entity.ConversionRateEntity;
import ru.chernov.currency_conversion_api.repository.ConversionRateRepository;

@ExtendWith(MockitoExtension.class)
public class CurrencyConversionServiceTest {
    @InjectMocks
    CurrencyConversionService mockCurrencyConversionService;
    @Mock
    private ExchangeRateAPIClient exchangeRateAPIClient;
    @Mock
    private ConversionRateRepository conversionRateRepository;

    private ConversionRateEntity mockConversionRateEntity;

    private Long time;
    private String baseCode;
    private String targetCode;
    private Long timeLastUpdate;
    private Long timeNextUpdate;
    private BigDecimal conversionRate;

    @BeforeEach
    void setUp() {
        time = 1721088002l;
        baseCode = "USD";
        targetCode = "AED";
        timeLastUpdate = 1721088001l;
        timeNextUpdate = 1721174401l;
        conversionRate = new BigDecimal(3.6725);

        mockConversionRateEntity = new ConversionRateEntity();
        mockConversionRateEntity.setBaseCode(baseCode);
        mockConversionRateEntity.setTargetCode(targetCode);
        mockConversionRateEntity.setTimeLastUpdate(timeLastUpdate);
        mockConversionRateEntity.setTimeNextUpdate(timeNextUpdate);
        mockConversionRateEntity.setConversionRate(conversionRate);
    }
    @Test
    void findActualConversionRateShouldReturnEntityFromDb() {
        when(conversionRateRepository.findByTargetCodeAndTimeInterval(anyString(), anyLong())).thenReturn(Optional.of(mockConversionRateEntity));

        ConversionRateEntity conversionRate = mockCurrencyConversionService.findActualConversionRate(targetCode, time);

        assertEquals(mockConversionRateEntity, conversionRate);
        assertNotNull(conversionRate);

        verify(conversionRateRepository).findByTargetCodeAndTimeInterval(targetCode, time);
    }
}
