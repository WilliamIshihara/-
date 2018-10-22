package web_crawer;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class test3 {

	public static void main(String[] args) {

		String type1="body";
		String type2="div.WB_text";
		String urlPre1="https://weibo.com/aj/v6/comment/big?ajwvr=6&id=4297523474474602";
		String urlPre2="&root_comment_max_id=512252530168152&root_comment_max_id_type=0&root_comment_ext_param=&";
		String urlPage="page=";
		String urlSelect="&filter=hot";
		String url = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=4297523474474602"
				+ "&root_comment_max_id=512252530168152&root_comment_max_id_type=0&root_comment_ext_param=&page=1"
				+ "&filter=hot";//https://weibo.com/aj/v6/comment/big?ajwvr=6&id=4297523474474602&from=singleWeiBo
		
		String cookie="";//登陆微博找到cookie 方法：百度
		try {
			for(int i=0;i<10;i++)
			{
				show(urlPre1+urlPre2+urlPage+i+urlSelect,cookie,type2);
				System.out.println("--------------"+i+"-----------------");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * 用get请求获取网页的json格式数据 用gson包解析json数据，获得它的html文档 用Jsoup
	 * 
	 */
	public static void show(String url, String cookie,String type) throws Exception, Exception {
		// 设置超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
		// 创建实例
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		// 创建get请求
		HttpGet httpGet = new HttpGet(url);
		// 模拟cookie登路网站
		httpGet.setHeader("cookie", cookie);
		// 接受信息
		CloseableHttpResponse response = httpClient.execute(httpGet);
		// 获得实体
		HttpEntity entity = response.getEntity();
		// 转化为String content 为网页源码
		String content = EntityUtils.toString(entity, "utf-8");// 现在为json格式的数据
		// 分析json
		JsonObject j1 = (JsonObject) new JsonParser().parse(content);// 分析第一层json 得到data json
		JsonObject j2 = (JsonObject) new JsonParser().parse(j1.get("data").toString());// 分析第二层json
//		System.out.println(j2.get("html").getAsString());
		Document doc = Jsoup.parse(j2.get("html").getAsString());
		// 找到数据在的标签

		Elements elements = doc.select(type);
		for(Element element : elements) {
			if(!element.text().endsWith("条回复"))
			System.out.println(element.text());
		}
		

	}

}
