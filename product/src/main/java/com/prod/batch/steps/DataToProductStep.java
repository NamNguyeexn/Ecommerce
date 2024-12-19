package com.prod.batch.steps;

import com.prod.batch.DTO.ProductData;
import com.prod.batch.steps.executors.Executor;
import com.prod.batch.steps.processors.ProductProcessor;
import com.prod.batch.steps.readers.ProductDataReader;
import com.prod.batch.steps.writers.ProductWriter;
import com.prod.models.products.Product;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class DataToProductStep {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager manager;
    @Autowired
    private ProductDataReader reader;
    @Autowired
    private ProductProcessor processor;
    @Autowired
    private ProductWriter writer;
    @Autowired
    private Executor executor;
    @Bean
    public Step step() throws Exception{
        return new StepBuilder("Product Step", jobRepository)
                .<ProductData, Product> chunk(1000, manager)
                .reader(reader.read())
                .processor(processor)
                .writer(writer.writer())
                .taskExecutor(executor.getExecutor())
                .build();
    }
}
