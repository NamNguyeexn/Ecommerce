package com.prod.batch.jobs;

import com.prod.batch.steps.DataToProductStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JobProduct {
    @Autowired
    private DataToProductStep dataToProductStep;
    @Autowired
    private JobRepository jobRepository;
    @Bean
    public Job getJob() throws Exception{
        return new JobBuilder("Import Product", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(dataToProductStep.step())
                .build();
    }
}
