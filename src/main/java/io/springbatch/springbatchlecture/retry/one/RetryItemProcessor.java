package io.springbatch.springbatchlecture.retry.one;

import org.springframework.batch.item.ItemProcessor;

public class RetryItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        if (item.equals("2") || item.equals("3")) {
            cnt++;
            throw new RetryalbeException("failed count -> " + cnt);
        }
        return item;
    }
}
