package io.springbatch.springbatchlecture.chunk;

import org.springframework.batch.item.ItemReader;

import java.util.ArrayList;
import java.util.List;

public class CustomItemReader implements ItemReader<Customer> {

    private final List<Customer> list;
    CustomItemReader(List<Customer> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public Customer read() {

        if (!list.isEmpty()) {
            return list.remove(0);
        }

        return null;
    }
}
