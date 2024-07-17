package ru.chernov.currency_conversion_api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    name = "Container for currency conversion rates",
    description = "Конвертация валюты на основании текущих курсов с ExchangeRate"
)
public class ConversionRateUIResponse {
    @JsonProperty("time_last_update_unix")
    @Schema(name = "Время последнего обновления курсов валют")
    private Long timeLastUpdate;

    @JsonProperty("time_next_update_unix")
    @Schema(name = "Время следующего обновления курсов валют")
    private Long timeNextUpdate;

    @JsonProperty("current_unix_time")
    @Schema(name = "Время запроса")
    private Long currentTime;

    @JsonProperty("base_code")
    @Schema(name = "Код конвертируемой валюты")
    private String baseCode;

    @JsonProperty("amount")
    @Schema(name = "Количество валюты")
    private BigDecimal amount;

    @JsonProperty("target_code")
    @Schema(name = "Код нужной валюты")
    private String targetCode;

    @JsonProperty("result")
    @Schema(name = "Результат конвертации")
    private BigDecimal result;
}
