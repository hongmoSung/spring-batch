package io.springbatch.springbatchlecture.composite;

import org.springframework.batch.item.ItemProcessor;

public class CustomClassifierItemProcessor2 implements ItemProcessor<ProcessInfo, ProcessInfo> {

    @Override
    public ProcessInfo process(ProcessInfo item) {
        System.out.println("CustomClassifierItemProcessor2");
        return item;
    }
}
