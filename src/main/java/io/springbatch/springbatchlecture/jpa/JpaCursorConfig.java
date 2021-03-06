package io.springbatch.springbatchlecture.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class JpaCursorConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final int chunkSize = 10;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job jpaJob() {
        return jobBuilderFactory.get("jpaJob")
                .start(jpaStep1())
                .build();
    }

    @Bean
    public Step jpaStep1() {
        return stepBuilderFactory.get("jpaStep1")
                .<CustomerEntity, Customer2>chunk(chunkSize)
                .reader(customerItemReader())
                .processor(customItemProcessor())
                .writer(customItermWriter())
                .build();
    }

    @Bean
    public ItemProcessor<? super CustomerEntity, ? extends Customer2> customItemProcessor() {
        return new CustomItemProcessor2();
    }

    @Bean
    public ItemReader<CustomerEntity> customerItemReader() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstName", "A%");

        return new JpaCursorItemReaderBuilder<CustomerEntity>()
                .name("jpaCursorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from CustomerEntity c where c.firstName like :firstName")
                .parameterValues(parameters)
                .build();

    }

    @Bean
    public ItemWriter<Customer2> customItermWriter() {
        return new JpaItemWriterBuilder<Customer2>()
                .usePersist(true)
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
