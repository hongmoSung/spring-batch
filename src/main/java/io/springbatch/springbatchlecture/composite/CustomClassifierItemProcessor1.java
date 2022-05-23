package io.springbatch.springbatchlecture.composite;

import org.springframework.batch.item.ItemProcessor;

public class CustomClassifierItemProcessor1 implements ItemProcessor<ProcessInfo, ProcessInfo> {

    @Override
    public ProcessInfo process(ProcessInfo item) {
        System.out.println("CustomClassifierItemProcessor1");
        return item;
    }
}
