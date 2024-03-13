package pl.training.shop.payments.adapters.persistence.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static pl.training.shop.payments.adapters.persistence.jpa.SearchCriteria.Matcher.EQUAL;
import static pl.training.shop.payments.adapters.persistence.jpa.SearchCriteria.Matcher.START_WITH;

@Transactional
@Component
@RequiredArgsConstructor
@Log
public class JpaExamples implements ApplicationRunner {

    private final JpaPaymentRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // log.info(repository.findByCurrencyCode(DEFAULT_CURRENCY_CODE).toString());
        // log.info(repository.findAllAsPaymentView().toString());
        //repository.findAllAsProjection()
        //        .forEach(row -> log.info(row.getId() + ":" + row.getValue()));

        // var result = repository.findAllAsync();
        // log.info("Is done: " + result.isDone());
        // log.info("Value: " + result.get());

        // log.info(repository.findCompletedWithValue(10, PageRequest.of(0, 10)).getContent().toString());

        /*var examplePaymentEntity = new PaymentEntity();
        examplePaymentEntity.setValue(1010);
        examplePaymentEntity.setCurrencyCode("PLN");

        var matcher = ExampleMatcher.matching()
                .withIgnorePaths("properties")
                .withIgnoreCase()
                .withIgnoreNullValues();

        var example = Example.of(examplePaymentEntity, matcher);

        repository.findAll(example)
                .forEach(result -> log.info(result.toString()));*/

        var specification = new CardSpecification(Set.of(
                new SearchCriteria("currencyCode", "PL", START_WITH),
                new SearchCriteria("value", 1010, EQUAL)
        ));

        repository.findAll(specification)
                .forEach(result -> log.info(result.toString()));
    }

}
