package com.bytecode.authorizer_domain.repositories;

import com.bytecode.authorizer_domain.entities.vos.Authorization;

public interface AuthorizationRepository {
    void save(Authorization authorization);
}
