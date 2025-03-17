package com.bytecode.authorizer_domain.events;

import com.bytecode.authorizer_domain.events.impls.DomainEvent;

public interface EventSubscriber {
    /// Should not block, please enqueue the event processing if possible
    /// It will be called in parallel with other subscribers, be transactional if possible
    void notify(final DomainEvent domainEvent);
}
