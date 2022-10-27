package com.SWOOSH.service;

import com.SWOOSH.api.SmsCSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final SmsCSender smsCSender = new SmsCSender("swooshAuth", "Swooshmail1@1342asd", "utf-8", true);

    public void sendSms(String phone, String message) {
        smsCSender.sendSms(phone, "Ваш код: " + message, 1, "", "", 0, "Swoosh", "");
    }
}
