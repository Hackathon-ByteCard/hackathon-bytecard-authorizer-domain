package com.bytecard.domain.authorization;

import com.bytecard.domain.card.Card;
import com.bytecard.domain.card.CardService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthorizationService {
    private final AuthorizationRepository authorizationRepository;
    private final CardService cardService;

    public void authorize(final Card card, final Authorization authorization) {
        this.cardService.pay(card, authorization);
        this.authorizationRepository.save(authorization);
    }

    public Optional<Authorization> findAuthorization(UUID authorizationCode) {
        var authorization = this.authorizationRepository.findOne(authorizationCode);

        if(Objects.isNull(authorization)) {
            return Optional.empty();
        }

        return authorization;
    }
}
