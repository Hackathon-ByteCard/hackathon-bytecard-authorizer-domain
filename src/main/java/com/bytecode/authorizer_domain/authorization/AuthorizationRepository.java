package com.bytecode.authorizer_domain.authorization;

import java.util.Optional;
import java.util.UUID;

public interface AuthorizationRepository {
    void save(Authorization authorization);
    Optional<Authorization> findOne(UUID code);
}
