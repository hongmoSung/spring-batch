package io.springbatch.springbatchlecture.multithread;

import io.springbatch.springbatchlecture.jdbc.Customer;
import org.springframework.batch.core.ItemReadListener;

public class MultiItemReadListener implements ItemReadListener<Customer> {

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(Customer item) {
        System.out.println("Thread : " + Thread.currentThread().getName() + "read item : " + item.getId());
    }

    @Override
    public void onReadError(Exception ex) {

    }
}
