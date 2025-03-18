package com.bytecode.authorizer_domain.authorization;

import com.bytecode.authorizer_domain.card.Card;
import com.bytecode.authorizer_domain.card.CardService;
import com.bytecode.authorizer_domain.shared.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.shared.errors.BusinessError;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthorizationService {
    private final AuthorizationRepository authorizationRepository;
    private final CardService cardService;

    public Authorization authorize(final Card card, final Authorization authorization) {
        try {
            card.pay(authorization);

            this.authorizationRepository.save(authorization);
            this.cardService.save(card);

            return authorization;
        } catch (Exception e) {
            throw AuthorizerDomainException.fromException(e);
        }
    }

    public Authorization findAuthorization(Authorization conciliation) {
        var originalAuthorization = this.authorizationRepository.findOne(conciliation.getCode());
        if(Objects.isNull(originalAuthorization)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "findOne should always return an optional instance");
        }

        if(originalAuthorization.isEmpty()) {
            throw new AuthorizerDomainException(BusinessError.ORIGINAL_AUTHORIZATION_NOT_FOUND, "original authorization must exist");
        }

        return originalAuthorization.get();
    }
}
