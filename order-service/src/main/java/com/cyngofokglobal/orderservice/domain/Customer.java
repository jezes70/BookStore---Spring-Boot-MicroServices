package com.cyngofokglobal.orderservice.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

//@Getter
//@Setter
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @NotBlank(message = "Customer Name is required")
    String name;
    @NotBlank(message = "Customer email is required")
    @Email
    String email;
    @NotBlank(message = "Customer Phone number is required")
    String phone;

//    public Customer() {}

//    public Customer(String name, String email, String phone) {
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//    }
}
