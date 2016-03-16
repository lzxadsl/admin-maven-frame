package com.admin.junit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.UUID;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 * 
 * @author lzx
 * @version 1.0
 * @create_date 下午11:57:31
 */
public class MainSolr {

	//solr服务器地址
	private static String SOLR_SERVICE_URL = "http://127.0.0.1:8080/solr/userDoc";
	
	public void addUserDoc(){
		try {
			SolrClient client = new HttpSolrClient(SOLR_SERVICE_URL);
			SolrInputDocument doc = new SolrInputDocument(); 
			doc.addField("id", UUID.randomUUID().toString());
			doc.addField("userName", "lll");
			doc.addField("fileName", "用户文档2");
			doc.addField("text",readText("2.txt"));
			client.add(doc);
			client.commit();  
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void queryFromUserDoc(){
		SolrClient client = new HttpSolrClient(SOLR_SERVICE_URL);
		try {
			SolrQuery query = new SolrQuery();
			//query.add("text","北京");
			query.setQuery("userName:lsl");
			//当前页码，从1开始计算
			query.setStart(0);
			//每页几条
			query.setRows(100);
			QueryResponse resp = client.query(query);
			SolrDocumentList list = resp.getResults();
			Iterator<SolrDocument> it = list.iterator();
			while(it.hasNext()){
				SolrDocument doc = it.next();
				System.out.println(doc.get("fileName"));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void delUserDoc(String id){
		SolrClient client = new HttpSolrClient(SOLR_SERVICE_URL);
		try {
			client.deleteById(id);
			client.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String readText(String filename){
		InputStream in = null;
		StringBuffer strBuf = null;
		try {
			in = new FileInputStream(new File("C:\\docs\\"+filename));
			Reader read = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(read);
			String str = null;
			strBuf = new StringBuffer();
			while((str = buf.readLine()) != null){
				strBuf.append(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(strBuf.toString());
		return strBuf.toString();
	}
	public static void main(String[] args) {
		MainSolr solr = new MainSolr();
		//solr.readText("2.txt");
		solr.addUserDoc();
		//solr.delUserDoc("a31bd33e-40ed-4c5f-b639-d265911c1571");
		solr.queryFromUserDoc();
	}
}
