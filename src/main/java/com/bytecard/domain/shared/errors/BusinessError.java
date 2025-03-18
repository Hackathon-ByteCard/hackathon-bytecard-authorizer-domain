package com.bytecard.domain.shared.errors;

import lombok.Getter;

@Getter
public enum BusinessError {
    UNEXPECTED_ERROR("unexpected-error", ErrorNature.TECHNICAL),
    INVARIANT_CONSTRAINT_ERROR("invariant-constraint-error", ErrorNature.FUNCTIONAL),
    INVALID_AUTHORIZATION_AMOUNT("invalid-authorization-amount", ErrorNature.FUNCTIONAL),
    INVALID_AUTHORIZATION_DESCRIPTION("invalid-authorization-description", ErrorNature.FUNCTIONAL),
    INVALID_AUTHORIZATION_TIME("invalid-authorization-time", ErrorNature.FUNCTIONAL),
    INSUFFICIENT_BALANCE("insufficient-balance", ErrorNature.FUNCTIONAL),
    BLOCKED_CARD("blocked-card", ErrorNature.FUNCTIONAL),
    INVALID_PAYMENT_AMOUNT("invalid-payment-amount", ErrorNature.FUNCTIONAL),
    ORIGINAL_AUTHORIZATION_NOT_FOUND("original-authorization-not-found", ErrorNature.FUNCTIONAL),
    INVALID_AVAILABLE_LIMIT("invalid-available-limit", ErrorNature.FUNCTIONAL),
    ORIGINAL_CARD_NOT_FOUND("original-card-not-found", ErrorNature.FUNCTIONAL),
    INVALID_CANCELLATION_TIME("invalid-cancellation-time", ErrorNature.FUNCTIONAL);

    private String code;
    private ErrorNature nature;

    BusinessError(final String code, final ErrorNature nature) {
        this.code = code;
        this.nature = nature;
    }
}
