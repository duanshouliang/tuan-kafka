package com.tuan.sl.elasticsearch.index.runner;

import com.alibaba.fastjson.JSON;
import com.tuan.sl.common.Result;
import com.tuan.sl.elasticsearch.event.IndexEvent;
import com.tuan.sl.elasticsearch.index.indexer.Indexer;
import com.tuan.sl.elasticsearch.task.IndexTask;
import com.tuan.sl.enumeration.IndexEventEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IndexRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexRunner.class);
    private BlockingQueue<IndexTask> tasks;
    private Thread worker;

    public IndexRunner(){
        tasks = new LinkedBlockingQueue<>(200);
        worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()){
                    IndexTask task = null;
                    try {
                        task = tasks.take();
                    } catch (InterruptedException e) {
                        LOGGER.error("Get new task with exception {}, stack {}", e.getMessage(), Arrays.toString(e.getStackTrace()));
                    }
                    if(null == task){
                        continue;
                    }
                    Result<String> result = null;
                    IndexEvent event = task.getEvent();
                    Indexer indexer = task.getIndexer();
                    try {
                        String operate = event.getCmd();
                        if(operate.equalsIgnoreCase(IndexEventEnum.ADD.getValue())){
                            result = indexer.add(event);
                        }else if(operate.equalsIgnoreCase(IndexEventEnum.UPDATE.getValue())){
                            result = indexer.update(event);
                        }else if(operate.equalsIgnoreCase(IndexEventEnum.DELETE.getValue())){
                            result = indexer.delete(event);
                        }else{
                            result = Result.failed("Illegal operation " + operate);
                            throw new Exception("Illegal operation " + operate);
                        }
                    }catch (Exception e){
                        LOGGER.error("Sync data to elasticsearch with exception {}", e.getMessage());
                    }
                    if(null != result && !result.isSuccess()){
                        LOGGER.error("Sync data {} to elasticsearch with failed for the reason that {}", JSON.toJSONString(event), result.getMsg());
                    }
                }
            }
        });

        worker.start();
    }

    public void submitTask(IndexTask task){
        tasks.add(task);
    }
}
