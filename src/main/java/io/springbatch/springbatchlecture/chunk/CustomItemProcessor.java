package io.springbatch.springbatchlecture.chunk;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) {
        customer.setName(customer.getName().toUpperCase());
        return customer;
    }
}
