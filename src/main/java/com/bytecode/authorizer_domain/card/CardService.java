package com.bytecode.authorizer_domain.card;

import com.bytecode.authorizer_domain.shared.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.shared.errors.BusinessError;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public Card findCard(UUID pan) {
        var originalCard = this.cardRepository.findOne(pan);
        if(Objects.isNull(originalCard)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "findOne should always return an optional instance");
        }

        if(originalCard.isEmpty()) {
            throw new AuthorizerDomainException(BusinessError.ORIGINAL_CARD_NOT_FOUND, "original card must exist");
        }

        return originalCard.get();
    }

    public void restoreLimit(final Card card, final BigDecimal paymentAmount) {
        try {
            card.addToAvailableLimit(paymentAmount);
            this.save(card);
        } catch (Exception e) {
            throw AuthorizerDomainException.fromException(e);
        }
    }

    public void save(Card card) {
        this.cardRepository.save(card);
    }
}
