package org.bng.core.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;

public class HttpUtils {

	private static Logger log = Logger.getLogger(HttpUtils.class);

	private static PoolingHttpClientConnectionManager phccm;

	private static int maxTotal = 50;	// 连接池最大连接数

	private static int defaultMaxPerRoute = 50;		// 单节点最大连接数

	private static int connectionRequestTimeout = 500;	// 从连接池取连接超时

	private static int connectTimeout = 3000;	// 建立远程连接超时

	private static int socketTimeout = 5000;	// 等待返回数据超时

	public static HttpResponse post(String url, byte[] bytes) {
		CloseableHttpClient client = getHttpClient();
		HttpPost post = new HttpPost(url);
		setTimeout(post);

//		StringEntity se = new StringEntity(json.toString());
//		se.setContentEncoding("UTF-8");
//		se.setContentType("application/json");
//		post.setEntity(se);
		
		post.setEntity(new ByteArrayEntity(bytes));

		CloseableHttpResponse resp = null;
		try {
			resp = client.execute(post);
		} catch (ClientProtocolException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			post.releaseConnection();
		}
		return resp;
	}

	public static void setTimeout(HttpRequestBase httpRequest) {
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout).build();
		httpRequest.setConfig(config);
	}

	public static CloseableHttpClient getHttpClient() {
		if (phccm == null) {
			phccm = new PoolingHttpClientConnectionManager();
			phccm.setMaxTotal(maxTotal);
			phccm.setDefaultMaxPerRoute(defaultMaxPerRoute);
		}

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(phccm).build();
		return httpClient;
	}

}
