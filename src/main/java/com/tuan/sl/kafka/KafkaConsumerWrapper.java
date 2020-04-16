package com.tuan.sl.kafka;

import com.alibaba.fastjson.JSON;
import com.tuan.sl.elasticsearch.index.runner.IndexRunner;
import com.tuan.sl.elasticsearch.task.IndexTask;
import com.tuan.sl.elasticsearch.task.IndexTaskBuilder;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class KafkaConsumerWrapper implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerWrapper.class);

    private static final String GROUP_ID_KEY="group.id";
    private KafkaContext context;
    private KafkaConsumer<String, String> consumer;
    private IndexRunner indexRunner;
    private TransportClient transportClient;

    public KafkaConsumerWrapper(KafkaContext context, IndexRunner indexRunner,TransportClient transportClient){
        this.context = context;
        this.indexRunner = indexRunner;
        this.transportClient = transportClient;
    }
    public void subscribe(String topic){
        List<String> topics = new ArrayList<>();
        topics.addAll(Arrays.asList(topic.split(";")));
        Properties props = context.getConfigs();
        props.put(GROUP_ID_KEY, topic + "_consumer");
        consumer = new KafkaConsumer<>(props);
        ConsumerRebalanceListener listener = new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                LOGGER.info(String.format("Kafka rebalance revoked: %s for topic %s", JSON.toJSONString(partitions), topic));
            }
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                LOGGER.info(String.format("Kafka consumer assigned response %s for topic %s", JSON.toJSONString(partitions),topic));
            }
        };
        consumer.subscribe(topics, listener);
    }
    @Override
    public void run() {
        while (!Thread.interrupted()){
            ConsumerRecords<String, String> records = consumer.poll(100);
            if(records.count() == 0){
                continue;
            }
            records.forEach(record ->{
                LOGGER.info("Message content: "+ record.value() + "partition["+ record.partition()+"] topic: " + record.topic());
                IndexTask indexTask = IndexTaskBuilder.build(record, transportClient);
                indexRunner.submitTask(indexTask);
            });
        }
    }
}
