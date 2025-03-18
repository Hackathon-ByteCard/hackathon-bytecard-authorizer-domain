package com.bytecode.authorizer_domain.shared.bus;

import com.bytecode.authorizer_domain.shared.bus.events.DomainEvent;

public interface EventSubscriber {
    /// Should not block, please enqueue the event processing if possible
    /// It will be called in parallel with other subscribers, be transactional if possible
    void notify(final DomainEvent domainEvent);
}
