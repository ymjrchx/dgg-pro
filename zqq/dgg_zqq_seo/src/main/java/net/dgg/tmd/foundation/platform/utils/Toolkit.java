package net.dgg.tmd.foundation.platform.utils;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.*;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/10/11.
 */
public class Toolkit {
    @Slf4j
    public static class FormatedDate {
        /**
         * 字段
         */
        private Calendar relCalendar;
        private int year;
        private Instant time;
        private int month;
        private int week;
        private int dayofweek;
        private int dayofmonth;
        private int hour;
        private int minute;
        private int second;
        private int hourofday;
        private int state;

        @SneakyThrows
        public static final FormatedDate parseDate(String dates, String format) {
            try {
                Date date = new SimpleDateFormat(format).parse(dates);
                return new FormatedDate(date);
            } catch (ParseException e) {
                throw e;
            }
        }

        public static final FormatedDate parseDefaultTime(String defaultTimeFormatTime) throws ParseException {
            try {
                SimpleDateFormat defaultTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = defaultTimeFormat.parse(defaultTimeFormatTime);
                return new FormatedDate(date);
            } catch (ParseException e) {
                throw e;
            }
        }

        public static final FormatedDate parseDateDefault(String defaultFormatDate) throws ParseException {
            try {
                SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = defaultDateFormat.parse(defaultFormatDate);
                return new FormatedDate(date);
            } catch (ParseException e) {
                throw e;
            }
        }

        public FormatedDate() {
            this(System.currentTimeMillis());
        }

        public FormatedDate(Date rel) {
            this.init(rel);
        }

        public FormatedDate(Instant date) {
            Date d = new Date();
            d.setTime(date.toEpochMilli());
            init(d);
        }

        public FormatedDate(Long timeMillis) {
            Date dt = new Date();
            dt.setTime(timeMillis);
            init(dt);
        }

        private void init(Date rel) {
            relCalendar = Calendar.getInstance();
            relCalendar.setTime(rel);
            this.time = Instant.ofEpochMilli(rel.getTime());
            year = relCalendar.get(Calendar.YEAR);
            month = (relCalendar.get(Calendar.MONTH) + 1);
            week = relCalendar.get(Calendar.WEEK_OF_MONTH);
            dayofweek = (relCalendar.get(Calendar.DAY_OF_WEEK) - 1);
            dayofmonth = relCalendar.get(Calendar.DAY_OF_MONTH);
            hour = relCalendar.get(Calendar.HOUR);
            hourofday = relCalendar.get(Calendar.HOUR_OF_DAY);
            minute = relCalendar.get(Calendar.MINUTE);
            second = relCalendar.get(Calendar.SECOND);
            state = relCalendar.get(Calendar.AM_PM);
        }

        public FormatedDate addDate(int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(relCalendar.getTimeInMillis());
            calendar.add(Calendar.DATE, day);
            return new FormatedDate(calendar.getTimeInMillis());
        }

        public FormatedDate addMillis(int millis) {
            return new FormatedDate(relCalendar.getTimeInMillis() + millis);
        }

        public FormatedDate addMinute(int minute) {
            return new FormatedDate(relCalendar.getTimeInMillis() + minute * 60000);
        }

        public void show() {
            log.debug("{}", this);
        }

        public Date getDate() {
            Date date = new Date();
            date.setTime(this.getTime().toEpochMilli());
            return date;
        }

        public Long getTimeMillis() {
            return this.relCalendar.getTimeInMillis();
        }

        public Calendar getCalendar() {
            return this.relCalendar;
        }

