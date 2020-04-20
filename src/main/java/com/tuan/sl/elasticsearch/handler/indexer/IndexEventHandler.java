package com.tuan.sl.elasticsearch.handler.indexer;


import com.alibaba.fastjson.JSONObject;
import com.tuan.sl.elasticsearch.event.IndexEvent;
import com.tuan.sl.kafka.KafkaMessageEntity;

/**
 *
 * 将从kafka中接受到的消息处理成elasticsearchdocument
 *
 */
public class IndexEventHandler {
    public IndexEvent handle(KafkaMessageEntity entity){
        return new IndexEvent();
    }
}
