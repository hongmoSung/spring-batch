package io.springbatch.springbatchlecture.jpa;

import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<CustomerEntity, Customer2> {

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Customer2 process(CustomerEntity customerEntity) {
        return modelMapper.map(customerEntity, Customer2.class);
    }
}
