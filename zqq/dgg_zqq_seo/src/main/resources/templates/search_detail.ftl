<!DOCTYPE html>
<html lang="en">
<head>
    <meta data-n-head="true" charset="utf-8"/>
    <meta data-n-head="true" name="viewport" content="width=device-width, initial-scale=1"/>
    <meta data-n-head="true" name="keywords" data-hid="keywords"
          content="商标注册，商标免费查询，商标注册查询，商标注册流程及费用，中国商标网，专利申请，专利检索，发明专利，${type}，${appNo}"/>
    <meta data-n-head="true" name="description" data-hid="description"
          content="知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可。"/>
    <title data-n-head="true">知千秋 ${type} ${appNo} 搜索结果</title>
</head>
<body>

<#if map??>
    <div>

        <span>注册号</span>
        <h1>${map.regNo!}</h1>

        <span>商标图片</span>
        <h1><img src="${map.imageUrl!}"/></h1>

        <span>初审公告日期</span>
        <h1>${map.announcementDate!}</h1>

        <span>商标名称</span>
        <h1>${map.name!}</h1>

        <span>注册日期</span>
        <h1>${map.regDate!}</h1>

        <span>申请号</span>
        <h1>${map.appNo!}</h1>

        <span>申请日期</span>
        <h1>${map.appDate!}</h1>

        <span>操作</span>
        <h1>${map.rules!}</h1>

        <span>商标分类</span>
        <h1>${map.intCls!}</h1>

        <span>申请人</span>
        <h1>${map.applicantCn!}</h1>

        <span>法律状态</span>
        <h1>${map.status!}</h1>

        <span>受理流程</span>
        <h1>${map.tmApplyFlow!}</h1>

    </div>
</#if>

</body>
</html>