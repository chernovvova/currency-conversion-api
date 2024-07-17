package ru.chernov.currency_conversion_api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
    name = "Container for currency codes",
    description = "Поддерживаемые курсы валют"
)
public class CurrencyCodesAPIResponse {
    @JsonProperty("result")
    @Schema(name = "Результат запроса")
    private String result;

    @JsonProperty("supported_codes")
    @Schema(name = "Поддерживаемые валюты")
    private List<List<String>> supportedCurrencies;

    @JsonProperty("error_type")
    @Schema(name = "Тип ошибки", description = "При отсутствии ошибки имеет значение null")
    private String errorType;
}
