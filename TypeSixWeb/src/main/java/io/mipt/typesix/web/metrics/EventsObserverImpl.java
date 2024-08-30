package io.mipt.typesix.web.metrics;

import io.micrometer.core.instrument.Counter;
import io.mipt.typesix.businesslogic.service.core.spi.EventsObserver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EventsObserverImpl implements EventsObserver {
    private final Counter loginCounter;
    private final Counter registrationCounter;

    public EventsObserverImpl(
            @Qualifier("login_counter") Counter loginCounter,
            @Qualifier("registration_counter") Counter registrationCounter
    ) {
        this.loginCounter = loginCounter;
        this.registrationCounter = registrationCounter;
    }

    @Override
    public void onLogin(String email) {
        loginCounter.increment();
    }

    @Override
    public void onInternalRegistration(String email) {
        registrationCounter.increment();
    }

    @Override
    public void onFederatedRegistration(String email) {
        registrationCounter.increment();
    }

    @Override
    public void onTokenGenerated(String email) {

    }
}
