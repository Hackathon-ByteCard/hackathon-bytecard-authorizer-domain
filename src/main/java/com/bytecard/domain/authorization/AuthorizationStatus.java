package com.bytecard.domain.authorization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum AuthorizationStatus {
    PENDING("pending"),
    POSTED("posted"),
    CANCELED("canceled");

    private final String code;

    public static Optional<AuthorizationStatus> of(final String code) {
        return Arrays.stream(AuthorizationStatus.values())
                .filter((status) -> status.code.equals(code))
                .findFirst();
    }
}
