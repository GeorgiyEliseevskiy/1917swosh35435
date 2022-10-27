package com.SWOOSH.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarWashDTO {

    private Long id;
    private String location;
    private String name;
    private String phone;
    private List<RegistrationDTO> employees;
    private List<ServiceDTO> services;
}
