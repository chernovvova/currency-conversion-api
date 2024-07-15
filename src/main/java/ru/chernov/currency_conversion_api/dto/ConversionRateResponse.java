package ru.chernov.currency_conversion_api.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversionRateResponse {
    @JsonProperty("result")
    private String result;

    @JsonProperty("time_last_update_unix")
    private Long timeLastUpdate;

    @JsonProperty("time_next_update_unix")
    private Long timeNextUpdate;

    @JsonProperty("base_code")
    private String baseCode;

    @JsonProperty("conversion_rates")
    private Map<String, BigDecimal> conversionRates;

    @JsonProperty("error-type")
    private String errorType;
}
