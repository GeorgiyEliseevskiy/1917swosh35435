package com.SWOOSH.controller.regauth;

import com.SWOOSH.dto.RegistrationDTO;
import com.SWOOSH.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${spring.whiteip}")
@RequestMapping("/api/reg")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping(value = "/createUser")
    public Boolean createUser(@RequestBody RegistrationDTO registrationDTO) {
        return userService.createUser(registrationDTO);
    }

    @PutMapping("/checkConfirmation")
    public Boolean checkConfirmationCode(String code, String contact) {
        return userService.checkCode(contact, code);
    }
}
