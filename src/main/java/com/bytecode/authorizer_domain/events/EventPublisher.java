package com.bytecode.authorizer_domain.events;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {
    private static final List<EventSubscriber> subscribers = new ArrayList<>();

    public static void subscribe(final EventSubscriber subscriber) {
        EventPublisher.subscribers.add(subscriber);
    }

    public static void unsubscribe(final EventSubscriber subscriber) {
        EventPublisher.subscribers.remove(subscriber);
    }

    public static void publish(final DomainEvent domainEvent) {
        EventPublisher
                .subscribers
                .stream()
                .parallel()
                .forEach((subscriber) -> {
                    subscriber.notify(domainEvent);
                });
    }
}
