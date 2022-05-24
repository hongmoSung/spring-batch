package io.springbatch.springbatchlecture.retry.one;

import org.springframework.batch.item.ItemProcessor;

public class RetryItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        cnt++;
        throw new RetryalbeException();
//        return null;
    }
}
