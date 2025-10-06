package org.ide.qoribet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class QoribetApplication {

    public static void main(String[] args) {
        SpringApplication.run(QoribetApplication.class, args);
    }

    // Configuramos un ThreadPoolTaskExecutor para manejar tareas asíncronas
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        // Definimos un ThreadPool con parámetros personalizados
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Configuraciones del pool
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Qoribet-Async-");
        executor.initialize();
        return executor;
    }
}
