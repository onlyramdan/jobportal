package com.lawencon.jobportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.time.ZonedDateTime;

@Configuration
@EnableJpaAuditing(
    dateTimeProviderRef = "dateTimeProvider",
    auditorAwareRef = "auditorProvider"
)
public class PersistenceConfig {
    @Bean
    AuditorAware<String> auditorProvider() { return new AditorAwareImpl();}

    @Bean
    public DateTimeProvider dateTimeProvider() { return
        () -> Optional.of(ZonedDateTime.now());
    }
}