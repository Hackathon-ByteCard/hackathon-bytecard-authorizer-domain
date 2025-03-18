package com.bytecode.authorizer_domain.authorization;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum AuthorizationStatus {
    PENDING("pending"),
    POSTED("posted"),
    CANCELED("canceled");

    private final String code;

    public Optional<AuthorizationStatus> of(final String code) {
        return Arrays.stream(AuthorizationStatus.values())
                .filter((status) -> status.code.equals(code))
                .findFirst();
    }

    AuthorizationStatus(final String code) {
        this.code = code;
    }
}
