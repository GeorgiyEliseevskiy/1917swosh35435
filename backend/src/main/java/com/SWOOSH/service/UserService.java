package com.SWOOSH.service;

import com.SWOOSH.dto.RegistrationDTO;
import com.SWOOSH.dto.UserDTO;
import com.SWOOSH.enums.Role;
import com.SWOOSH.enums.Status;
import com.SWOOSH.model.ConfirmationCode;
import com.SWOOSH.model.User;
import com.SWOOSH.model.UserSecurity;
import com.SWOOSH.repository.ConfirmationCodeRepository;
import com.SWOOSH.repository.UserRepository;
import com.SWOOSH.service.util.UserUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final SmsService smsService;
    private final EmailService emailService;
    private final UserUtilService userUtilService;

    public Boolean createUser(RegistrationDTO registrationDTO) {
        if (userUtilService.isPresentEmail(registrationDTO.getEmail())) {
            return false;
        }

        User user = userRepository.findByPhone(registrationDTO.getPhone());
        if (user == null) {
            user = new User();
        } else if (user.getEmail() != null) {
            return false;
        }

        user.setEmail(registrationDTO.getEmail());
        user.setName(registrationDTO.getName());
        user.setPhone(registrationDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder(12).encode(registrationDTO.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setStatus(Status.CONFIRMATION);
        sendCode(registrationDTO.getEmail());
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailWithStatusActive(email);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exists");
        }
        return UserSecurity.fromUser(user);
    }

    public User updateStatus(Long userId, Status status) {
        User user = userRepository.getById(userId);
        user.setStatus(status);
        return userRepository.save(user);
    }

    public Boolean checkCode(String contact, String code) {
        ConfirmationCode confirmationCode = confirmationCodeRepository.getConfirmationCodeByContact(contact);
        User user = userRepository.findByEmail(contact);
        if (confirmationCode != null && confirmationCode.getCode().equals(code)) {
            userRepository.save(updateStatus(user.getId(), Status.ACTIVE));
            return true;
        }
        return false;
    }

    public void sendCode(String contact) {
        Thread thread = new Thread(() -> {
            ConfirmationCode code = new ConfirmationCode();
            code.setCode(generateCode(10));
            code.setContact(contact);
//            smsService.sendSms(code.getContact(), code.getCode());
            emailService.sendEmail(code.getContact(), code.getCode());
            if (confirmationCodeRepository.existByContact(contact)) {
                confirmationCodeRepository.updateCode(code.getContact(), code.getCode());
            } else {
                confirmationCodeRepository.save(code);
            }
        });
        thread.start();
    }

    private String generateCode(Integer codeLength) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            int number = (int) (Math.random() * 10);
            code.append(number % 2 == 0 ? String.valueOf((char) ((Math.random() * 26) + 65)) : String.valueOf(number));
        }
        return code.toString();
    }

    public UserDTO getUser(String email) {
        User user = userRepository.findByEmail(email);
        return new UserDTO(user.getId(), user.getName(), user.getPhone(), user.getEmail(), user.getRole());
    }
}
