package com.gmbh.itdeveloper.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@Configuration
@ComponentScan(basePackages = {"com.gmbh.itdeveloper.service","com.gmbh.itdeveloper.dao"})
public class AppConfig {

    @Bean("forkJoinPool")
    @Qualifier("forkJoinPool")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ForkJoinPool createForkJoin(){
        return new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {

                    }
                }, true);
    }

    @Bean("cexecutorService")
    @Qualifier("cexecutorService")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ExecutorService createExecutorService(){
        return Executors.newFixedThreadPool(10);
    }
}
