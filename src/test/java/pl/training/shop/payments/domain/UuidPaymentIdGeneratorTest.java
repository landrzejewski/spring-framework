package pl.training.shop.payments.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static pl.training.shop.payments.UuidMatcher.isUuid;

class UuidPaymentIdGeneratorTest {

    private final UuidPaymentIdGenerator generator = new UuidPaymentIdGenerator();

    @DisplayName("when get next")
    @Nested
    class WhenGetNext {

        @Test
        void then_returns_valid_id() {
            var id = generator.getNext();
            assertThat(id, isUuid());
        }

        @Test
        void then_returns_unique_id() {
            assertNotEquals(generator.getNext(), generator.getNext());
        }

    }

}
