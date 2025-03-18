package com.bytecard.domain.shared.bus.events;

public sealed class DomainEvent permits ErrorEvent, ClearingEvent {
}
