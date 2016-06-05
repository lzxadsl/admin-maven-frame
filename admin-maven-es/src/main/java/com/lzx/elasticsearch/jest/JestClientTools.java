package com.lzx.elasticsearch.jest;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.lzx.elasticsearch.util.PropertiesUtils;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

/**
 * 管理工具类
 * @author lizx
 * @data 2016-5-2下午3:24:46
 */
public class JestClientTools {

	private static Map<String,JestClient> clientMap = new HashMap<String,JestClient>();
	/**
	 * 获取客户端
	 * @author lizx
	 * @data 2016-5-2下午3:49:57
	 * @param index 索引库名称
	 */
	public static JestClient getClient(String index){
		JestClient client = null;
		synchronized(clientMap){
			client = clientMap.get(index);
			if(client == null){
				client = build(index);
				clientMap.put(index, client);
			}
		}
		return client;
	}
	
	/**
	 * 创建客户端
	 * @author lizx
	 * @data 2016-5-2下午3:59:01
	 * @param index 索引库名称
	 */
	private static JestClient build(String index){
		//es的服务端地址
        String connectionUrl = getServiceUrl(index);// 一般都是9200端口  
        HttpClientConfig clientConfig = new HttpClientConfig.Builder(connectionUrl).multiThreaded(true).build();
        //当你用集群时,就有可能会有多个es的服务端,这里我暂时没有集群  
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(clientConfig);
		return factory.getObject();
	}
	
	/**
	 * 获取es索引库地址
	 * @author lizx
	 * @data 2016年5月12日下午7:12:18
	 * @param index 索引名称
	 */
	private static String getServiceUrl(String index){
    	if(StringUtils.isBlank(index)){
    		throw new RuntimeException("请指定一个索引名称！");
    	}
    	String addr = PropertiesUtils.getValue(index+".es.address");
        final String esUrl = addr + ":9200";//PropertiesCacher.getSysProp(addr);
        if(StringUtils.isNotBlank(esUrl)){
            if(StringUtils.startsWithIgnoreCase(esUrl, "http://")){
                return esUrl;
            } else {
                return "http://" + esUrl;
            }
        } else {
            throw new RuntimeException(addr + " not set");
        }
    }
	
	/**
	 * 关闭制定客户端
	 * @author lizx
	 * @data 2016年5月12日下午7:08:23
	 */
	public static void close(String index){
		JestClient client = null;
		synchronized(clientMap){
			client = clientMap.get("index");
		}
		client.shutdownClient();
	}
	
	public static void main(String[] args) {
		String query = "{\"query\":{\"filtered\":{\"query\": {\"bool\": {\"must\": [{\"query_string\": {\"query\": \"**\"}},{\"term\": {\"ityc\": {\"value\": \"14000\"}}}]}}}},\"aggs\":{\"data\": {\"terms\": {\"field\": \"dsco\"}}}}";
		String searchIndex = JestUtil.searchIndex("esdb", null, query);
		System.out.println(searchIndex);
	}
}
