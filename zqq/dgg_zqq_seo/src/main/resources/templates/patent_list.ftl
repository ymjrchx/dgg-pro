<!DOCTYPE html>
<html lang="en">
<head>
    <meta data-n-head="true" charset="utf-8"/>
    <meta data-n-head="true" name="viewport" content="width=device-width, initial-scale=1"/>
    <meta data-n-head="true" name="keywords" data-hid="keywords"
          content="商标注册，商标免费查询，商标注册查询，商标注册流程及费用，中国商标网，专利申请，专利检索，发明专利，${keyWord}"/>
    <meta data-n-head="true" name="description" data-hid="description"
          content="知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可。"/>
    <title data-n-head="true">知千秋 ${keyWord} 搜索结果</title>
</head>
<body>
<!--渲染列表-->
<#if list??>
    <#list list as map>
    <div>

        <span>摘要</span>
        <h1>${map.abstracts!}</h1>

        <span>申请日期</span>
        <h1>${map.applicationDate!}</h1>

        <span>申请号</span>
        <h1>${map.applicationNumber!}</h1>

        <span>IPC备案</span>
        <h1>${map.iPCDesc!}</h1>

        <span>发明人</span>
        <h1>${map.inventorString!}</h1>

        <span>关键词</span>
        <h1>${map.keyWord!}</h1>

        <span>类名</span>
        <h1>${map.kindCodeDesc!}</h1>

        <span>法律状态</span>
        <h1>${map.legalStatusDesc!}</h1>

        <span>分类号</span>
        <h1>${map.piClassifyNum!}</h1>

        <span>说明书</span>
        <h1>${map.piInventName!}</h1>

        <span>公开公告日</span>
        <h1>${map.publicationDate!}</h1>

        <span>公开公告号</span>
        <h1>${map.publicationNumber!}</h1>
    </div>
    </#list>
</#if>
<script type="text/javascript">
    //window.location.href = 'https://www.zhiqianqiu.com/list/result_' + encodeURIComponent('${keyWord}') + '.html';
</script>
</body>
</html>