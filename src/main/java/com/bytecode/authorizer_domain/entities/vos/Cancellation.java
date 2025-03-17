package com.bytecode.authorizer_domain.entities.vos;

import com.bytecode.authorizer_domain.entities.Card;

import java.time.LocalDateTime;

public record Cancellation(Card card, Authorization originAuthorization, LocalDateTime canceledAt) { }
