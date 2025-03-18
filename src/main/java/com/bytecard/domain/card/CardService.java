package com.bytecard.domain.card;

import com.bytecard.domain.authorization.Authorization;
import com.bytecard.domain.conciliation.CancellationService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CardService {
    private static final int MAX_CANCELLATION_COUNT = 2;
    private static final int MAX_CANCELLATION_PERIOD_IN_DAYS = 7;

    private final CancellationService cancellationService;

    private final CardRepository cardRepository;

    public void pay(final Card card, final Authorization authorization) {
        card.pay(authorization);
        this.cardRepository.save(card);
    }

    public Optional<Card> findCard(final UUID pan) {
        var card = this.cardRepository.findOne(pan);

        if(Objects.isNull(card)) {
            return Optional.empty();
        }

        return card;
    }

    public void restoreLimit(final Card card, final BigDecimal paymentAmount) {
        card.addToAvailableLimit(paymentAmount);
        this.cardRepository.save(card);
    }

    public void applyCancellationRules(final Card card) {
        var since = LocalDateTime.now().minus(Duration.ofDays(MAX_CANCELLATION_PERIOD_IN_DAYS));
        var cancellationCount = this.cancellationService.count(card, since);

        if(cancellationCount > MAX_CANCELLATION_COUNT) {
            card.block();
            this.cardRepository.save(card);
        }
    }
}
