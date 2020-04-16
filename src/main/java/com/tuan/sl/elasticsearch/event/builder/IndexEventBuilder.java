package com.tuan.sl.elasticsearch.event.builder;

import com.tuan.sl.elasticsearch.handler.indexer.IndexEventHandler;

public class IndexEventBuilder {
    public static IndexEventHandler build(String business){
        return getIndexEventHandlerInstance();
    }

    public static IndexEventHandler getIndexEventHandlerInstance(){
        IndexEventHandler handler = null;
        return handler;
    }
}
