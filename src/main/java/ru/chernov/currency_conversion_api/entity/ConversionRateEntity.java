package ru.chernov.currency_conversion_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="conversion_rates")
@IdClass(ConversionRateId.class)
public class ConversionRateEntity {
    @Column(name="base_code")
    private String baseCode;

    @Id
    @Column(name = "target_code")
    private String targetCode;

    @Id
    @Column(name = "timeLastUpdate")
    private Long timeLastUpdate;

    @Column(name = "timeNextUpdate")
    private Long timeNextUpdate;

    @Column(name = "conversion_rate", precision = 38, scale = 4)
    private BigDecimal conversionRate;
}
