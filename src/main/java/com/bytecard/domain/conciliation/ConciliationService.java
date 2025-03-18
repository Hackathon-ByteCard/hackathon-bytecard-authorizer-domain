package com.bytecard.domain.conciliation;

import com.bytecard.domain.authorization.Authorization;
import com.bytecard.domain.authorization.AuthorizationService;
import com.bytecard.domain.card.Card;
import com.bytecard.domain.shared.bus.events.ClearingEvent;
import com.bytecard.domain.shared.bus.EventPublisher;
import com.bytecard.domain.shared.errors.AuthorizerDomainException;
import com.bytecard.domain.shared.errors.BusinessError;
import lombok.RequiredArgsConstructor;

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
