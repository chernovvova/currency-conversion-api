package ru.chernov.currency_conversion_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.chernov.currency_conversion_api.client.ExchangeRateAPIClient;
import ru.chernov.currency_conversion_api.dto.ConversionRateUIResponse;
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
    private BigDecimal amount;
    private BigDecimal forwardConvResult;
    private BigDecimal backwardConvResult;

    private static final int NUMBERS_SCALE = 4;

    @BeforeEach
    void setUp() {
        time = 172108800l;
        baseCode = "USD";
        targetCode = "AED";
        timeLastUpdate = 1721088001l;
        timeNextUpdate = 1721174401l;
        conversionRate = new BigDecimal(3.6725).setScale(NUMBERS_SCALE, RoundingMode.HALF_UP);
        amount = new BigDecimal(10);
        forwardConvResult = new BigDecimal(36.725).setScale(NUMBERS_SCALE, RoundingMode.HALF_UP);
        backwardConvResult = new BigDecimal(2.7229).setScale(NUMBERS_SCALE, RoundingMode.HALF_UP);

        mockConversionRateEntity = buildMockConversionRateEntity();
    }
    @Test
    void findActualConversionRateShouldReturnEntityFromDb() {
        when(conversionRateRepository.findByTargetCodeAndTimeInterval(anyString(), anyLong())).thenReturn(Optional.of(mockConversionRateEntity));

        ConversionRateEntity conversionRate = mockCurrencyConversionService.findActualConversionRate(targetCode, time);

        assertEquals(mockConversionRateEntity, conversionRate);
        assertNotNull(conversionRate);

        verify(conversionRateRepository).findByTargetCodeAndTimeInterval(targetCode, time);
    }

    @Test
    void testConvertCurrencyForwardConvertion() {
        when(conversionRateRepository.findByTargetCodeAndTimeInterval(anyString(), anyLong())).thenReturn(Optional.of(mockConversionRateEntity));
        ConversionRateUIResponse expectedResponse = buildResponseForForwardConvertion();

        ConversionRateUIResponse actualResponse = mockCurrencyConversionService.convertCurrency(baseCode, targetCode, amount, time);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testConvertCurrencyBackwardConvertion() {
        when(conversionRateRepository.findByTargetCodeAndTimeInterval(anyString(), anyLong())).thenReturn(Optional.of(mockConversionRateEntity));
        ConversionRateUIResponse expectedResponse = buildResponseForBackwardConvertion();

        ConversionRateUIResponse actualResponse = mockCurrencyConversionService.convertCurrency(targetCode, baseCode, amount, time);

        assertEquals(expectedResponse, actualResponse);
    }

    private ConversionRateUIResponse buildResponseForForwardConvertion() {
        return new ConversionRateUIResponse(
            timeLastUpdate,
            timeNextUpdate,
            time,
            baseCode,
            amount,
            targetCode,
            forwardConvResult
        );
    }

    private ConversionRateUIResponse buildResponseForBackwardConvertion() {
        return new ConversionRateUIResponse(
            timeLastUpdate,
            timeNextUpdate,
            time,
            targetCode,
            amount,
            baseCode,
            backwardConvResult
        );
    }

    private ConversionRateEntity buildMockConversionRateEntity() {
        return new ConversionRateEntity(
            baseCode,
            targetCode,
            timeLastUpdate,
            timeNextUpdate,
            conversionRate
        );
    }
}
