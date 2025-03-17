package com.bytecode.authorizer_domain.events;

public interface EventSubscriber {
    void notify(final DomainEvent domainEvent);
}
