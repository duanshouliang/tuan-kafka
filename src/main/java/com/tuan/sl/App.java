package com.tuan.sl;

import com.tuan.sl.consumer.KafkaConsumerWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App
{
    public static void main(String[] args){
        ConfigurableApplicationContext ca = SpringApplication.run(App.class,args);
        KafkaConsumerWrapper kafkaConsumerWrapper = new KafkaConsumerWrapper();
//        kafkaConsumerWrapper.consume()consume;
    }
}
