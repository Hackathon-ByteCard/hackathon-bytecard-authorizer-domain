package com.bytecard.domain.shared.bus.events;

import com.bytecard.domain.authorization.Authorization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ClearingEvent extends DomainEvent {
    final public Authorization authorization;
}