        public FormatedDate getDayFirst() {
            try {
                SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = getDate();
                String dateStr = defaultDateFormat.format(date);
                Date newDate = defaultDateFormat.parse(dateStr);
                return new FormatedDate(newDate.getTime());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public String getFormat(String formate) {
            Date date = new Date();
            date.setTime(this.relCalendar.getTimeInMillis());
            try {
                return new SimpleDateFormat(formate).format(date);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public Instant getTime() {
            return time;
        }

        public int getMonth() {
            return month;
        }

        public int getYear() {
            return year;
        }

        public int getWeek() {
            return week;
        }

        public int getDayofweek() {
            return dayofweek;
        }

        public int getDayofmonth() {
            return dayofmonth;
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }

        public int getSecond() {
            return second;
        }

        public int getState() {
            return state;
        }

        public int getHourofday() {
            return hourofday;
        }

        public String toString() {
            return getFormat("yyyy-MM-dd HH:mm:ss");
        }
    }

    @Slf4j
    public static class StringHelper {
        public static boolean isNullOREmpty(String s) {
            return s == null || s.equals("");
        }

        public static boolean notNullAndEmpty(String s) {
            return !isNullOREmpty(s);
        }

        private StringHelper() {
            throw new IllegalStateException("工具类不实例化");
        }

        public static List<String> sortAsc(List<String> src) {
            if (src == null) {
                throw new IllegalStateException("不能为空");
            } else {
                return src.stream().sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());
            }
        }

        public static boolean isAllEnglishChar(String text) {
            List<Character> characters = toCharArray(text);
            for (char c : characters) {
                if (c > 128) {
                    return false;
                }
            }
            return true;
        }

        public static boolean containEnglishChar(String text) {
            List<Character> characters = toCharArray(text);
            for (char c : characters) {
                if (c < 128) {
                    return true;
                }
            }
            return false;
        }


        public static String makeRepeat(String c, int num) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < num; i++) {
                s.append(c);
            }
            return s.toString();
        }

        public static List<Character> toCharArray(String text) {
            List<Character> chars = new ArrayList<>();
            char[] cs = text.toCharArray();
            for (char c : cs) {
                chars.add(c);
            }
            return chars;
        }

        public static int getFrequency(Character c, List<Character> characters) {
            int count = 0;
            for (Character charVal : characters) {
                if (charVal.charValue() == c.charValue()) {
                    count++;
                }
            }
            return count;
        }

        public static String uuid() {
            return UUID.randomUUID().toString().replaceAll("\\-", "");
        }

        public static String frontCompWithZero(int source, int len) {
            return frontCompWithZore(source, len);
        }

        public static String frontCompWithZore(int source, int len) {
       /*
        * 0 指前面补充零
        * formatLength 字符总长度为 formatLength
        * d 代表为正数。
        */
            return String.format("%0".concat(String.valueOf(len)).concat("d"), source);
        }

        public static List<String> sortDesc(List<String> src) {
            if (src == null) {
                throw new IllegalStateException("不能为空");
            } else {
                return src.stream().sorted((o1, o2) -> o2.compareTo(o1)).collect(Collectors.toList());
            }
        }

        public static String[] conact(String[] s1, String... s2) {
            int length = s1.length + s2.length;
            String[] s = new String[length];
            for (int i = 0; i < s1.length; i++) {
                s[i] = s1[i];
            }
            for (int i = 0; i < s2.length; i++) {
                s[s1.length + i] = s2[i];
            }
            return s;
        }


        public static String md5(String md5) {
            try {
                MessageDigest md = MessageDigest
                        .getInstance("MD5");
                byte[] array = md.digest(md5.getBytes());
                String s = "";
                for (int i = 0; i < array.length; ++i) {
                    s = s.concat(Integer.toHexString((array[i] & 0xFF) | 0x100)
                            .substring(1, 3));
                }
                return s;
            } catch (java.security.NoSuchAlgorithmException e) {
                log.info("{}", e);
            }
            return null;
        }

        public static String joinUse(String[] s, String c) {
            if (s == null) return null;
            if (s.length == 0) return "";
            StringBuilder sl = new StringBuilder();
            sl.append(s[0]);
            for (int i = 1; i < s.length; i++) {
                sl.append(c);
                sl.append(s[i]);
            }
            return sl.toString();
        }

        /**
         * 从完整图片路径截取尾部
         *
         * @param prefix
         * @param fullIamgePath
         * @return
         */
        public static String getTailFromFullImagePath(String prefix, String fullIamgePath) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(fullIamgePath)) {
                String trimedPath = fullIamgePath.trim();
                if (trimedPath.contains(prefix)) {
                    return trimedPath.substring(prefix.length());
                }
                return trimedPath;
            }
            return fullIamgePath;
        }

        /**
         * 根据当前数据库最大id生成编号
         *
         * @param maxId
         * @return
         */
        public static String contractCode(Long maxId, char... charArr) {
            StringBuilder stringBuilder = new StringBuilder();
            maxId++;
            String id = maxId.toString();
            int bound = charArr.length - id.length();
            for (int i = 0; i <= bound; i++) {
                stringBuilder.append(charArr[i]);
            }
            stringBuilder.append(id);
            return stringBuilder.toString();
        }

