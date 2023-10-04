package pl.training.shop.payments.adapters.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import static pl.training.shop.payments.adapters.persistence.SearchCriteria.Matcher.EQUAL;
import static pl.training.shop.payments.adapters.persistence.SearchCriteria.Matcher.START_WITH;

@Transactional
@Component
@RequiredArgsConstructor
@Log
public class JpaExamples {

    private final JpaPaymentRepository repository;

    public void start() throws ExecutionException, InterruptedException {
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


        /*var specification = new CardSpecification(Set.of(
                new SearchCriteria("currencyCode", "EU", START_WITH),
                new SearchCriteria("value", 1010, EQUAL)
        ));

        repository.findAll(specification)
                .forEach(result -> log.info(result.toString()));*/
    }

}
