package io.mipt.typesix.web.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {
    @Bean
    @Qualifier("login_counter")
    public Counter loginCounter(MeterRegistry meterRegistry) {
        return meterRegistry.counter("type6LoginCounter");
    }

    @Bean
    @Qualifier("registration_counter")
    public Counter registrationCounter(MeterRegistry meterRegistry) {
        return meterRegistry.counter("type6RegistrationCounter");
    }
}
