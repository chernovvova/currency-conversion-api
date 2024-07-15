package ru.chernov.currency_conversion_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyConversionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionApiApplication.class, args);
	}

}
