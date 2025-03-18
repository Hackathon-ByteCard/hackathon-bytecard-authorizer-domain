package com.bytecode.authorizer_domain.authorization;

import com.bytecode.authorizer_domain.card.Card;
import com.bytecode.authorizer_domain.card.CardService;
import com.bytecode.authorizer_domain.shared.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.shared.errors.BusinessError;
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
