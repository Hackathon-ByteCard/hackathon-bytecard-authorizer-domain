package com.bytecode.authorizer_domain.repositories;

import com.bytecode.authorizer_domain.entities.vos.Cancellation;

public interface CancellationRepository {
    void save(Cancellation cancellation);
}
