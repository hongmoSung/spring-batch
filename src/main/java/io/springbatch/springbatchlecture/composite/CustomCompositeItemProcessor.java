package io.springbatch.springbatchlecture.composite;

import org.springframework.batch.item.ItemProcessor;

public class CustomCompositeItemProcessor implements ItemProcessor<String, String> {

    int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        cnt++;
        return (item + cnt).toUpperCase();
    }
}