        /**
         * 切除文件名后缀
         *
         * @param name
         * @return
         */
        public static String cutPostfix(String name) {
            if (org.apache.commons.lang3.StringUtils.isBlank(name)) {
                throw new IllegalArgumentException("传入字符不能为空！");
            } else {
                if (name.lastIndexOf('.') >= 0) {
                    return name.substring(0, name.lastIndexOf('.'));
                } else {
                    return name;
                }
            }
        }
    }

    @Slf4j
    public static class Https {

        private String store;
        private String pass;

        public Https(String store, String pass) {
            this.store = store;
            this.pass = pass;
        }

        public Https() {

        }

        public String get(String url, Map<String, String> params, String charset) throws Exception {
            return new String(doConnect(url, "get", params != null ? params : new HashMap<>(), charset), charset);
        }

        public byte[] getBinary(String url, Map<String, String> params, String charset) throws Exception {
            return doConnect(url, "get", params != null ? params : new HashMap<>(), charset);
        }

        public String post(String url, Map<String, String> params, String charset) throws Exception {
            return new String(doConnect(url, "post", params != null ? params : new HashMap<>(), charset), charset);
        }

        public String postData(String url, String data, String charset) throws Exception {
            return new String(doConnect(url, "post", data.getBytes(), charset), charset);
        }

        private byte[] doConnect(String url, String method, Map<String, String> params, String charset) throws Exception {
            if (url.startsWith("https://")) {
                TrustManager[] tm;
                try {
                    tm = new TrustManager[]{this.store == null && this.pass == null ? new TrustAnyManager() : new RequestX509TrustManager(store, pass)};
                    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                    sslContext.init(null, tm, new java.security.SecureRandom());
                    SSLSocketFactory ssf = sslContext.getSocketFactory();
                    HttpsURLConnection httpsConn = null;
                    String posts;
                    URL myURL;
                    switch (method.trim().toLowerCase()) {
                        case "post":
                            posts = "";
                            if (params.size() > 0) {
                                int i = 0;
                                for (Map.Entry<String, String> key : params.entrySet()) {
                                    posts = posts.concat((i++ == 0 ? "" : "&") + key.getKey() + "=" + URLEncoder.encode(params.get(key.getKey()), charset));
                                }
                            }
                            byte[] data = posts.getBytes(charset);
                            myURL = new URL(url);
                            httpsConn = (HttpsURLConnection) myURL.openConnection();
                            httpsConn.setSSLSocketFactory(ssf);
                            httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
                            httpsConn.setRequestMethod("POST");
                            httpsConn.setRequestProperty("accept", "*/*");
                            httpsConn.setRequestProperty("connection", "Keep-Alive");
                            httpsConn.setRequestProperty("user-agent",
                                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                            httpsConn.setRequestProperty("Content-Length", String.valueOf(data.length));
                            httpsConn.setDoInput(true);
                            httpsConn.setDoOutput(true);
                            httpsConn.connect();
                            OutputStream os = httpsConn.getOutputStream();
                            os.write(data);
                            os.flush();
                            break;
                        case "get":
                            if (url.indexOf('?') != -1) {
                                for (Map.Entry key : params.entrySet()) {
                                    url = url.concat("&" + key.getKey() + "=" + URLEncoder.encode(params.get(key.getKey()), charset));
                                }
                            } else {
                                if (params.size() > 0) {
                                    url = url.concat("?");
                                    for (Map.Entry<String, String> key : params.entrySet()) {
                                        url = url.concat("&" + key.getKey() + "=" + URLEncoder.encode(params.get(key.getKey()), charset));
                                    }
                                }
                            }
                            myURL = new URL(url);
                            httpsConn = (HttpsURLConnection) myURL.openConnection();
                            httpsConn.setSSLSocketFactory(ssf);
                            httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
                            httpsConn.setRequestMethod("GET");
                            httpsConn.setRequestProperty("accept", "*/*");
                            httpsConn.setRequestProperty("connection", "Keep-Alive");
                            httpsConn.setRequestProperty("user-agent",
                                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                            httpsConn.connect();
                            break;
                        default:
                            throw new IllegalStateException("错误，不允许的方法");
                    }
                    InputStream is = httpsConn.getInputStream();
                    int readc = 0;
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    byte[] buff = new byte[122];
                    while ((readc = is.read(buff)) != -1) {
                        bo.write(buff, 0, readc);
                        bo.flush();
                    }
                    try {
                        is.close();
                    } catch (Exception e) {
                        log.debug("e", e);
                    }
                    return bo.toByteArray();
                } catch (Exception e) {
                    throw e;
                }
            } else {
                try {
                    HttpURLConnection httpConn = null;
                    String posts;
                    URL myURL;
                    switch (method.trim().toLowerCase()) {
                        case "post":
                            posts = "";
                            if (params.size() > 0) {
                                int i = 0;
                                for (Map.Entry<String, String> key : params.entrySet()) {
                                    posts = posts.concat((i++ == 0 ? "" : "&") + key.getKey() + "=" + URLEncoder.encode(params.get(key.getKey()), charset));
                                }
                            }
                            byte[] data = posts.getBytes(charset);
                            myURL = new URL(url);
                            httpConn = (HttpsURLConnection) myURL.openConnection();
                            httpConn.setRequestMethod("POST");
                            httpConn.setRequestProperty("accept", "*/*");
                            httpConn.setRequestProperty("connection", "Keep-Alive");
                            httpConn.setRequestProperty("user-agent",
                                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                            httpConn.setRequestProperty("Content-Length", String.valueOf(data.length));
                            httpConn.setDoInput(true);
                            httpConn.setDoOutput(true);
                            httpConn.connect();
                            OutputStream os = httpConn.getOutputStream();
                            os.write(data);
                            os.flush();
                            break;
                        case "get":
                            if (url.indexOf('?') != -1) {
                                for (Map.Entry key : params.entrySet()) {
                                    url = url.concat("&" + key.getKey() + "=" + URLEncoder.encode(params.get(key.getKey()), charset));
                                }
                            } else {
                                if (params.size() > 0) {
                                    url = url.concat("?");
                                    for (Map.Entry<String, String> key : params.entrySet()) {
                                        url = url.concat("&" + key.getKey() + "=" + URLEncoder.encode(params.get(key.getKey()), charset));
                                    }
                                }
                            }
                            myURL = new URL(url);
                            httpConn = (HttpURLConnection) myURL.openConnection();
                            httpConn.setRequestMethod("GET");
                            httpConn.setRequestProperty("accept", "*/*");
                            httpConn.setRequestProperty("connection", "Keep-Alive");
                            httpConn.setRequestProperty("user-agent",
                                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                            httpConn.connect();
                            break;
                        default:
                            throw new IllegalStateException("错误，不允许的方法");
                    }
                    InputStream is = httpConn.getInputStream();
                    int readc = 0;
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    byte[] buff = new byte[122];
                    while ((readc = is.read(buff)) != -1) {
                        bo.write(buff, 0, readc);
                        bo.flush();
                    }
                    try {
                        is.close();
                    } catch (Exception e) {
                        log.debug("e", e);
                    }
                    return bo.toByteArray();
                } catch (Exception e) {
                    throw e;
                }
            }
        }

        private byte[] doConnect(String url, String method, byte[] data, String charset) throws Exception {
            TrustManager[] tm;
            try {
                tm = new TrustManager[]{this.store == null && this.pass == null ? new TrustAnyManager() : new RequestX509TrustManager(store, pass)};
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new java.security.SecureRandom());
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                HttpsURLConnection httpsConn = null;
                URL myURL = null;

                switch (method.trim().toLowerCase()) {
                    case "post":
                        myURL = new URL(url);
                        httpsConn = (HttpsURLConnection) myURL.openConnection();
                        httpsConn.setSSLSocketFactory(ssf);
                        httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
                        httpsConn.setRequestMethod("POST");
                        httpsConn.setRequestProperty("accept", "*/*");
                        httpsConn.setRequestProperty("connection", "Keep-Alive");
                        httpsConn.setRequestProperty("user-agent",
                                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                        httpsConn.setRequestProperty("Content-Length", String.valueOf(data.length));
                        httpsConn.setDoInput(true);
                        httpsConn.setDoOutput(true);
                        httpsConn.connect();
                        OutputStream os = httpsConn.getOutputStream();
                        os.write(data);
                        os.flush();
                        break;
                    case "get":
                        myURL = new URL(url);
                        httpsConn = (HttpsURLConnection) myURL.openConnection();
                        httpsConn.setSSLSocketFactory(ssf);
                        httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
                        httpsConn.setRequestMethod("GET");
                        httpsConn.setRequestProperty("accept", "*/*");
                        httpsConn.setRequestProperty("connection", "Keep-Alive");
                        httpsConn.setRequestProperty("user-agent",
                                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                        httpsConn.connect();
                        break;
                    default:
                        throw new IllegalStateException("错误，不允许的方法");
                }
                InputStream is = httpsConn.getInputStream();
                int readCnt = 0;
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                byte[] buff = new byte[122];
                while ((readCnt = is.read(buff)) != -1) {
                    bo.write(buff, 0, readCnt);
                    bo.flush();
                }
                try {
                    is.close();
                } catch (Exception e) {
                    log.debug("{}", e);
                }
                return bo.toByteArray();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public static class TrustAnyManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    @Slf4j
    public static class RequestX509TrustManager implements X509TrustManager {

        X509TrustManager sunJSSEX509TrustManager;

        public RequestX509TrustManager(String keystoreFile, String pass) throws Exception {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(keystoreFile), pass.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
            tmf.init(ks);
            TrustManager[] tms = tmf.getTrustManagers();
            for (int i = 0; i < tms.length; i++) {
                if (tms[i] instanceof X509TrustManager) {
                    sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                    return;
                }
            }
            throw new Exception("Couldn't initialize");
        }

        public RequestX509TrustManager(InputStream storeInputStream, String pass) throws Exception {
            // create a "default" JSSE X509TrustManager.
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(storeInputStream, pass.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
            tmf.init(ks);
            TrustManager[] tms = tmf.getTrustManagers();
            for (int i = 0; i < tms.length; i++) {
                if (tms[i] instanceof X509TrustManager) {
                    sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                    return;
                }
            }
            throw new Exception("Couldn't initialize");
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
            } catch (CertificateException e) {
                log.debug("{}", e);
            }
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException e) {
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return sunJSSEX509TrustManager.getAcceptedIssuers();
        }
    }

    public static class DomHelper {

        private DomHelper() {
            throw new IllegalStateException("DOM实例化不需要");
        }

        public static String doc2str(Document doc) {
            try {
                StringWriter sw = new StringWriter();
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.transform(new DOMSource(doc), new StreamResult(sw));
                return sw.toString();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        public static Document str2doc(String xml) {
            try {
                return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

    }

    public static class MD5 {
        private MD5() {
            throw new IllegalStateException("工具类不需要实例化");
        }

        public static final String getMessageDigest(String buffer) {
            return getMessageDigest(buffer.getBytes());
        }

        public static final String getMessageDigest(byte[] buffer) {
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            try {
                MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                mdTemp.update(buffer);
                byte[] md = mdTemp.digest();
                int j = md.length;
                char[] str = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                return null;
            }
        }

    }

    public static class IpHelper {

        private IpHelper() {
            throw new IllegalStateException("工具类不需要实例化");
        }

        public static String getIp() {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        String host = inetAddress.getHostAddress();
                        if (!host.startsWith("169.") && !host.startsWith("127.") && !host.startsWith("0:") && !host.contains(":")) {
                            return host;
                        }
                    }
                }
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            throw new IllegalStateException();
        }

    }

    public static class AESHelper {

        public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

        private AESHelper() {
            throw new IllegalThreadStateException("不需要实例化");
        }

        /**
         * 解密
         *
         * @param data 待解密内容
         * @return
         */
        public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
            Key k = toKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, k);
            return cipher.doFinal(data);
        }

        private static SecretKey toKey(byte[] key) throws Exception {
            return new SecretKeySpec(key, "AES");
        }

    }

    public static class DateUtils {

        @SneakyThrows
        public static Long fromDate(String date) {
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
            } else if (date.matches("\\d{8}")) {
                return new SimpleDateFormat("yyyyMMdd").parse(date).getTime();
            } else if (date.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}")) {
                return new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(date).getTime();
            } else if (date.matches("\\d{14}")) {
                return new SimpleDateFormat("yyyyMMddHHmmss").parse(date).getTime();
            } else {
                throw new IllegalStateException("不支持的时间格式");
            }
        }

        public static Long addMonth(long time, int month) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.add(Calendar.MONTH, month);
            return calendar.getTimeInMillis();
        }

        public static String format(Long time, String pattern) {
            Date date = new Date();
            date.setTime(time);
            return new SimpleDateFormat(pattern).format(date);
        }

    }

    public static class ObjHelper {

        @SneakyThrows
        public static String obj2str(Object obj) {
            @Cleanup ByteArrayOutputStream baos = new ByteArrayOutputStream();
            @Cleanup ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }

        @SneakyThrows
        public static <T> T str2obj(String str) {
            @Cleanup ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(str));
            @Cleanup ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        }

    }

    public static class MapBuilder {

        public static MapBuilder getInstance() {
            return new MapBuilder();
        }

        private Map<String, Object> map = new HashMap<>();

        public MapBuilder put(String key, Object value) {
            map.put(key, value);
            return this;
        }

        public MapBuilder putAll(Map<? extends String, ?> m) {
            map.putAll(m);
            return this;
        }

        public MapBuilder remove(String key) {
            map.remove(key);
            return this;
        }

        public Map<String, Object> build() {
            return map;
        }

    }

    public static class NumberUtil {

        public static Integer getFirstNumber(String text) {
            Pattern numericPattern = Pattern.compile("\\d+");
            Matcher matcher = numericPattern.matcher(text);
            if (matcher.find()) {
                return Integer.valueOf(matcher.group());
            } else {
                return null;
            }
        }

    }

}
