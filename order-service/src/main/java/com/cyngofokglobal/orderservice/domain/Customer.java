package com.cyngofokglobal.orderservice.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Customer {
    @NotBlank(message = "Customer Name is required") String name;
    @NotBlank(message = "Customer email is required") @Email String email;
    @NotBlank(message = "Customer Phone number is required") String phone;

    public Customer() {}

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
