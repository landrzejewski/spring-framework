package pl.training.shop.payments.integration;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.training.shop.Application;
import pl.training.shop.payments.adapters.persistence.jpa.PaymentEntity;

import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.training.shop.payments.PaymentsFixtures.*;
import static pl.training.shop.payments.domain.PaymentStatus.STARTED;

@WithMockUser(roles = "ADMIN")
@SpringBootTest(classes = Application.class, webEnvironment = DEFINED_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class PaymentRestAdapterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;

    private PaymentEntity initDatabase(String status) {
        var paymentEntity = createEntity(status);
        entityManager.persist(paymentEntity);
        entityManager.flush();
        return paymentEntity;
    }

    @Transactional
    @Test
    void given_payment_exists_when_get_by_id_then_returns_payment() throws Exception {
        var statusName = STARTED.name();
        initDatabase(statusName);
        mockMvc.perform(get("/api/payments/" + PAYMENT_ID).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(PAYMENT_ID)))
                .andExpect(jsonPath("$.value", is(PAYMENT.getValue().toString())))
                .andExpect(jsonPath("$.status", is(statusName)));
    }

}
