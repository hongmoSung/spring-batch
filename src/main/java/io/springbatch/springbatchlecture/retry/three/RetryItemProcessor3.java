package io.springbatch.springbatchlecture.retry.three;

import io.springbatch.springbatchlecture.jpa.Customer2;
import io.springbatch.springbatchlecture.retry.one.RetryalbeException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.classify.Classifier;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

public class RetryItemProcessor3 implements ItemProcessor<String, CustomerDto> {

    @Autowired
    private RetryTemplate retryTemplate;
    private int cnt;

    @Override
    public CustomerDto process(String item) throws Exception {

        Classifier<Throwable, Boolean> rollbackClassifier = new BinaryExceptionClassifier(true);

        CustomerDto customerDto = retryTemplate.execute(new RetryCallback<CustomerDto, RuntimeException>() {
            @Override
            public CustomerDto doWithRetry(RetryContext context) throws RuntimeException {

                if (item.equals("1") || item.equals("2")) {
                    cnt++;
                    throw new RetryalbeException("failed cnt " + cnt);
                }
                return new CustomerDto(item);
            }
        }, new RecoveryCallback<CustomerDto>() {
            @Override
            public CustomerDto recover(RetryContext context) throws Exception {
                return new CustomerDto(item);
            }
        }, new DefaultRetryState(item, rollbackClassifier));
        return customerDto;
    }
}
