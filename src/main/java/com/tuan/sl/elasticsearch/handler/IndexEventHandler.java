package com.tuan.sl.elasticsearch.handler;


import com.alibaba.fastjson.JSONObject;
import com.tuan.sl.elasticsearch.event.IndexEvent;
import com.tuan.sl.model.RowEntity;

/**
 *
 * 将从kafka中接受到的消息处理成elasticsearchdocument
 *
 */
public class IndexEventHandler {
    public IndexEvent handle(RowEntity rowEntity, String topic){
        return null;
    }
}
