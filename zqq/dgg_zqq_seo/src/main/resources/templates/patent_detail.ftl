<!DOCTYPE html>
<html lang="en">
<head>
    <meta data-n-head="true" charset="utf-8"/>
    <meta data-n-head="true" name="viewport" content="width=device-width, initial-scale=1"/>
    <meta data-n-head="true" name="keywords" data-hid="keywords"
          content="商标注册，商标免费查询，商标注册查询，商标注册流程及费用，中国商标网，专利申请，专利检索，发明专利，${val}"/>
    <meta data-n-head="true" name="description" data-hid="description"
          content="知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可。"/>
    <title data-n-head="true">知千秋 ${val} 搜索结果</title>
</head>
<body>

<#if map??>
<div>

    <span>申请号</span>
    <h1>${map.applicationNumber!}</h1>

    <span>申请日</span>
    <h1>${map.applicationDate!}</h1>

    <span>主分类号</span>
    <h1>${map.iPCList!}</h1>

    <span>分类号</span>
    <h1>${map.cites!}</h1>

    <span>地址</span>
    <h1>${map.piAddress!}</h1>

    <span>优先权</span>
    <h1>${map.priority!}</h1>

    <span>公开/公告号</span>
    <h1>${map.publicationNumber!}</h1>

    <span>公开/公告日</span>
    <h1>${map.publicationDate!}</h1>

    <span>申请人</span>
    <h1>${map.assigneestring!}</h1>

    <span>发明/设计人</span>
    <h1>${map.inventorString!}</h1>

    <span>专利代理机构</span>
    <h1>${map.agency!}</h1>

    <span>代理人</span>
    <h1>${map.agent!}</h1>

    <span>摘要</span>
    <h1>${map.abstracts!}</h1>

    <span>权利要求</span>
    <h1>${map.claInfo!}</h1>

    <span>说明书</span>
    <h1>${map.desInfo!}</h1>

    <h2>法律状态</h2>
    <div>
        <#if map.lawInfos??>
            <#list map.lawInfos as l>
                <div>
                    <span>日期</span>
                    <h1>${l.lawDate!}</h1>
                </div>
                <div>
                    <span>信息</span>
                    <h1>${l.lawStatusInfo!}</h1>
                </div>
                <div>
                    <span>状态</span>
                    <h1>${l.lawStatus!}</h1>
                </div>
            </#list>
        </#if>
    </div>
</div>
</#if>

</body>
</html>