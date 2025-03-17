package com.bytecode.authorizer_domain.entities;

import java.time.LocalDateTime;

public record Cancellation(Card card, Authorization originAuthorization, LocalDateTime canceledAt) { }
