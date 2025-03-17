package com.bytecode.authorizer_domain.entities;

import com.bytecode.authorizer_domain.entities.vos.Authorization;
import com.bytecode.authorizer_domain.entities.vos.Cancellation;
import com.bytecode.authorizer_domain.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.errors.BusinessError;
import com.bytecode.authorizer_domain.events.ClearingEvent;
import com.bytecode.authorizer_domain.events.EventPublisher;
import com.bytecode.authorizer_domain.repositories.AuthorizationRepository;
import com.bytecode.authorizer_domain.repositories.CancellationRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
public class ConciliationService {
    private final CancellationRepository cancellationRepository;
    private final AuthorizationRepository authorizationRepository;

    public void conciliate(final Card card, final Authorization conciliation)  {
        var originalAuthorization = this.authorizationRepository.findOne(conciliation.code());
        if(Objects.isNull(originalAuthorization)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "findOne should always return an optional instance");
        }

        if(originalAuthorization.isEmpty()) {
            throw new AuthorizerDomainException(BusinessError.ORIGINAL_AUTHORIZATION_NOT_FOUND, "original authorization must exist");
        }

        if(conciliation.equals(originalAuthorization.get())) {
            EventPublisher.publish(new ClearingEvent(conciliation));
        } else {
            this.cancellationRepository.save(new Cancellation(card, conciliation, LocalDateTime.now()));
        }
    }
}
