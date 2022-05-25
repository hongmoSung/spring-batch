package io.springbatch.springbatchlecture.async;

import io.springbatch.springbatchlecture.jdbc.Customer;
import io.springbatch.springbatchlecture.jdbc.CustomerRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AsyncConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job asyncJob() throws InterruptedException {
        return jobBuilderFactory.get("asyncJob")
                .incrementer(new RunIdIncrementer())
//                .start(testStep())
                .start(asyncStep())
                .listener(new StopWatchJobListener())
                .build();
    }

    @Bean
    public Step testStep() throws InterruptedException {
        return stepBuilderFactory.get("testStep")
                .<Customer, Customer>chunk(100)
                .reader(pagingItemReader())
                .processor(customItemProcessor())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public ItemProcessor<Customer, Customer> customItemProcessor() throws InterruptedException {
        return new ItemProcessor<Customer, Customer>() {
            @Override
            public Customer process(Customer item) throws Exception {
                Thread.sleep(100);
                return new Customer(item.getId(), item.getFirstName().toUpperCase(), item.getLastName().toUpperCase(),
                        item.getBirthdate());
            }
        };
    }

    @Bean
    public Step asyncStep() throws InterruptedException {
        return stepBuilderFactory.get("asyncStep")
                .<Customer, Customer>chunk(100)
                .reader(pagingItemReader())
                .processor(asyncItemProcessor())
                .writer(asyncItemWriter())
                .build();
    }

    @Bean
    public AsyncItemWriter asyncItemWriter() {
        AsyncItemWriter<Customer> asyncItemWriter = new AsyncItemWriter();
        asyncItemWriter.setDelegate(customItemWriter());
        return asyncItemWriter;
    }

    @Bean
    public ItemProcessor asyncItemProcessor() throws InterruptedException {
        AsyncItemProcessor<Customer, Customer> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(customItemProcessor());
        asyncItemProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return asyncItemProcessor;
    }

    @Bean
    public JdbcPagingItemReader<Customer> pagingItemReader() {
        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource);
        reader.setFetchSize(300);
        reader.setRowMapper(new CustomerRowMapper());

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, firstName, lastName, birthdate");
        queryProvider.setFromClause("from customer");

        Map<String, Order> sorKeys = new HashMap<>();
        sorKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sorKeys);
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter customItemWriter() {
        JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("insert into Customer2 values (:id, :firstName, :lastName, :birthdate)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }
}
