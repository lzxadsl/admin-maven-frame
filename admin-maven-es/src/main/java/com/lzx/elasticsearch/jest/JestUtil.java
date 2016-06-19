package com.lzx.elasticsearch.jest;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lzx.elasticsearch.entity.EsQueryResult;

/**
 * 操作工具类
 * @author lizx
 * @data 2016-5-2下午4:02:12
 */
public class JestUtil {

	/**
	 * 查询索引
	 * @author lizx
	 * @data 2016-5-2下午4:02:38
	 * @param index 索引名称
	 * @param query 查询语句，DSL风格的
	 */
	public static String searchIndex(String index,String query){
		return searchIndex(index,"",query);
	}
	
	/**
	 * 查询索引
	 * @author lizx
	 * @data 2016-5-2下午4:02:38
	 * @param index 索引名称
	 * @param type 类型，为空默认是查全部
	 * @param query 查询语句，DSL风格的
	 */
	public static String searchIndex(String index,String type,String query){
		List<String> types = new ArrayList<String>();
		if(StringUtils.isNotBlank(type))
			types.add(type);
		return searchIndex(index,types,query);
	}
	
	/**
	 * 查询索引
	 * @author lizx
	 * @data 2016-5-2下午4:02:38
	 * @param index 索引名称
	 * @param types 类型(多个)，为空默认是查全部
	 * @param query 查询语句，DSL风格的
	 */
	public static String searchIndex(String index,List<String> types,String query){
		SearchResult execute = searchIndexHandle(index,types,query);
		return execute.getJsonString();
	}
	
