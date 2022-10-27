package com.SWOOSH.dto;

import com.SWOOSH.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private Role role;
}
