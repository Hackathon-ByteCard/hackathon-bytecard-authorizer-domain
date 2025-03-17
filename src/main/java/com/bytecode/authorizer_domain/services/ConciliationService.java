package com.bytecode.authorizer_domain.services;

import com.bytecode.authorizer_domain.entities.Authorization;
import com.bytecode.authorizer_domain.entities.Cancellation;
import com.bytecode.authorizer_domain.entities.Card;
import com.bytecode.authorizer_domain.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.errors.BusinessError;
import com.bytecode.authorizer_domain.events.impls.ClearingEvent;
import com.bytecode.authorizer_domain.events.EventPublisher;
import com.bytecode.authorizer_domain.repositories.AuthorizationRepository;
import com.bytecode.authorizer_domain.repositories.CancellationRepository;
import com.bytecode.authorizer_domain.repositories.CardRepository;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
public class ConciliationService {
    private final CancellationRepository cancellationRepository;
    private final AuthorizationRepository authorizationRepository;
    private final CardRepository cardRepository;

    private static final int MAX_CANCELLATION_COUNT = 2;
    private static final int MAX_CANCELLATION_PERIOD_IN_DAYS = 7;

    public void conciliate(final Card card, final Authorization conciliation)  {
        var originalAuthorization = this.authorizationRepository.findOne(conciliation.getCode());
        if(Objects.isNull(originalAuthorization)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "findOne should always return an optional instance");
        }

        if(originalAuthorization.isEmpty()) {
            throw new AuthorizerDomainException(BusinessError.ORIGINAL_AUTHORIZATION_NOT_FOUND, "original authorization must exist");
        }

        if(conciliation.equals(originalAuthorization.get())) {
            EventPublisher.publish(new ClearingEvent(conciliation));
        } else {
            processCancellation(card, conciliation);
        }
    }

    private void processCancellation(Card card, Authorization conciliation) {
        this.cancellationRepository.save(new Cancellation(card, conciliation, LocalDateTime.now()));

        var since = LocalDateTime.now().minus(Duration.ofDays(MAX_CANCELLATION_PERIOD_IN_DAYS));
        var cancellationCount = this.cancellationRepository.count(card, since);
        if(cancellationCount > MAX_CANCELLATION_COUNT) {
            card.block();
            cardRepository.save(card);
        }
    }
}
