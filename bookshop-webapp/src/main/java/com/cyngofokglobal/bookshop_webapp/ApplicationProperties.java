package com.cyngofokglobal.bookshop_webapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@ConfigurationProperties(prefix = "bookshop")
public record ApplicationProperties(String apiGatewayUrl) {}
