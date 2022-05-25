package io.springbatch.springbatchlecture.multithread;

import io.springbatch.springbatchlecture.jdbc.Customer;
import org.springframework.batch.core.ItemProcessListener;

public class MultiItemProcessListener implements ItemProcessListener<Customer, Customer> {

    @Override
    public void beforeProcess(Customer item) {

    }

    @Override
    public void afterProcess(Customer item, Customer result) {
        System.out.println("Thread : " + Thread.currentThread().getName() + "read item : " + item.getId());
    }

    @Override
    public void onProcessError(Customer item, Exception e) {

    }
}
