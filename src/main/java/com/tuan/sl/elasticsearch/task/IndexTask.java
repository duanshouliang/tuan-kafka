package com.tuan.sl.elasticsearch.task;

import com.tuan.sl.elasticsearch.event.IndexEvent;
import com.tuan.sl.elasticsearch.index.indexer.Indexer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@AllArgsConstructor
@Data
public class IndexTask implements Serializable{
    private IndexEvent event;
    private Indexer indexer;
}
