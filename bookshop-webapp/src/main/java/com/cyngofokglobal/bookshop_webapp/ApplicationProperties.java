package com.cyngofokglobal.bookshop_webapp;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "bookshop")
public record ApplicationProperties(String apiGatewayUrl) { }
