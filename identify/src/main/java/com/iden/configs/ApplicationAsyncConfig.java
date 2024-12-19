package com.iden.configs;

import com.common.exceptions.CustomAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ApplicationAsyncConfig {
    @Bean(name = "MailExecutor")
    public ThreadPoolTaskExecutor mailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("MailThread::");
        executor.initialize();
        return executor;
    }
    @Bean(name = "BatchExecutor")
    public ThreadPoolTaskExecutor batchExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("BatchThread::");
        executor.initialize();
        return executor;
    }
    @Bean(name = "TransactionalExecutor")
    public ThreadPoolTaskExecutor transactionalExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("TransactionalThread::");
        executor.initialize();
        return executor;
    }
    @Bean
    public CustomAsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler(){
        return new CustomAsyncUncaughtExceptionHandler();
    }
}