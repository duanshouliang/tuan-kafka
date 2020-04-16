package com.tuan.sl.elasticsearch.task;

import com.alibaba.fastjson.JSONObject;
import com.tuan.sl.elasticsearch.event.IndexEvent;
import com.tuan.sl.elasticsearch.handler.EventHandlerSelector;
import com.tuan.sl.elasticsearch.index.indexer.Indexer;
import com.tuan.sl.elasticsearch.index.indexer.IndexerSelector;
import com.tuan.sl.model.RowEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.client.transport.TransportClient;

public class IndexTaskBuilder {
    public static IndexTask build(ConsumerRecord<String, String> record, TransportClient client){
        String topic = record.topic();
        RowEntity rowEntity = JSONObject.parseObject(record.value(), RowEntity.class);
        String business = rowEntity.getSchemaName() +"_"+ rowEntity.getTableName();
        Indexer indexer = IndexerSelector.select(business, client);
        IndexEvent event = EventHandlerSelector.selector(business).handle(rowEntity, topic);
        IndexTask task= new IndexTask(event, indexer);
        return task;
    }
}
