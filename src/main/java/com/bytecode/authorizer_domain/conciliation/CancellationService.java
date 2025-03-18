package com.bytecode.authorizer_domain.conciliation;

import com.bytecode.authorizer_domain.authorization.Authorization;
import com.bytecode.authorizer_domain.card.Card;
import com.bytecode.authorizer_domain.card.CardService;
import com.bytecode.authorizer_domain.shared.errors.AuthorizerDomainException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CancellationService {
    private final CancellationRepository cancellationRepository;
    private final CardService cardService;


    protected void handleCancellation(final Card card, final Authorization conciliation) {
        var cancellation = new Cancellation(card, conciliation, LocalDateTime.now());
        this.cancellationRepository.save(cancellation);
        this.cardService.applyCancellationRules(card);
    }

    public int count(final Card card, final LocalDateTime since) {
        try {
            return this.cancellationRepository.count(card, since);
        } catch (Exception e) {
            throw AuthorizerDomainException.fromException(e);
        }
    }
}
