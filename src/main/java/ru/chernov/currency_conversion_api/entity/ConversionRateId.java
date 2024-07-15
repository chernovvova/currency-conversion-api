package ru.chernov.currency_conversion_api.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
public class ConversionRateId implements Serializable {
    private String targetCode;
    private Long timeLastUpdate;
}
