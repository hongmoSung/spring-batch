package io.springbatch.springbatchlecture.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//@Configuration
@RequiredArgsConstructor
public class ClassifierConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job classifierJob() {
        return jobBuilderFactory.get("classifierJob")
                .incrementer(new RunIdIncrementer())
                .start(calssifierStep())
                .build();
    }

    @Bean
    public Step calssifierStep() {
        return stepBuilderFactory.get("classifierStep")
                .<ProcessInfo, ProcessInfo>chunk(10)
                .reader(new ItemReader<ProcessInfo>() {
                    int i = 0;

                    @Override
                    public ProcessInfo read() {
                        i++;
                        ProcessInfo processInfo = ProcessInfo.builder().id(i).build();
                        return i > 3 ? null : processInfo;
                    }
                })
                .processor(customItemProcessor())
                .writer(items -> System.out.println("items = " + items))
                .build();
    }

    @Bean
    public ItemProcessor<? super ProcessInfo, ? extends ProcessInfo> customItemProcessor() {
        ClassifierCompositeItemProcessor<ProcessInfo, ProcessInfo> processor = new ClassifierCompositeItemProcessor<>();

        ProcessorClassifier<ProcessInfo, ItemProcessor<?, ? extends ProcessInfo>> classifier = new ProcessorClassifier<>();

        Map<Integer, ItemProcessor<ProcessInfo, ProcessInfo>> processorMap = new HashMap<>();
        processorMap.put(1, new CustomClassifierItemProcessor1());
        processorMap.put(2, new CustomClassifierItemProcessor2());
        processorMap.put(3, new CustomClassifierItemProcessor3());
        classifier.setProcessorMap(processorMap);
        processor.setClassifier(classifier);

        return processor;
    }
}
