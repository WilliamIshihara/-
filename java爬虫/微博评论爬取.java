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
		
		String cookie="";//��½΢���ҵ�cookie �������ٶ�
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
	 * ��get�����ȡ��ҳ��json��ʽ���� ��gson������json���ݣ��������html�ĵ� ��Jsoup
	 * 
	 */
	public static void show(String url, String cookie,String type) throws Exception, Exception {
		// ���ó�ʱʱ��
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
		// ����ʵ��
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		// ����get����
		HttpGet httpGet = new HttpGet(url);
		// ģ��cookie��·��վ
		httpGet.setHeader("cookie", cookie);
		// ������Ϣ
		CloseableHttpResponse response = httpClient.execute(httpGet);
		// ���ʵ��
		HttpEntity entity = response.getEntity();
		// ת��ΪString content Ϊ��ҳԴ��
		String content = EntityUtils.toString(entity, "utf-8");// ����Ϊjson��ʽ������
		// ����json
		JsonObject j1 = (JsonObject) new JsonParser().parse(content);// ������һ��json �õ�data json
		JsonObject j2 = (JsonObject) new JsonParser().parse(j1.get("data").toString());// �����ڶ���json
//		System.out.println(j2.get("html").getAsString());
		Document doc = Jsoup.parse(j2.get("html").getAsString());
		// �ҵ������ڵı�ǩ

		Elements elements = doc.select(type);
		for(Element element : elements) {
			if(!element.text().endsWith("���ظ�"))
			System.out.println(element.text());
		}
		

	}

}
