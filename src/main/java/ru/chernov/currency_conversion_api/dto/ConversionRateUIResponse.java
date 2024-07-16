package ru.chernov.currency_conversion_api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConversionRateUIResponse {
    @JsonProperty("time_last_update_unix")
    private Long timeLastUpdate;

    @JsonProperty("time_next_update_unix")
    private Long timeNextUpdate;

    @JsonProperty("base_code")
    private String baseCode;
    
    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("target_code")
    private String targetCode;

    @JsonProperty("result")
    private BigDecimal result;
}
