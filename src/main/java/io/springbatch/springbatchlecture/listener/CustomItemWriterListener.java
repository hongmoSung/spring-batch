package io.springbatch.springbatchlecture.listener;

import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

/**
 * 클래스 제목(작업목적)
 *
 * @author hm.sung
 * @since 2022/05/27
 */
public class CustomItemWriterListener implements ItemWriteListener<String> {

    @Override
    public void beforeWrite(List items) {
        System.out.println(">> before Write");
    }

    @Override
    public void afterWrite(List items) {
        System.out.println(">> after Write");
    }

    @Override
    public void onWriteError(Exception exception, List items) {
        System.out.println(">> on Write Error");
    }
}
