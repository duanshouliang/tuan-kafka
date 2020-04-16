package com.tuan.sl.elasticsearch.task;

import com.alibaba.fastjson.JSONObject;
import com.tuan.sl.elasticsearch.event.IndexEvent;
import com.tuan.sl.elasticsearch.handler.indexer.selector.EventHandlerSelector;
import com.tuan.sl.elasticsearch.index.indexer.Indexer;
import com.tuan.sl.elasticsearch.index.indexer.selector.IndexerSelector;
import com.tuan.sl.kafka.KafkaMessageEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.client.transport.TransportClient;

public class IndexerTaskBuilder {
    public static IndexerTask build(ConsumerRecord<String, String> record, TransportClient client){
        KafkaMessageEntity kafkaMessageEntity = JSONObject.parseObject(record.value(), KafkaMessageEntity.class);
        String business = kafkaMessageEntity.getSchemaName() +"_"+ kafkaMessageEntity.getTableName();
        Indexer indexer = IndexerSelector.select(business, client);
        IndexEvent event = EventHandlerSelector.selector(business).handle(kafkaMessageEntity);
        IndexerTask task= new IndexerTask(event, indexer);
        return task;
    }
}
