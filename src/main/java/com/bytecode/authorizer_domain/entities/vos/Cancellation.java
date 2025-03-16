package com.bytecode.authorizer_domain.entities.vos;

import java.time.LocalDateTime;

public record Cancellation(Authorization originAuthorization, LocalDateTime canceledAt) { }
