package com.SWOOSH.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.IOException;

@Slf4j
@Configuration
@EnableConfigurationProperties
@EnableScheduling

public class DumpConfig {
    @Scheduled(cron = "0 0 3 * * *")
    private void dump() throws IOException {
        String[] cmd = { "sh", "backup.sh", "./"};
        Runtime.getRuntime().exec(cmd);
    }
}
