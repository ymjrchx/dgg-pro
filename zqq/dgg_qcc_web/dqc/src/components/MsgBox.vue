<template>
   <section class="dropdown-menu msg-box messageBox" >
        <ul class="list-group m-b-none">
            <li class="list-group-item">
                <div class="he">消息中心（
                    <span class="text-danger unreadmsgnum">{{this.$store.state.msgNum}}</span> 条未读）</div>
            </li>
            <ul style="max-height:288px;overflow-y:auto;">
                <li class="list-group-item hitem" >
                    <div class="title mb10">顶企查 </div>
                    <div class="clearfix"></div>
                    <div >亲，顶企查又有很多更新啦，快来看看吧！</div>
                    <div class="operate"> </div>
                </li>
                <li class="list-group-item hitem">
                    <div class="title mb10">新增工具 </div>
                    <div class="clearfix"></div>
                    <div >新增公司范围生成器，智能生成经营范围</div>
                    <div class="operate"> </div>
                </li>
                 <li class="list-group-item hitem">
                    <div class="title mb10">版块更新 </div>
                    <div class="clearfix"></div>
                    <div >新增企业税号查询</div>
                    <div class="operate"> </div>
                </li>
                <li class="list-group-item hitem" >
                    <div class="title mb10">版块更新 </div>
                    <div class="clearfix"></div>
                    <div >新增企业商标查询</div>
                    <div class="operate"> </div>
                </li>
                <li class="list-group-item hitem" >
                    <div class="title mb10">顶企查 </div>
                    <div class="clearfix"></div>
                    <div >新增专利查询</div>
                    <div class="operate"> </div>
                </li>
                
            </ul>
            <li class="list-group-item" style="text-align:center;color:#409EFF">
                    <router-link to='/userCenter' class="fo" style="color:#409EFF">查看全部</router-link>
            </li>
        </ul>
    </section>
</template>
<script>
export default {
    props: {
        msg: ""
    }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
