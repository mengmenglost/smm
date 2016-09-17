package org.mm.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpRequestUtil {
	private static final String defaultCharSet = "utf-8";

	public static String sendGet(String url, Map<String, Object> params, Map<String, String> headers) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(buildGetFullUrl(url, params));
			buildHeaders(headers, httpGet);
			HttpResponse response = httpClient.execute(httpGet);
			return EntityUtils.toString(response.getEntity(), defaultCharSet);
		} finally {
			httpClient.close();
		}

	}

	public static String sendPost(String url, Map<String, Object> params, Map<String, String> headers) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			buildHeaders(headers, httpPost);
			buildPostParams(params, httpPost);
			HttpResponse response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity());
		} finally {
			httpClient.close();
		}

	}

	public static String sendRpc() {
		return "";
	}

	private static String buildGetFullUrl(String url, Map<String, Object> params) {
		Set<Entry<String, Object>> paramEntries = params.entrySet();
		StringBuilder fullUrl = new StringBuilder();
		fullUrl.append(url);
		fullUrl.append("?");
		for (Entry<String, Object> param : paramEntries) {
			fullUrl.append(param.getKey() + "=" + param.getValue() + "&");
		}
		fullUrl.deleteCharAt(fullUrl.lastIndexOf("&"));
		return fullUrl.toString();
	}

	private static void buildPostParams(Map<String, Object> params, HttpPost httpPost) {
		Set<Entry<String, Object>> paramEntries = params.entrySet();
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Entry<String, Object> entry : paramEntries) {
			NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
			list.add(nameValuePair);
		}
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(list, defaultCharSet);
			httpPost.setEntity(httpEntity);
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private static void buildHeaders(Map<String, String> headers, HttpRequest httpRequest) {
		if (headers == null) {
			return;
		}
		Set<Entry<String, String>> headerEntries = headers.entrySet();
		for (Entry<String, String> entry : headerEntries) {
			if (entry.getValue() != null) {
				httpRequest.addHeader(entry.getKey(), entry.getValue());
			}
		}

	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sMobile", "18044440405");
		params.put("sPassword", "a123456");
//		params.put("house_id", 115050);
//		String response = sendGet("http://api.ananzu.st3.anhouse.com.cn/v3/housing/getDetail", params, null);
		
		String response=sendPost("http://api.ananzu.st3.anhouse.com.cn/member/2.0/user/login", params, null);
		System.out.println(response);
	}

}
