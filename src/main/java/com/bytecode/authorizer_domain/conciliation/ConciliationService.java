package com.bytecode.authorizer_domain.conciliation;

import com.bytecode.authorizer_domain.authorization.Authorization;
import com.bytecode.authorizer_domain.authorization.AuthorizationService;
import com.bytecode.authorizer_domain.card.Card;
import com.bytecode.authorizer_domain.card.CardService;
import com.bytecode.authorizer_domain.shared.bus.events.ClearingEvent;
import com.bytecode.authorizer_domain.shared.bus.EventPublisher;
import com.bytecode.authorizer_domain.shared.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.shared.errors.BusinessError;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class ConciliationService {
    private final AuthorizationService authorizationService;
    private final CancellationService cancellationService;

    public void conciliate(final Card card, final Authorization conciliation)  {
        var originalAuthorization = this.findOriginalAuthorization(conciliation);
        if(conciliation.equals(originalAuthorization)) {
            EventPublisher.publish(new ClearingEvent(conciliation));
            return;
        }

        this.cancellationService.handleCancellation(card, conciliation);
    }

    private Authorization findOriginalAuthorization(final Authorization conciliation) {
        var originalAuthorization = this.authorizationService.findAuthorization(conciliation.getCode());

        if(originalAuthorization.isEmpty()) {
            throw new AuthorizerDomainException(BusinessError.ORIGINAL_AUTHORIZATION_NOT_FOUND, "original authorization must exist");
        }

        return originalAuthorization.get();
    }
}
