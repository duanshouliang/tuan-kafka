package com.tuan.sl.elasticsearch.index.indexer;

import com.tuan.sl.common.Result;
import com.tuan.sl.elasticsearch.event.IndexEvent;
import org.elasticsearch.client.transport.TransportClient;


public class CanalTestTableIndexer extends Indexer {
    public CanalTestTableIndexer(TransportClient transportClient) {
        super.transportClient=transportClient;
    }

    @Override
    public Result<String> add(IndexEvent event) {
        return super.add(event);
    }

    @Override
    public Result<String> update(IndexEvent event) {
        return super.update(event);
    }

    @Override
    public Result<String> delete(IndexEvent event) {
        return super.delete(event);
    }
}
