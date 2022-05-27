package io.springbatch.springbatchlecture.listener;

import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener<Integer, String> {
    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println(">> onSkipRead : " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(String item, Throwable t) {
        System.out.println(">> onSkipWrite : " + item);
        System.out.println(">> onSkipWrite : " + t.getMessage());
    }

    @Override
    public void onSkipInProcess(Integer item, Throwable t) {
        System.out.println(">> onSkipInProcess : " + item);
        System.out.println(">> onSkipInProcess : " + t.getMessage());
    }
}
