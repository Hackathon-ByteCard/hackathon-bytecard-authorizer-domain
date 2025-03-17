package com.bytecode.authorizer_domain.repositories;

import com.bytecode.authorizer_domain.entities.vos.Authorization;

import java.util.Optional;
import java.util.UUID;

public interface AuthorizationRepository {
    void save(Authorization authorization);
    Optional<Authorization> findOne(UUID code);
}
