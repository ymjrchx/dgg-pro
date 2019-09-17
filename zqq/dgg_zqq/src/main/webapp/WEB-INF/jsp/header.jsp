<%--
  头部文件，引入必要静态资源文件
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="UTF-8">

<script>
    var baseUrl="${pageContext.request.contextPath}/";
</script>

<script type="text/javascript" charset="utf-8">
    var sysInfo={};
    sysInfo.basePath='${pageContext.request.contextPath}';
    //document.domain = '${siteDomain}';
</script>

<link rel="shortcut icon" type="image/ico" href="${baseStaticUrl}/static/images/favicon.png?v=${version}">
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/static/font-awesome/css/font-awesome.min.css?v=${version}"/>
<link rel="stylesheet" type="text/css"
      href="${baseStaticUrl}/static/plug/ztree/css/zTreeStyle/zTreeStyle.css?v=${version}"/>
<link rel="stylesheet" type="text/css"
      href="${baseStaticUrl}/static/plug/DataTables/css/jquery.dataTables.css?v=${version}"/>
<link rel="stylesheet" type="text/css" href="${baseStaticUrl}/static/plug/layui/css/layui.css?v=${version}"/>
<link rel="stylesheet" type="text/css" href="${baseStaticUrl}/static/plug/select2/css/select2.css?v=${version}"/>
<link rel="stylesheet" type="text/css"
      href="${baseStaticUrl}/static/plug/date-picker/skin/WdatePicker.css?v=${version}"/>
<link rel="stylesheet" type="text/css" href="${baseStaticUrl}/static/plug/raty/jquery.raty.css?v=${version}"/>
<link rel="stylesheet" type="text/css" href="${baseStaticUrl}/static/css/content.css?v=${version}"/>
<link rel="stylesheet" type="text/css" href="${baseStaticUrl}/static/css/layui.reset.css?v=${version}"/>
<link rel="stylesheet" type="text/css" href="${baseStaticUrl}/static/css/distribution.css?v=${version}"/>
<link rel="stylesheet" type="text/css" href="${baseStaticUrl}/static/css/node.css?v=${version}"/>