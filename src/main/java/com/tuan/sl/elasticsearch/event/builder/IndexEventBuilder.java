package com.tuan.sl.elasticsearch.event.builder;

import com.tuan.sl.elasticsearch.handler.IndexEventHandler;
import com.tuan.sl.model.RowEntity;

public class IndexEventBuilder {
    public static IndexEventHandler build(String business){
        return getIndexEventHandlerInstance();
    }

    public static IndexEventHandler getIndexEventHandlerInstance(){
        IndexEventHandler handler = null;
        return handler;
    }
}
