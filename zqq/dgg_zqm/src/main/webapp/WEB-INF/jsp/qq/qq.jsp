<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="/fblock" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>QQ测试</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<div class="body-main">
    <div class="main-title">
				<span class="bus-state">
					<font>QQ测试</font>
				</span>
        <div class="title-tab">
            <a href="javascript:void(0);" class="active"
            ></a>
        </div>
    </div>


    <div class="table-body">
        <%--<span id="qqLoginBtn"></span>--%>
    </div>

    <div class="table-body">
        <a href="#" onclick='qqLogin()'>
            <img src="${basePath}/static/images/qq/qq_logo_3.png"></a>
    </div>

    <%--<form name="punchout_form" method="post"
          action="https://openapi.alipay.com/gateway.do?charset=UTF-8&method=alipay.trade.page.pay&sign=jdiGTTCC0aIWyFIAU1g%2FdJ%2FnHD7OdTiMCb9TTB%2BEaJVWbL3z5NAdFwATXG59Vyyp7bK%2FztPl9gU5h91k6TIfi35vy7%2FeLVVPeyG2UTEISEQevSjEeEqJMBnjANpZPs%2FPHAnnpauSsC%2F9zh6zdE%2Bh3A96ifr4BiHIIv%2BiG8VTYE0OhKyTS9iq219u0%2FCEnsi3liH%2Btod1Kzb69d8fJ%2FbDbiBxmCfZ63xWWvO2qrpav2d4GJXNiQld17xF6WqupBnGKxV6S875AMHtQuJyYw7a%2FsOJy7wWlyF2WFoAgCDHTGThKrMtSoWZlFS%2FzhHJOQvYkvXwLr6B%2B7EBKmHQ1JdfcQ%3D%3D&return_url=http%3A%2F%2Fwww.zqq.com%2Fapi%2Falipay%2Freturn&notify_url=http%3A%2F%2Fwww.zqq.com%2Fapi%2Falipay%2Fcallback&version=1.0&app_id=2018102261789215&sign_type=RSA2&timestamp=2018-10-24+15%3A37%3A08&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=JSON">
        <input type="hidden" name="biz_content"
               value="{'out_trade_no' : '121-20181024153708-000001','total_amount' : '1','subject' : '订单付款','body' : '知千秋-订单：[121]','product_code' : 'FAST_INSTANT_TRADE_PAY'}">
        <input type="submit" value="立即支付" style="display:none">
    </form>
    <script>document.forms[0].submit();</script>--%>


</div>


<c:import url="/WEB-INF/jsp/footer.jsp"/>

<script type="text/javascript" charset="utf-8"
        src="http://connect.qq.com/qc_jssdk.js"
        data-appid="${qq.appId}"
        data-redirecturi="http://www.zhiqianqiu.com/login/login.html"
        data-callback="true"
></script>

<script type="text/javascript">
    /*QC.Login({
        btnId: "qqLoginBtn",    //插入按钮的节点id
        showModal: true
    });*/

    function qqLogin() {
        QC.Login.showPopup({
            appId: "${qq.appId}",
            redirectURI: encodeURI("http://www.zhiqianqiu.com/login/login.html?type=qq")
        });
    }

    /*QC.Login.getMe(function (openId, accessToken) {
        console.log("openId", openId);
        console.log("accessToken", accessToken);
    })
*/
</script>


</body>

</html>