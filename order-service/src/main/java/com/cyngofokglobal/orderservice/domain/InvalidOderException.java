package com.cyngofokglobal.orderservice.domain;

public class InvalidOderException extends RuntimeException{
    public InvalidOderException(String message) {
        super(message);
    }
}
