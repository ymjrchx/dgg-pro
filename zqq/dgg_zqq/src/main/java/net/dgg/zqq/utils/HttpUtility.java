/******************************************************************************
 * Copyright (C) 2014 ShenZhen YiHua Computer Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳怡化电脑股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package net.dgg.zqq.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//TODO from_xuzhe 建议使用resttemplate实现远程http接口调用，一来性能要高一些，二来方便接入springcloud
public class HttpUtility {
    private static final String KEY = "?encryptKey=ENCRYPT_KEY";
    private static final int TIMEOUT = 60000;

    /**
     * GET方式
     *
     * @param url    :请求服务器的路径
     * @param params ：请求服务器参数
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String getRest(String url, Map<String, Object> params
    ) throws ClientProtocolException, IOException {
        String resultStr = "";
        // 将Map类型的参数转成字符串类型
        StringBuffer stringBuffer = new StringBuffer();
        String paramStr = null;
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            Object value = entry.getValue();
            value = null == value ? "" : value;
            String key = (String) entry.getKey();
            stringBuffer.append("&" + key + "=" + value);
        }
        paramStr = stringBuffer.toString();
        // 创建一个http客户端
        HttpClient client = new DefaultHttpClient();
        // 创建一个GET请求

        HttpGet httpGet = new HttpGet(url + KEY + paramStr);
        // 设置连接超时时间(单位毫秒)
        HttpConnectionParams.setConnectionTimeout(httpGet.getParams(), TIMEOUT);
        // 设置读数据超时时间(单位毫秒)
        HttpConnectionParams.setSoTimeout(httpGet.getParams(), TIMEOUT);
        // 向服务器发送请求并获取服务器返回的结果
        HttpResponse response = client.execute(httpGet);
        // 返回码
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回的结果可能放到InputStream中。
            InputStream inputStream = response.getEntity().getContent();
            String line = "";
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "UTF-8"));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultStr = buffer.toString();
            if (reader != null) {
                reader.close();
            }
        } else {
            System.out.println("请求服务器返回码："
                    + response.getStatusLine().getStatusCode());
        }
        return resultStr;
    }

    /**
     * POST方式
     *
     * @param url    :请求服务器的路径
     * @param params ：请求服务器参数
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String postRest(String url, Map<String, Object> params) throws ClientProtocolException, IOException {
        String resultStr = "";
        HttpClient client = new DefaultHttpClient();

        // 创建一个POST请求
        HttpPost httpPost = new HttpPost(url + KEY);
        // 设置连接超时时间(单位毫秒)
        HttpConnectionParams.setConnectionTimeout(httpPost.getParams(), TIMEOUT);
        // 设置读数据超时时间(单位毫秒)
        HttpConnectionParams.setSoTimeout(httpPost.getParams(), TIMEOUT);

        // 向HttpPost中加入参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            value = null == value ? "" : value;
            nvps.add(new BasicNameValuePair(key, value == null ? "" : value.toString()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        // 向服务器发送POST请求并获取服务器返回的结果
        HttpResponse response = client.execute(httpPost);
        // 返回的结果可能放到InputStream中。
        if (response.getStatusLine().getStatusCode() == 200) {
            InputStream inputStream = response.getEntity().getContent();
            String line = "";
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "UTF-8"));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultStr = buffer.toString();
            if (reader != null) {
                reader.close();
            }
//            System.out.println("POST方式结果：" + resultStr);
        } else {
            System.out.println("请求服务器返回码："
                    + response.getStatusLine().getStatusCode());
        }

        return resultStr;
    }

    /**
     * Post Json 方式
     *
     * @param url    ：请求服务器的路径
     * @param params ：参数列表
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String postJson(String url, Map<String, Object> params) throws ClientProtocolException, IOException {
        String resultStr = "";
        // 创建一个http客户端
        HttpClient client = new DefaultHttpClient();
        // 创建一个PUT请求ENCRYPT_KEY
        HttpPost httpPost = new HttpPost(url + KEY);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");
        // 设置连接超时时间(单位毫秒)
        HttpConnectionParams.setConnectionTimeout(httpPost.getParams(), TIMEOUT);
        // 设置读数据超时时间(单位毫秒)
        HttpConnectionParams.setSoTimeout(httpPost.getParams(), TIMEOUT);
        // 组装数据放到HttpEntity中发送到服务器
        String requestParam = toJsonWithGson(params);
        StringEntity entity = new StringEntity(requestParam, "UTF-8");
        Header header = new BasicHeader("Content-Type", "application/json;charset=UTF-8");
        entity.setContentType(header);
        httpPost.setEntity(entity);
        // 向服务器发送PUT请求并获取服务器返回的结果
        HttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回的结果可能放到InputStream中。
            InputStream inputStream = response.getEntity().getContent();
            String line = "";
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultStr = buffer.toString();
            if (reader != null) {
                reader.close();
            }
//            System.out.println("POST方式结果：" + resultStr);
        } else {
            System.out.println("请求服务器返回码：" + response.getStatusLine().getStatusCode());
        }
        return resultStr;
    }

    /**
     * DELETE方式
     *
     * @param url :请求服务器的路径
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String deleteRest(String url, Map<String, Object> paramsMap)
            throws IllegalStateException, IOException {
        String resultStr = "";
        // 将Map类型的参数转成字符串类型
        StringBuffer stringBuffer = new StringBuffer();
        String paramStr = null;
        Iterator iter = paramsMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            value = null == value ? "" : value;
            stringBuffer.append("&" + key + "=" + value);
        }
        paramStr = stringBuffer.toString();
        // System.out.println(paramStr);
        // 创建一个http客户端
        HttpClient client = new DefaultHttpClient();
        // 创建一个DELETE请求
        HttpDelete httpDelete = new HttpDelete(url + KEY + paramStr);
        // 设置连接超时时间(单位毫秒)
        HttpConnectionParams
                .setConnectionTimeout(httpDelete.getParams(), TIMEOUT);
        // 设置读数据超时时间(单位毫秒)
        HttpConnectionParams.setSoTimeout(httpDelete.getParams(), TIMEOUT);
        // 向服务器发送DELETE请求并获取服务器返回的结果
        HttpResponse response = client.execute(httpDelete);
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回的结果可能放到InputStream中。
            InputStream inputStream = response.getEntity().getContent();
            String line = "";
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "UTF-8"));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultStr = buffer.toString();
            // System.out.println("DELETE方式结果："+resultStr);
            if (reader != null) {
                reader.close();
            }
        } else {
            // System.out.println("请求服务器返回码："+response.getStatusLine().getStatusCode());
        }
        return resultStr;
    }

    /***
     * 文件传输
     *
     * @param url
     * @param file
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String uploadFile(String url, File file)
            throws ClientProtocolException, IOException {
        String resultStr = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url + KEY);
        // 设置连接超时时间(单位毫秒)
        HttpConnectionParams.setConnectionTimeout(httpPost.getParams(), TIMEOUT);
        // 设置读数据超时时间(单位毫秒)
        HttpConnectionParams.setSoTimeout(httpPost.getParams(), TIMEOUT);
        FileBody bin = new FileBody(file);
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("file", bin);
        httpPost.setEntity(reqEntity);
        HttpResponse response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        String line = "";
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream, "UTF-8"));
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        resultStr = buffer.toString();
        if (reader != null) {
            reader.close();
        }
        // System.out.println("文件传输结果："+resultStr);
        return resultStr;
    }

    /**
     * 将键值对Map转换成NameValuePair列表
     *
     * @param paramsMap
     * @return
     */
    static List<NameValuePair> keyValueToValuePairList(
            Map<String, String> paramsMap) {
        final List<NameValuePair> dataList = new ArrayList<NameValuePair>();
        for (Entry<String, String> entry : paramsMap.entrySet()) {
            dataList.add(new BasicNameValuePair(entry.getKey(), entry
                    .getValue()));
        }
        return dataList;
    }

    /**
     * 由Gson格式将object格式转换成json格式字符串
     *
     * @param object
     * @return
     */
    public static String toJsonWithGson(Object object) {
        String jsonStr = JsonUtils.obj2Json(object);
        return jsonStr;
    }


}
