package com.tuan.sl.elasticsearch.query;

import com.alibaba.fastjson.JSONObject;
import com.tuan.sl.elasticsearch.event.SearchEvent;

import java.util.List;

public abstract class Searcher {
    public List<JSONObject> queryByCondition(SearchEvent event){
        return null;
    }
}
