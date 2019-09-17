<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK" import="org.w3c.dom.Document,
																		org.w3c.dom.NodeList,
																		org.xml.sax.InputSource,
																		javax.xml.parsers.DocumentBuilder,
																		javax.xml.parsers.DocumentBuilderFactory,
																		java.io.*,
																		java.net.URL,
																		java.net.URLConnection" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Hashtable" %>

<%
    String post_url = "http://api.west263.com/API/";  //api地址，实际地址以西部数码发布为准
    String userid = "apidemo";                       //代理接口用户名
    String passwd = "west263apidemo";               //代理接口密码
    String encoding = "GB2312";
/**
 *cmdstrng 和 type  需要匹配API接口调用的值
 *
 **/
    String cmdstrng = "other" + "\r\n" + "get" + "\r\n" + "entityname:usemoney" + "\r\n" + "." + "\r\n"; //传入的命令
//String cmdstrng = "other"+"\r\n"+"get"+"\r\n"+"entityname:oslist"+"\r\n"+"productid:tw001"+"\r\n"+"roomid:25"+"\r\n"+"."+"\r\n";
    String type = "usemoney";  //传入获取信息类型

//定义一个hash，将所有参数与对应值保存的这个hash中
    Hashtable hash_data = new Hashtable();
    hash_data.put("post_url", post_url);
    hash_data.put("userid", userid);
    hash_data.put("passwd", passwd);
    hash_data.put("encoding", encoding);
    hash_data.put("cmdstrng", cmdstrng);
    hash_data.put("type", type);

//创建一个表单发送对象 
    FormPoster poster = new FormPoster(hash_data);

    try {
        //发送并取得响应
        String responseStr = poster.post();
        System.out.println(responseStr);

        Hashtable voldmap = string2Document(responseStr, hash_data.get("type").toString());
        String returncode = voldmap.get("returncode").toString();

        //returncode节点状态为200则显示
        if (returncode.equals("200") && type.equals("usemoney")) {
            //将usemoney节点的值取出
            out.write(hash_data.get("userid") + "帐号的可用余额为:");
            out.write("<font color=red>￥" + voldmap.get("usemoney").toString() + "</font> 元");
        } else if (returncode.equals("200") && type.equals("xinghao")) {

            for (int i = 0; i < voldmap.size(); i++) {
                out.write("操作系统：");
                out.write(voldmap.get("osval" + i).toString() + "<p>");
                out.write("服务类型：");
                out.write(voldmap.get("osname" + i).toString() + "<p>");
            }

        } else {
            out.write("状态不正确!");
        }

    } catch (Exception e) {
        //exception deal
        // out.write("发送数据失败：<br>" + e.getMessage());
    }
%>

<%!
    //使用一个inner class封装POST过程
    public class FormPoster {
        private Hashtable data = null;

        public FormPoster(Hashtable postData) {
            this.data = postData;
        }

        //提交
        public String post() throws IOException, Exception {

            addExtendInfo();

            /**
             * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
             * java.net.URL and //java.net.URLConnection
             */
            System.out.println(data.get("post_url"));
            String Surl = data.get("post_url").toString();
            URL url = new URL(Surl);
            URLConnection connection = url.openConnection();
            /**
             * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
             * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
             */
            connection.setDoOutput(true);
            /**
             * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
             */
            OutputStreamWriter out = new OutputStreamWriter(connection .6 (), data.get("encoding").toString());
            out.write("user_account=admin&user_password=******"); // post的关键所在！
            // remember to clean up
            out.flush();
            out.close();
            /**
             * 这样就可以发送一个看起来象这样的POST： POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT:
             * text/plain Content-type: application/x-www-form-urlencoded
             * Content-length: 99 username=bob password=someword
             */
            // 一旦发送成功，用以下方法就可以得到服务器的回应：
            String sCurrentLine;
            String sTotalString;
            sCurrentLine = "";
            sTotalString = "";
            InputStream l_urlStream;
            l_urlStream = connection.getInputStream();
            // 三层包装
            BufferedReader l_reader = new BufferedReader(new InputStreamReader(
                    l_urlStream));
            while ((sCurrentLine = l_reader.readLine()) != null) {
                sTotalString += sCurrentLine + "\r\n";


            }

            return sTotalString;

        }

        //将验证信息加入POST数据
        private void addExtendInfo() {
            String auth_info = md5(data.get("userid").toString() + data.get("passwd").toString() + data.get("cmdstrng").toString().substring(0, 10));
            String sURL = data.get("post_url").toString() + "index.asp" + "?userid=" + data.get("userid") + "&versig=" + auth_info + "&strCmd=" + URLEncoder.encode(data.get("cmdstrng").toString());
            if (auth_info != null)
                data.put("post_url", sURL);
        }

        //md5 function MD5后的数据是非ASIIC码，所以在发送时一定要URL编码
        public String md5(String s) {
            char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            try {
                byte[] strTemp = s.getBytes();
                //使用MD5创建MessageDigest对象
                java.security.MessageDigest mdTemp = java.security.MessageDigest.getInstance("MD5");
                mdTemp.update(strTemp);
                byte[] md = mdTemp.digest();
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte b = md[i];
                    //将没个数(int)b进行双字节加密
                    str[k++] = hexDigits[b >> 4 & 0xf];
                    str[k++] = hexDigits[b & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static Hashtable string2Document(String s, String c) {
        Document document = null;
        Hashtable voldmap = new Hashtable();
        try {
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = parser.parse(new InputSource(new StringReader(s)));
            // 获得余额
            if (c.equals("usemoney")) {
                NodeList returncode = document.getElementsByTagName("returncode");
                voldmap.put("returncode", returncode.item(0).getTextContent().toString()); //获取状态值，固定不变
                System.out.println(returncode.item(0).getTextContent());

                NodeList list = document.getElementsByTagName("usemoney");// 获取金额
                voldmap.put("usemoney", list.item(0).getTextContent().toString());

                System.out.println(list.item(0).getTextContent());
            }
            // 获得操作系统
            else if (c.equals("xinghao")) {
                NodeList returncode = document.getElementsByTagName("returncode");
                voldmap.put("returncode", returncode.item(0).getTextContent().toString()); //获取状态值，固定不变

                NodeList record = document.getElementsByTagName("record");
                NodeList osval = document.getElementsByTagName("osval");//返回操作系统
                NodeList osname = document.getElementsByTagName("osname");//返回操作服务器


                for (int i = 0; i < record.getLength(); i++) {
                    voldmap.put("osval" + i, osval.item(i).getTextContent());
                    voldmap.put("osname" + i, osname.item(i).getTextContent());
                    System.out.println(voldmap.get("osval" + i));
                    System.out.println(voldmap.get("osname" + i));
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return voldmap;
    }

%>