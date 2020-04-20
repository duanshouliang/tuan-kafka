package com.tuan.sl;

import com.tuan.sl.kafka.KafkaConsumerWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class KafkaApp
{
    public static void main(String[] args){
        ConfigurableApplicationContext ca = SpringApplication.run(KafkaApp.class,args);
    }
}
