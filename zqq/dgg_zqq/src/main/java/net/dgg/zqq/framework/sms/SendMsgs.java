package net.dgg.zqq.framework.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发送短信
 * 
 * @author dgg
 * 
 */
public class SendMsgs {
	public String jinLunSendMsg(String mobile, String content) {
		String result = "";
		try{
			Map<String,Object> retParams = new Client().sendMsgBatch(mobile,"【顶呱呱集团】"+content,1);
			result =  (String) retParams.get("error");
			if(!result.equals("0")){
				sendMsg(mobile,content);
			}
		}catch (Exception e){
			sendMsg(mobile,content);
		}
        return result;
	}

	public String sendMsg(String mobile, String content) {
		try {
			content = "【顶呱呱集团】"+content;
			content = URLEncoder.encode(content, "utf-8");
		} catch (Exception e) {
		}
		String url = "http://sdk.entinfo.cn:8061/webservice.asmx/mdsmssend";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new NameValuePair("sn", "SDK-WSS-010-09391"));
		params.add(new NameValuePair("pwd", "DFD5A3C3573B850BE0991C6C1EA5B4EC"));
		params.add(new NameValuePair("mobile", mobile));
		params.add(new NameValuePair("content", content));
		params.add(new NameValuePair("ext", ""));
		params.add(new NameValuePair("stime", ""));
		params.add(new NameValuePair("rrid", ""));
		params.add(new NameValuePair("msgfmt", ""));
		String result = SendMsgs.request(url, params.toArray(new NameValuePair[params.size()]));
		return result;
	}


	public static String request(String url, NameValuePair[] params) {
		String result = null;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestBody(params);
		int statusCode = 0;
		try {
			statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			postMethod.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return result;
	}

}
