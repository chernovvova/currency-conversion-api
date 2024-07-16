package ru.chernov.currency_conversion_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import feign.Param;
import ru.chernov.currency_conversion_api.entity.ConversionRateEntity;
import ru.chernov.currency_conversion_api.entity.ConversionRateId;

@Repository
public interface ConversionRateRepository extends JpaRepository<ConversionRateEntity, ConversionRateId>{
    @Query(value = "SELECT * FROM conversion_rates WHERE target_code = :targetCode AND :time BETWEEN time_last_update AND time_next_update", 
            nativeQuery = true)
    List<ConversionRateEntity> findByTargetCodeAndTimeInterval(@Param("targetCode")String targetCode, @Param("time") Long time);
} 
