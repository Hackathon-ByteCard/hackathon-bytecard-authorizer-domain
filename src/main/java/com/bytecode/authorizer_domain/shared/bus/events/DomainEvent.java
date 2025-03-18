package com.bytecode.authorizer_domain.shared.bus.events;

public sealed class DomainEvent permits ErrorEvent, ClearingEvent {
}
