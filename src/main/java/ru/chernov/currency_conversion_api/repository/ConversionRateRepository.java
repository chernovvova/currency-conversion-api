package ru.chernov.currency_conversion_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.chernov.currency_conversion_api.entity.ConversionRateEntity;
import ru.chernov.currency_conversion_api.entity.ConversionRateId;

@Repository
public interface ConversionRateRepository extends JpaRepository<ConversionRateEntity, ConversionRateId>{

} 
