package com.admin.junit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import com.admin.solr.model.OrderForm;
import com.admin.solr.model.SolrPage;
import com.admin.solr.utils.SolrUtil;
import com.admin.basic.model.TypeToken;
import com.admin.basic.utils.BeanUtil;

/**
 * sol测试
 * @author lizhixian
 * @version 1.0
 * @create_date 2015-9-11 上午11:17:31
 */
public class SolrTest {

	//solr服务器地址
	private static String SOLR_SERVICE_URL = "http://127.0.0.1:8080/solr/core1";
	
	/**
	 * 获取订单数据
	 */
	public static List<OrderForm> getOrders(){
		try {
			List<OrderForm> list = new ArrayList<OrderForm>();
			SolrClient client = new HttpSolrClient(SOLR_SERVICE_URL);
			SolrQuery query = new SolrQuery();
			query.setQuery("title:移动,联通");
			query.setQuery("price:[500 TO 9999]");
			query.setSort("price",ORDER.desc);
			//当前页码，从1开始计算
			query.setStart(0);
			//每页几条
			query.setRows(100);
			//查询
			QueryResponse response = client.query(query);
			//返回结果
			SolrDocumentList docList = response.getResults();
			Iterator<SolrDocument> item = docList.listIterator();
			//遍历返回结果
			while(item.hasNext()){
				SolrDocument doc = item.next();
				OrderForm order = new OrderForm();
				System.out.println("id:" + doc.get("id"));
				System.out.println("title:" + doc.get("title"));
				System.out.println("price:" + doc.get("price"));
				System.out.println("///////////////////////////////////");
				order.setId(Integer.valueOf(doc.get("id").toString()));
				order.setTitle(doc.get("title").toString());
				order.setPrice(Double.valueOf(doc.get("price").toString()));
				Map<String, Object> map = BeanUtil.convert2Map(order, false);
				System.out.println(map);
				list.add(order);
			}
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void testSolrUtil(){
		SolrQuery query = new SolrQuery();
		query.setQuery("title:移动,联通");
		query.setQuery("price:[500 TO 9999]");
		query.setSort("price",ORDER.desc);
		SolrPage<OrderForm> page = new SolrPage<OrderForm>();
		SolrUtil.getSolrPage(page,query,OrderForm.class);
		System.out.println(page.getResults());
	}
	
	public static void queryFromText(){
		SolrQuery query = new SolrQuery();
		query.setQuery("text:深圳");
		SolrPage<Map<String, Object>> page = new SolrPage<Map<String, Object>>();
		SolrUtil.getSolrPage(page,query,new TypeToken<Map<String, Object>>().getClass());
		List<Map<String, Object>> list = page.getResults();
		for(Map<String, Object> map:list){
			System.out.println(map);
		}
	}
	
	public static void main(String[] args) {
		//getOrders();
		//testSolrUtil();
		queryFromText();

		System.out.println("继续执行...");
	}
}
