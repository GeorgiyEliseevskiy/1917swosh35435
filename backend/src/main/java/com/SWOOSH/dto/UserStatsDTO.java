package com.SWOOSH.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Integer numberOfVisits;
    private Integer averageCheck;
}
