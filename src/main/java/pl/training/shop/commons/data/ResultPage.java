package pl.training.shop.commons.data;

import lombok.Value;

import java.util.List;
import java.util.function.Function;

@Value
public class ResultPage<T> {

    List<T> items;
    long totalPages;
    int pageNumber;

    public <D> ResultPage<D> map(Function<T, D> mapper) {
        return new ResultPage<>(items.stream().map(mapper).toList(), totalPages, pageNumber);
    }

}
