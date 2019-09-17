package net.dgg.core.utils.httpclient;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * @description HttpClient 工具类
 * @author Ma Hong
 * @date 2018-09-10 15:02
 * @since 1.0
 */
public class DggHttpUtil {

	public static String doHttpGet(String url) {
		return doHttpGet(url, null, null, false);
	}

	public static String doHttpGet(String url, String cookie, String refer) {
		return doHttpGet(url, cookie, refer, false);
	}

	public static String doHttpsGet(String url) {
		return doHttpGet(url, null, null, true);
	}

	public static String doHttpsGet(String url, String cookie, String refer) {
		return doHttpGet(url, cookie, refer, true);
	}

	public static String doHttpPost(String url, Map<String, String> params) {
		return doHttpPost(url, params, null, null, false);
	}

	public static String doHttpPost(String url, Map<String, String> params, String cookie, String refer) {
		return doHttpPost(url, params, cookie, refer, false);
	}

	public static String doHttpsPost(String url, Map<String, String> params) {
		return doHttpPost(url, params, null, null, true);
	}

	public static String doHttpsPost(String url) {
		return doHttpPost(url, null, null, null, true);
	}

	public static String doHttpsPost(String url, Map<String, String> params, String cookie, String refer) {
		return doHttpPost(url, params, cookie, refer, true);
	}

	/**
	 * @param url    例如：http(s)://localhost:8080/api/query
	 * @param params 例如： p1=1&p2=2&p3=3
	 * @return 字符串
	 */
	public static String doHttpUrl(String url, String params) {
		return DggCloseableHttpClient.doHttpUrl(url, params);
	}

	public static String doHttpUrl(String url) {
		return DggCloseableHttpClient.doHttpUrl(url, null);
	}

	/**
	 * 需要客户端带证书请求GET
	 * 
	 * @param path     证书路径
	 * @param password 证书密码
	 * @param url      请求的 【https://】的地址
	 * @return JSON格式的字符串
	 */
	public static String doHttpsGetCustomSSL(String path, String password, String url) {
		Objects.requireNonNull(path, "path");
		Objects.requireNonNull(password, "password");
		Objects.requireNonNull(url, "url");

		String text = null;
		try {
			CloseableHttpResponse response = DggCloseableHttpClient.doHttpsGetCustomSSL(path, password, url);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				text = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			response.close();
		} catch (Exception e) {
			text = null;
		}
		return text;
	}

	/**
	 * 需要客户端带证书请求GET
	 * 
	 * @param in       证书
	 * @param password 证书密码
	 * @param url      请求的 【https://】的地址
	 * @return JSON格式的字符串
	 */
	public static String doHttpsGetCustomSSL(InputStream in, String password, String url) {
		Objects.requireNonNull(in, "in");
		Objects.requireNonNull(password, "password");
		Objects.requireNonNull(url, "url");

		String text = null;
		try {
			CloseableHttpResponse response = DggCloseableHttpClient.doHttpsGetCustomSSL(in, password, url);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				text = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			response.close();
		} catch (Exception e) {
			text = null;
		}
		return text;
	}

	/**
	 * 需要客户端带证书请求POST
	 * 
	 * @param path     证书路径
	 * @param password 证书密码
	 * @param url      请求的 【https://】的地址
	 * @param params   【K,V】形式
	 * @return JSON格式的字符串
	 */
	public static String doHttpsPostCustomSSL(String path, String password, String url, Map<String, String> params) {
		Objects.requireNonNull(path, "path");
		Objects.requireNonNull(password, "password");
		Objects.requireNonNull(url, "url");

		String text = null;
		try {
			CloseableHttpResponse response = DggCloseableHttpClient.doHttpsPostCustomSSL(path, password, url, params);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				text = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			response.close();
		} catch (Exception e) {
			text = null;
		}
		return text;
	}

	/**
	 * 需要客户端带证书请求POST
	 * 
	 * @param in       证书
	 * @param password 证书密码
	 * @param url      请求的 【https://】的地址
	 * @param params   【K,V】形式
	 * @return JSON格式的字符串
	 */
	public static String doHttpsPostCustomSSL(InputStream in, String password, String url, Map<String, String> params) {
		Objects.requireNonNull(in, "in");
		Objects.requireNonNull(password, "password");
		Objects.requireNonNull(url, "url");

		String text = null;
		try {
			CloseableHttpResponse response = DggCloseableHttpClient.doHttpsPostCustomSSL(in, password, url, params);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				text = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			response.close();
		} catch (Exception e) {
			text = null;
		}
		return text;
	}

	/**
	 * 需要客户端带证书请求POST
	 * 
	 * @param path     证书路径
	 * @param password 证书密码
	 * @param url      请求的 【https://】的地址
	 * @return JSON格式的字符串
	 */
	public static String doHttpsPostCustomSSL(String path, String password, String url) {
		return doHttpsPostCustomSSL(path, password, url, null);
	}

	public static String doHttpsPostCustomSSL(InputStream in, String password, String url) {
		return doHttpsPostCustomSSL(in, password, url, null);
	}

	private static String doHttpGet(String url, String cookie, String refer, boolean isHttps) {
		String text = null;
		try {
			CloseableHttpResponse response = isHttps ? DggCloseableHttpClient.doHttpsGet(url, cookie, refer)
					: DggCloseableHttpClient.doHttpGet(url, cookie, refer);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				text = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	private static String doHttpPost(String url, Map<String, String> params, String cookie, String refer,
			boolean isHttps) {
		String text = null;
		try {
			CloseableHttpResponse response = isHttps ? DggCloseableHttpClient.doHttpsPost(url, params, cookie, refer)
					: DggCloseableHttpClient.doHttpPost(url, params, cookie, refer);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				text = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

}
