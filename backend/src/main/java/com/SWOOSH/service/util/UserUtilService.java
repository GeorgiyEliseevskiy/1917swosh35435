package com.SWOOSH.service.util;

import com.SWOOSH.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUtilService {

    private final UserRepository userRepository;

    public Boolean isPresentEmail(String email) {
        return userRepository.existUserByEmail(email);
    }
}
