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
import org.jsoup.select.Elements;

/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class test {

	public static int number = 0;

	public static void main(String[] args) throws Exception, Exception {
		String url = "http://tz.cqut.edu.cn/bmtz.htm";//ȷ����������ҳ
		long startTime=System.currentTimeMillis();
		//ѭ��ÿһҳ��ֱ��βҳ
		while (true) {
			Elements elements = getNext(url);
			if (elements.size() != 2)
				break;
			String next = elements.get(0).attr("href");
			if (next.startsWith("bmtz"))
				url = "http://tz.cqut.edu.cn/" + next;
			else
				url = "http://tz.cqut.edu.cn/bmtz/" + next;

		}
		long endTime=System.currentTimeMillis();
		System.out.println("������:" + number);
		System.out.println("����ʱ��:"+(endTime-startTime)/1000+"s");//����ʱ�䣨������:99s  
		// �ִ�
		// JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
		// List<String> terms = jiebaSegmenter.sentenceProcess(title+essay);//���ı��ͱ���һ�����
		// System.out.println(terms);

	}

	public static Elements getNext(String url) throws Exception {
		System.out.println("page:" + url + "\n\n\n\n\n");
		//���ó�ʱʱ��
		RequestConfig deConfig=RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(deConfig).build(); // 1������ʵ��
		
		HttpGet httpGet = new HttpGet(url); // 2������ʵ��
		//�����������������
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet); // 3��ִ��
		HttpEntity entity = httpResponse.getEntity(); // 4����ȡʵ��
		String content = EntityUtils.toString(entity, "utf-8"); // 5����ȡ��ҳ����//gb2312
		httpResponse.close();
		httpClient.close();
		Document doc = Jsoup.parse(content); // ������ҳ �õ��ĵ�����
		// System.out.println(doc);

		Elements eaElements = doc.select("div.list a.title"); // �ҵ�����Ҫ����һ��
		for (int i = 0; i < eaElements.size(); i++) {

			System.out.println("����:" + eaElements.get(i).text());
			url = eaElements.get(i).attr("href");
			if (url.startsWith(".."))
				url = "http://tz.cqut.edu.cn/" + eaElements.get(i).attr("href").substring(3);
			else
				url = "http://tz.cqut.edu.cn/" + eaElements.get(i).attr("href");
			showArticle(url);// ��ȫ����
			System.out.println("_________________________________________________________________");

		}

		number += eaElements.size();
		Elements nexts = doc.select("div.list").select("a.next");
		return nexts;
	}

	public static void showArticle(String url) throws Exception, Exception {
		System.out.println("url:" + url);
		RequestConfig deConfig=RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(deConfig).build(); // 1������ʵ��
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0");
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		String content = EntityUtils.toString(entity, "utf-8");
		Document doc = Jsoup.parse(content);
		Elements eplements = doc.select("div.content").select("p");
		System.out.println("size:"+eplements.size());
		// for (Element e : eplements) {
		// System.out.println(e.text());
		// }

	}

}
