package com.SWOOSH.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private UserDTO user;
    private RegistrationDTO employee;
    private CarWashDTO carWash;
    private ServiceDTO service;
    private Date date;
    private String text;
    private Integer price;
    private Double grade;
    private Boolean status;
}
