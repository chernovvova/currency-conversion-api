package ru.chernov.currency_conversion_api.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyCodesAPIResponse {
    @JsonProperty("result")
    private String result;

    @JsonProperty("supported_codes")
    private List<List<String>> supportedCurrencies;

    @JsonProperty("error_type")
    private String errorType;
}
