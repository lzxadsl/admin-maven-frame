package com.lzx.elasticsearch.entity;

import java.util.List;
import java.util.Map;

/**
 * ElasticSearch返回内容<br/>
 * 查询结果
 * @author lizx
 * @data 2016-5-27下午2:14:05
 */
public class EsQueryResult {
    private long hit = 0L;
    private List<Map<String, Object>> buckets;//分组统计数据
    private List<Map<String, Object>> list;//数据列表
    
    public long getHit() {
        return hit;
    }

    public void setHit(long hit) {
        this.hit = hit;
    }

	public List<Map<String, Object>> getBuckets() {
		return buckets;
	}

	public void setBuckets(List<Map<String, Object>> buckets) {
		this.buckets = buckets;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
}
