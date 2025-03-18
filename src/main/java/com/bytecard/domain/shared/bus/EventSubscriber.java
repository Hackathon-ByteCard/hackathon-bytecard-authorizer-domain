package com.bytecard.domain.shared.bus;

import com.bytecard.domain.shared.bus.events.DomainEvent;

public interface EventSubscriber {
    /// Should not block, please enqueue the event processing if possible
    /// It will be called in parallel with other subscribers, be transactional if possible
    void notify(final DomainEvent domainEvent);
}
