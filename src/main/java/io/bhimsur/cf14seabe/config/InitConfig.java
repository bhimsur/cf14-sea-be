package io.bhimsur.cf14seabe.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class InitConfig extends AsyncConfigurerSupport implements WebMvcConfigurer {
    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.initialize();
        return new LazyTraceExecutor(beanFactory, threadPoolTaskExecutor);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.setMaxPoolSize(2);
        threadPoolTaskExecutor.initialize();
        return new LazyTraceExecutor(beanFactory, threadPoolTaskExecutor);
    }
}
