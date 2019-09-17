package net.dgg.core.utils.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;

public class DggCloseableHttpClient {
	private static SSLConnectionSocketFactory socketFactory;

	public static CloseableHttpResponse doHttpGet(String url, String cookie, String refer) throws Exception {
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build();
		URL url1 = new URL(url);
		URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
		HttpGet get = new HttpGet(uri);
		if (cookie != null) {
			get.setHeader("Cookie", cookie);
		}
		if (refer != null) {
			get.setHeader("Referer", refer);
		}
		CloseableHttpResponse response = client.execute(get);
		return response;
	}

	public static CloseableHttpResponse doHttpsGet(String url, String cookie, String refer) throws Exception {
		enableSSL();
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(config).build();
		URL url1 = new URL(url);
		URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
		HttpGet get = new HttpGet(uri);
		if (cookie != null) {
			get.setHeader("Cookie", cookie);
		}
		if (refer != null) {
			get.setHeader("Referer", refer);
		}
		CloseableHttpResponse response = client.execute(get);
		return response;
	}

	public static CloseableHttpResponse doHttpPost(String url, Map<String, String> params, String cookie, String refer)
			throws IOException {

		List<NameValuePair> values = new ArrayList<>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				values.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		return doHttpPost(url, values, cookie, refer);
	}

	public static CloseableHttpResponse doHttpsPost(String url, Map<String, String> params, String cookie, String refer)
			throws IOException {

		List<NameValuePair> values = new ArrayList<>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				values.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		return doHttpsPost(url, values, cookie, refer);
	}

	public static String doHttpUrl(String url, String params) {
		StringBuffer sbf = new StringBuffer();
		try {
			trustAllHttpsCertificates();
			URLConnection conn = new URL(url).openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			if (params != null && params.length() > 0) {
				out.write(params);
			}
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sbf.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sbf.toString();
	}

	public static CloseableHttpResponse doHttpsPostCustomSSL(String path, String password, String url,
			Map<String, String> params) {
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient client = getClientCustomSSL(path, password);
			if (client != null) {
				HttpPost post = new HttpPost(url);

				List<NameValuePair> values = new ArrayList<>();
				if (params != null && !params.isEmpty()) {
					for (Map.Entry<String, String> entry : params.entrySet()) {
						values.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
					}
				}
				if (values != null && values.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, "UTF-8");
					post.setEntity(entity);
				}

				response = client.execute(post);
				response.close();
				client.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static CloseableHttpResponse doHttpsPostCustomSSL(InputStream in, String password, String url,
			Map<String, String> params) {
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient client = getClientCustomSSL(in, password);
			if (client != null) {
				HttpPost post = new HttpPost(url);

				List<NameValuePair> values = new ArrayList<>();
				if (params != null && !params.isEmpty()) {
					for (Map.Entry<String, String> entry : params.entrySet()) {
						values.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
					}
				}
				if (values != null && values.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, "UTF-8");
					post.setEntity(entity);
				}

				response = client.execute(post);
				response.close();
				client.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static CloseableHttpResponse doHttpsGetCustomSSL(String path, String password, String url) {
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient client = getClientCustomSSL(path, password);
			if (client != null) {
				HttpGet get = new HttpGet(url);
				response = client.execute(get);
				response.close();
				client.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static CloseableHttpResponse doHttpsGetCustomSSL(InputStream in, String password, String url) {
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient client = getClientCustomSSL(in, password);
			if (client != null) {
				HttpGet get = new HttpGet(url);
				response = client.execute(get);
				response.close();
				client.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private static CloseableHttpClient getClientCustomSSL(String path, String password) {
		CloseableHttpClient client = null;
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(Files.newInputStream(Paths.get(path)), password.toCharArray());

			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy())
					.build();
			HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hv);
			client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
			client = null;
		}
		return client;
	}

	private static CloseableHttpClient getClientCustomSSL(InputStream in, String password) {
		CloseableHttpClient client = null;
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(in, password.toCharArray());

			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy())
					.build();
			HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hv);
			client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
			client = null;
		}
		return client;
	}

	private static CloseableHttpResponse doHttpPost(String url, List<NameValuePair> values, String cookie, String refer)
			throws IOException {
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config).build();
		HttpPost post = new HttpPost(url);
		if (cookie != null) {
			post.setHeader("Cookie", cookie);
		}
		if (refer != null) {
			post.setHeader("Referer", refer);
		}
		if (values != null && values.size() > 0) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, "UTF-8");
			post.setEntity(entity);
		}
		CloseableHttpResponse response = client.execute(post);
		return response;
	}

	private static CloseableHttpResponse doHttpsPost(String url, List<NameValuePair> values, String cookie,
			String refer) throws IOException {
		enableSSL();
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(config).build();
		HttpPost post = new HttpPost(url);
		if (cookie != null) {
			post.setHeader("Cookie", cookie);
		}
		if (refer != null) {
			post.setHeader("Referer", refer);
		}
		if (values != null && values.size() > 0) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, "UTF-8");
			post.setEntity(entity);
		}
		CloseableHttpResponse response = client.execute(post);
		return response;
	}

	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		trustAllCerts[0] = new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}
		};
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	private static TrustManager manager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] certificate, String s) {
		}

		public void checkServerTrusted(X509Certificate[] certificate, String s) {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	private static void enableSSL() {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { manager }, null);
			socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
