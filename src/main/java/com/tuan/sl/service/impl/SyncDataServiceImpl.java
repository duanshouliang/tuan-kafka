package com.tuan.sl.service.impl;

import com.tuan.sl.elasticsearch.index.runner.IndexRunner;
import com.tuan.sl.kafka.KafkaConsumerWrapper;
import com.tuan.sl.kafka.KafkaContext;
import com.tuan.sl.service.SyncDataService;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Service
public class SyncDataServiceImpl implements SyncDataService {

    @Autowired
    private KafkaContext context;

    @Autowired
    private TransportClient transportClient;

    private Map<String, KafkaConsumerWrapper> kafkaConsumers;
    private ExecutorService executors;


    @PostConstruct
    public void init(){
        kafkaConsumers = new HashMap<>();
        List<String> topics = context.getTopics();
        //创建线程池
        ThreadFactory threadFactory = new DefaultThreadFactory("Sync data executor") {
            @Override
            protected Thread newThread(Runnable r, String name) {
                return new Thread(r, name);
            }
        };
        //创建线程执行器
        executors = Executors.newCachedThreadPool(threadFactory);
        topics.forEach(topic ->{
            IndexRunner indexRunner = new IndexRunner();
            KafkaConsumerWrapper consumer = new KafkaConsumerWrapper(context, indexRunner,transportClient);
            consumer.subscribe(topic);
            kafkaConsumers.put(topic, consumer);
            executors.submit(consumer);
        });
    }


    private void configIndexerClazz(){

    }

    private void configIndexEventHandlerClazz(){

    }
    @Override
    public void syncData() {

    }
}
