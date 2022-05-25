package io.springbatch.springbatchlecture.multithread;

import io.springbatch.springbatchlecture.jdbc.CustomerRowMapper;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

/**
 * 클래스 제목(작업목적)
 *
 * @author hm.sung
 * @since 2022/05/25
 */
public class MultiItemWriterListener implements ItemWriteListener<CustomerRowMapper> {

    @Override
    public void beforeWrite(List<? extends CustomerRowMapper> items) {

    }

    @Override
    public void afterWrite(List<? extends CustomerRowMapper> items) {
        System.out.println("Thread : " + Thread.currentThread().getName() + "read items size : " + items.size());
    }

    @Override
    public void onWriteError(Exception exception, List<? extends CustomerRowMapper> items) {

    }
}
