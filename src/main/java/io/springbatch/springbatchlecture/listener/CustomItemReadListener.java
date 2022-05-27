package io.springbatch.springbatchlecture.listener;

import javax.batch.api.chunk.listener.ItemReadListener;

public class CustomItemReadListener implements ItemReadListener {

    @Override
    public void beforeRead() throws Exception {
        System.out.println(">> before Read");
    }

    @Override
    public void afterRead(Object item) throws Exception {
        System.out.println(">> after Read");
    }

    @Override
    public void onReadError(Exception ex) throws Exception {
        System.out.println(">> on Read Error");
    }
}
