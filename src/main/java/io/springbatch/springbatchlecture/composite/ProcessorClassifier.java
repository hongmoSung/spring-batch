package io.springbatch.springbatchlecture.composite;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;

public class ProcessorClassifier<C, T> implements Classifier<C, T> {

    private Map<Integer, ItemProcessor<ProcessInfo, ProcessInfo>> processorMap = new HashMap<>();

    @Override
    public T classify(C classifiable) {
        return (T)processorMap.get(((ProcessInfo)classifiable).getId());
    }

    public void setProcessorMap(Map<Integer, ItemProcessor<ProcessInfo, ProcessInfo>> processorMap) {
        this.processorMap = processorMap;
    }
}
