package pl.training.shop.commons.data;

import lombok.Value;

import java.util.List;

@Value
public class ResultPage<T> {

    List<T> items;
    long totalPages;
    int pageNumber;

}
