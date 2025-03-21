package com.bytecard.domain;

import com.bytecard.domain.authorization.Authorization;
import com.bytecard.domain.authorization.AuthorizationService;
import com.bytecard.domain.card.Card;
import com.bytecard.domain.card.CardService;
import com.bytecard.domain.conciliation.ConciliationService;
import com.bytecard.domain.shared.bus.EventPublisher;
import com.bytecard.domain.shared.bus.EventSubscriber;
import com.bytecard.domain.shared.bus.events.ErrorEvent;
import com.bytecard.domain.shared.errors.AuthorizerDomainException;
import com.bytecard.domain.shared.errors.BusinessError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizerTest {
    @Mock
    AuthorizationService authorizationService;

    @Mock
    CardService cardService;

    @Mock
    ConciliationService conciliationService;

    @Mock
    EventSubscriber eventSubscriber;

    @Mock
    Card card;

    @Mock
    Authorization authorization;

    @InjectMocks Authorizer authorizer;

    @BeforeEach
    public void setup() {
        EventPublisher.unsubscribe(eventSubscriber);
        EventPublisher.subscribe(eventSubscriber);
    }
    
    @Nested
    class ResponsibilityOrchestration {
        @Test
        public void authorize() {
            authorizer.authorize(card, authorization);
            verify(authorizationService, times(1)).authorize(card, authorization);
        }

        @Test
        public void conciliate() {
            authorizer.conciliate(card, authorization);
            verify(conciliationService, times(1)).conciliate(card, authorization);

        }

        @Test
        public void restoreLimit() {
            final var paymentValue = BigDecimal.valueOf(100.56);
            authorizer.restoreLimit(card, paymentValue);
            verify(cardService, times(1)).restoreLimit(card, paymentValue);
        }

        @Test
        public void findCard() {
            final var pan = UUID.randomUUID();
            final Optional<Card> card = authorizer.findCard(pan);
            Assertions.assertNotNull(card);
            verify(cardService, times(1)).findCard(pan);
        }

        @Test
        public void findAuthorization() {
            final var authorizationCode = UUID.randomUUID();
            final Optional<Authorization> authorization = authorizer.findAuthorization(authorizationCode);
            Assertions.assertNotNull(authorization);
            verify(authorizationService, times(1)).findAuthorization(authorizationCode);
        }
    }

    @Nested
    class GracefulUnexpectedExceptionHandling {
        private final Exception unexpectedException = new RuntimeException("Are You Winning Son?");

        @Test
        public void authorize() {
            doThrow(unexpectedException).when(authorizationService).authorize(card, authorization);

            var thrown = Assertions.assertThrows(AuthorizerDomainException.class,
                    () -> authorizer.authorize(card, authorization));

            Assertions.assertEquals(BusinessError.UNEXPECTED_ERROR, thrown.getBusinessError());
            verify(eventSubscriber, times(1)).notify(any(ErrorEvent.class));
        }

        @Test
        public void conciliate() {
            doThrow(unexpectedException).when(conciliationService).conciliate(card, authorization);

            var thrown = Assertions.assertThrows(AuthorizerDomainException.class,
                    () -> authorizer.conciliate(card, authorization));

            Assertions.assertEquals(BusinessError.UNEXPECTED_ERROR, thrown.getBusinessError());
            verify(eventSubscriber, times(1)).notify(any(ErrorEvent.class));
        }

        @Test
        public void restoreLimit() {
            var paymentValue = BigDecimal.valueOf(1000);
            doThrow(unexpectedException).when(cardService).restoreLimit(card, paymentValue);

            var thrown = Assertions.assertThrows(AuthorizerDomainException.class,
                    () -> authorizer.restoreLimit(card, paymentValue));

            Assertions.assertEquals(BusinessError.UNEXPECTED_ERROR, thrown.getBusinessError());
            verify(eventSubscriber, times(1)).notify(any(ErrorEvent.class));
        }

        @Test
        public void findCard() {
            var pan = UUID.randomUUID();
            doThrow(unexpectedException).when(cardService).findCard(pan);

            var thrown = Assertions.assertThrows(AuthorizerDomainException.class,
                    () -> authorizer.findCard(pan));

            Assertions.assertEquals(BusinessError.UNEXPECTED_ERROR, thrown.getBusinessError());
            verify(eventSubscriber, times(1)).notify(any(ErrorEvent.class));
        }

        @Test
        public void findAuthorization() {
            var authorizationCode = UUID.randomUUID();
            doThrow(unexpectedException).when(authorizationService).findAuthorization(authorizationCode);

            var thrown = Assertions.assertThrows(AuthorizerDomainException.class,
                    () -> authorizer.findAuthorization(authorizationCode));

            Assertions.assertEquals(BusinessError.UNEXPECTED_ERROR, thrown.getBusinessError());
        }
    }
}
