package com.lzx.elasticsearch.entity;

import java.util.List;
import java.util.Map;

/**
 * ElasticSearch返回内容<br/>
 * 
 * @author lizx
 * @data 2016-5-27下午2:14:05
 */
public class EsResult {
    private long hit = 0L;
    private List<Map<String, String>> datas;
    private List<Map<String, Object>> buckets;//分组统计数据
    private List<Map<String, Object>> list;//跟datas一样，主要是扩展出来满足map中值不是字符串类型
    
    public long getHit() {
        return hit;
    }

    public void setHit(long hit) {
        this.hit = hit;
    }

    public List<Map<String, String>> getDatas() {
        return datas;
    }

    public void setDatas(List<Map<String, String>> datas) {
        this.datas = datas;
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
