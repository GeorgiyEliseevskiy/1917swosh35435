package com.SWOOSH.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String carWashLocation;
}