	/**
	 * 查询索引处理
	 * @author lizx
	 * @data 2016-5-2下午4:02:38
	 * @param index 索引名称
	 * @param type 类型，为空默认是查全部
	 * @param query 查询语句，DSL风格的
	 */
	public static SearchResult searchIndexHandle(String index,List<String> types,String query){
		JestClient client = JestClientTools.getClient(index);
		Builder build = new Search.Builder(query).addIndex(index);
		if(types != null && types.size() > 0){
			build.addType(types);
		}
		Search search = build.build();
		try {
			SearchResult execute = client.execute(search);
			return execute;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 单个查询
	 * @author lizx
	 * @data 2016-5-27下午1:52:29
	 * @param index 
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public static Map<String ,Object> searchOne(String index,String id){
		JestClient client = JestClientTools.getClient(index);
		io.searchbox.core.Get.Builder builder = new Get.Builder(index,id);
		Get build = builder.build();
		try {
			DocumentResult execute = client.execute(build);
			Map<String,Object> sourceAsObject = new Gson().fromJson(execute.getJsonString(), new TypeToken<Map<String, Object>>(){}.getType());
			Map<String,Object> _source = (Map<String, Object>) sourceAsObject.get("_source");
			_source.put("_index", sourceAsObject.get("_index"));
			_source.put("_type", sourceAsObject.get("_type"));
			_source.put("_id", sourceAsObject.get("_id"));
			return _source;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除文档
	 * @author lizx
	 * @data 2016-5-26下午5:01:14
	 * @param index
	 * @param type
	 * @param id
	 */
	public static Boolean delDoc(String index,String type,String id){
		if(StringUtils.isBlank(index) || StringUtils.isBlank(type) || StringUtils.isBlank(id)){
			throw new RuntimeException("请检查参数index、type、id是否为空！");
		}
		Boolean result = null;
		JestClient client = JestClientTools.getClient(index);
		io.searchbox.core.Delete.Builder builder = new Delete.Builder(id).index(index).type(type);
		Delete build = builder.build();
		try {
			DocumentResult execute = client.execute(build);
			if(StringUtils.isNotBlank(execute.getErrorMessage()))
				throw new RuntimeException(execute.getErrorMessage());
			result = execute.isSucceeded();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 添加文档
	 * @author lizx
	 * @data 2016-5-26下午5:16:42
	 * @param index 
	 * @param type 
	 * @param id id为空时es会自动生成
	 * @param source 可以是对象，也可以是JSON串（不能包含_index,_type,_id这几个属性）
	 * @param return id
	 */
	public static String addDoc(String index,String type,String id,Object source){
		if(StringUtils.isBlank(index) || StringUtils.isBlank(type) || source == null){
			throw new RuntimeException("请检查参数index、type、source是否为空！");
		}
		JestClient client = JestClientTools.getClient(index);
		io.searchbox.core.Index.Builder builder = new Index.Builder(source).index(index).type(type).id(id);
		builder.refresh(true);
		Index build = builder.build();
		try {
			DocumentResult execute = client.execute(build);
			if(StringUtils.isNotBlank(execute.getErrorMessage()))
				throw new RuntimeException(execute.getErrorMessage());
			return execute.getId();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更新文档
	 * @author lizx
	 * @data 2016-5-26下午5:16:42
	 * @param index 
	 * @param type 
	 * @param id
	 * @param source Map（必须包含_index,_type,_id这几个属性）
	 * @param return id
	 */
	public static String updateDoc(Map<String, Object> source){
		try {
			String index = source.get("_index").toString();
			String type = source.get("_type").toString();
			String id = source.get("_id").toString();
			if(StringUtils.isBlank(index) || StringUtils.isBlank(type) || StringUtils.isBlank(id)){
				throw new RuntimeException("请检查属性_index、_type、_id是否为空！");
			}
			source.remove("_id");
			source.remove("_index");
			source.remove("_type");
			JestClient client = JestClientTools.getClient(index);
			io.searchbox.core.Index.Builder builder = new Index.Builder(source).index(index).type(type).id(id);
			builder.refresh(true);
			Index build = builder.build();
			DocumentResult execute = client.execute(build);
			if(StringUtils.isNotBlank(execute.getErrorMessage()))
				throw new RuntimeException(execute.getErrorMessage());
			return execute.getId();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更新文档
	 * @author lizx
	 * @data 2016-5-26下午5:16:42
	 * @param index 
	 * @param type 
	 * @param id
	 * @param source 可以是对象，也可以是JSON串（不能包含_index,_type,_id这几个属性）
	 * @param return id
	 */
	public static String updateDoc(String index,String type,String id,Object source){
		//GsonUtils.toJson(source)
		try {
			if(StringUtils.isBlank(index) || StringUtils.isBlank(type) || StringUtils.isBlank(id) || source == null){
				throw new RuntimeException("请检查参数index、type、id、source是否为空！");
			}
			JestClient client = JestClientTools.getClient(index);
			io.searchbox.core.Index.Builder builder = new Index.Builder(source).index(index).type(type).id(id);
			builder.refresh(true);
			Index build = builder.build();
			DocumentResult execute = client.execute(build);
			if(StringUtils.isNotBlank(execute.getErrorMessage()))
				throw new RuntimeException(execute.getErrorMessage());
			return execute.getId();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 创建索引
	 * @author lizx
	 * @data 2016-5-27下午3:59:05
	 * @param index 索引名称
	 * @param settings 设置项
	 */
	public static Boolean creatIndex(String index,Object settings){
		if(StringUtils.isBlank(index)){
			throw new RuntimeException("index不能为空！");
		}
		JestClient client = JestClientTools.getClient(index);
		io.searchbox.indices.CreateIndex.Builder builder = new CreateIndex.Builder(index).settings(settings);
		CreateIndex build = builder.build();
		try {
			JestResult execute = client.execute(build);
			if(StringUtils.isNotBlank(execute.getErrorMessage()))
				throw new RuntimeException(execute.getErrorMessage());
			return execute.isSucceeded();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		/*String query = "";
		String index = "lzxes";
		JestClient client = JestClientTools.getClient(index);
		Builder build = new Search.Builder(query).addIndex(index);
		List<String> types = new ArrayList<String>();
		types.add("reportdata_201601");
		types.add("reportdata_201602");
		build.addType(types);
		Search search = build.build();
		try {
			SearchResult execute = client.execute(search);
			System.out.println(execute.getJsonString());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//System.out.println(delDoc("lzxes","events","AVTmrG3Nr0crVjYKgIbh"));
		//AVTxAw6gU57NoMbt1SN_
		/*Map<String , Object> set = new HashMap<>();
		set.put("number_of_shards", 3);
		set.put("number_of_replicas", 2);
		creatIndex("lzxes",set);*/
		/*delDoc("lzxes",null,"AVTxAw6gU57NoMbt1SN_");
		Map<String, Object> searchOne = searchOne("lzxes","AVTmpkAUr0crVjYKgHwl");
		String id = searchOne.get("_id").toString();
		searchOne.remove("_id");
		searchOne.remove("_index");
		searchOne.remove("_type");
		searchOne.put("opt", "李知县1");
		updateDoc("lzxes", "events", id,searchOne);*/
		//Map<String, Object> map = new HashMap<String, Object>();
		//map.put("name", "lzx");
		//addDoc("lzxes", "events", null, map);
	}
	
	/**
     * 解析es返回的json数据
     * @param json
     * @param withIndexAndType 每条数据是否要附带_index和_type
     * @return
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static EsQueryResult wrapSearchResult(String json, boolean withIndexAndType){
        EsQueryResult esResult = new EsQueryResult();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Map<String, Object> jsonMap = gson.fromJson(json, Map.class);
        Map<String, Object> hits = (Map<String, Object>) jsonMap.get("hits");
        if(null != hits) {
            Object total = hits.get("total");
            long hit = 0L;
            if (total instanceof Double) {
                hit = ((Double) total).longValue();
            } else {
                hit = Long.valueOf("" + total);
            }
            esResult.setHit(hit);

            List<Map> datas = (List<Map>) hits.get("hits");
            if(null != datas){
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for(Map sm : datas){
                	Map source = (Map) sm.get("_source");
                    if(null != source) {
                        Map<String, Object> tmpMap = new HashMap<String, Object>(source);
                        if(withIndexAndType){
                            tmpMap.put("_index",  sm.get("_index"));
                            tmpMap.put("_type",  sm.get("_type"));
                        }
                        list.add(tmpMap);
                    }
                }
                esResult.setList(list);
            }
            //分组统计处理
            Map aggr = (Map)jsonMap.get("aggregations");
            if(null != aggr){
            	Map group = (Map)aggr.get("data");
	            List<Map<String, Object>> buckets = (List<Map<String, Object>>)group.get("buckets");
	            esResult.setBuckets(buckets);
            }
        }
        return esResult;
    }
	
}
