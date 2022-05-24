package io.springbatch.springbatchlecture.skip;

import org.springframework.batch.item.ItemProcessor;

public class SkipItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        if (item.equals("6") || item.equals("7")) {
            throw new SkippableException("Process failed cnt : " + cnt);
        } else {
            System.out.println("ItemProcess : " + item);
            return String.valueOf(Integer.parseInt(item) * -1);
        }
    }
}
