package org.mm.hf.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mm.http.HttpRequestUtil;
import org.mm.util.MD5Util;

public class Signature {
	public static final String API_KEY = "797z3it2mdh44weikz4x513irjq22pu9y292k246";
	public static final String PRIVATE_KEY = "7bae14bab42629ee01e323db934d6a060b60b634";

	public static String getSign(String url, Map<String, Object> params, long time) {
		List<String> keyList = new ArrayList<String>(params.keySet());
		Collections.sort(keyList);
		StringBuilder sb = new StringBuilder();
		for (String key : keyList) {
			String value = params.get(key).toString();
			String keyValueString = String.format("\"%s\":\"%s\",", key, value);
			sb.append(keyValueString);
		}
		String param = sb.substring(0, sb.length() - 1);
		String apiSequence = getApiSequence(url);
		String privateKey = PRIVATE_KEY;
		String finalKey = String.format("data={%s}&time=%s&apiSequence=%s&signFuncID=%s%s", param, time, apiSequence, 100, privateKey);
		return MD5Util.md5Hex(finalKey);
	}

	public static String getApiSequence(String url) {
		int index = url.indexOf("/", 8);
		return MD5Util.md5Hex(url.substring(index));
	}

	public static void main(String[] args) throws IOException {
		String url = "http://api.pinganfang.com/member/2.0/user/login";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_from", "ios");
		map.put("sMobile", "13611702121");
		map.put("sPassword", "0819b00936abd815e87bb9275b2f2c03");
		long time = System.currentTimeMillis() / 1000;
		String apiSequence = getApiSequence(url);
		String signature = getSign(url, map, time);
		map.put("time", time);
		map.put("apiSequence", apiSequence);
		map.put("apiKey", "797z3it2mdh44weikz4x513irjq22pu9y292k246");
		map.put("signature", signature);
		map.put("signFuncID", 100);
		String response=HttpRequestUtil.sendPost(url, map, null);
		System.out.println(response);

	}
}
