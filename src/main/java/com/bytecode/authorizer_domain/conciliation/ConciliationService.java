package com.bytecode.authorizer_domain.conciliation;

import com.bytecode.authorizer_domain.authorization.Authorization;
import com.bytecode.authorizer_domain.authorization.AuthorizationService;
import com.bytecode.authorizer_domain.card.Card;
import com.bytecode.authorizer_domain.card.CardService;
import com.bytecode.authorizer_domain.shared.bus.events.ClearingEvent;
import com.bytecode.authorizer_domain.shared.bus.EventPublisher;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class ConciliationService {
    private final CancellationRepository cancellationRepository;

    private final CardService cardService;
    private final AuthorizationService authorizationService;

    private static final int MAX_CANCELLATION_COUNT = 2;
    private static final int MAX_CANCELLATION_PERIOD_IN_DAYS = 7;

    public void conciliate(final UUID pan, final Authorization conciliation)  {
        var card = this.cardService.findCard(pan);
        var originalAuthorization = this.authorizationService.findAuthorization(conciliation);

        if(conciliation.equals(originalAuthorization)) {
            EventPublisher.publish(new ClearingEvent(conciliation));
        } else {
            handleCancellation(card, conciliation);
        }
    }

    private void handleCancellation(Card card, Authorization conciliation) {
        this.cancellationRepository.save(new Cancellation(card, conciliation, LocalDateTime.now()));

        var since = LocalDateTime.now().minus(Duration.ofDays(MAX_CANCELLATION_PERIOD_IN_DAYS));
        var cancellationCount = this.cancellationRepository.count(card, since);
        if(cancellationCount > MAX_CANCELLATION_COUNT) {
            card.block();
            this.cardService.save(card);
        }
    }
}
