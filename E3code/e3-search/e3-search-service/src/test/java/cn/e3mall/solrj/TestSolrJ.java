package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {
	
	@Test
	public void addDocument() throws Exception {
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.130:8081/solr/collection1");
		//创建一个文档对象SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域。文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义。
		document.addField("id", "doc01");
		document.addField("item_title", "测试商品01");
		document.addField("item_price", 1000);
		//把文档写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	/*@Test
	public void deleteDocument()throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.130:8081/solr/collection1");
		solrServer.deleteById("doc01");
		solrServer.commit();
	}*/
	
	@Test
	public void queryIndex()throws Exception{
		//创建solr服务
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.130:8081/solr/collection1");
		//创建solrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.set("q", "*:*");
		//执行查询，QueryResponse对象
		QueryResponse queryResponse = solrServer.query(query);
		//取文档列表。取查询结果的总记录数。
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询结果总记录数："+solrDocumentList.getNumFound());
		//遍历文档列表，取域的内容
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}
}
