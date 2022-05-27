package io.springbatch.springbatchlecture.listener;

import org.springframework.batch.core.ItemProcessListener;

public class CustomItemProcessListener implements ItemProcessListener<Integer, String> {

    @Override
    public void beforeProcess(Integer item) {
        System.out.println(">> before Process");
    }

    @Override
    public void afterProcess(Integer item, String result) {
        System.out.println(">> after Process");
    }

    @Override
    public void onProcessError(Integer item, Exception e) {
        System.out.println(">> on Process Error");
    }
}
