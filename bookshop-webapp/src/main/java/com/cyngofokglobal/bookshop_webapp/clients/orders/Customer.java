package com.cyngofokglobal.bookshop_webapp.clients.orders;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @NotBlank(message = "Customer Name is required") String name;
    @NotBlank(message = "Customer email is required") @Email String email;
    @NotBlank(message = "Customer Phone number is required") String phone;
}