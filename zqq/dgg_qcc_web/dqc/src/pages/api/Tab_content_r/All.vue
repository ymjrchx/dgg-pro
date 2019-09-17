<template>

    <div class="v3_page_wide data_page  allfr" id="apiList" style="min-height:682px;">

        <div class="page_ttl">
            <span>{{json.name}}（{{json.urlArr.length}}）</span>
        </div>
        <dl class="data_list" v-for="(item,idx) in json.urlArr" :key="idx">
            <MathImg :str='item.name' />
            <dd>
                <p>
                <!-- <router-link to='/Apikey' ></router-link> -->
                <a @click="goApikey(item.id)" style="font-size:18px;color: #3b97f5;">{{item.name}}</a>
                </p>

                <p class="desc">{{item.outExplain}}</p>
                <div class="d_left">
                    <p>
                        <label class="star">
                            评分： <span><em class="star0"></em></span>
                        </label>
                    </p>
                    <p>套餐类型：包月,计次</p>
                </div>
                <div class="d_right">
                    <a class="b_btn" @click="applyKey(item.id)">申请数据</a>
                </div>
            </dd>
        </dl>
        <!-- <dl class="data_list">
            <dt>
                <span></span>
                <img src="@/assets/images/api/271.png" style="padding-top:8%">
            </dt>
            <dd>
                <p>
                    <a class="d_ttl">税务登记号查询</a>
                </p>

                <p class="desc">获取公司税务登记号</p>
                <div class="d_left">
                    <p>

                        <label>连接数：780</label>
                        <label class="star">
                            评分：
                            <span>
                                <em class="star0"></em>
                            </span>
                        </label>
                    </p>
                    <p>套餐类型：包月,计次</p>
                </div>
                <div class="d_right">
                    <a class="b_btn">申请数据</a>
                </div>
            </dd>
        </dl> -->
        <!-- <dl class="data_list">
            <dt>
                <span></span>
                <img src="@/assets/images/api/213.png" style="padding-top:8%">
            </dt>
            <dd>
                <p>
                    <a class="d_ttl">企业年报信息</a>
                </p>

                <p class="desc">公司年报包含企业自己申报的基本信息，网站或网店信息，发起人及出资信息，企业资产状态和股权变更信息等</p>
                <div class="d_left">
                    <p>

                        <label>连接数：2786</label>
                        <label class="star">
                            评分：
                            <span>
                                <em class="star0"></em>

                            </span>
                        </label>
                    </p>
                    <p>套餐类型：包月,计次</p>
                </div>
                <div class="d_right">
                    <a class="b_btn">申请数据</a>
                </div>
            </dd>
        </dl>
        <dl class="data_list">
            <dt>
                <span></span>
                <img src="@/assets/images/api/214.png" style="padding-top:8%">
            </dt>
            <dd>
                <p>
                    <a class="d_ttl">企业经营异常信息</a>
                </p>

                <p class="desc">查询公司经营异常信息</p>
                <div class="d_left">
                    <p>

                        <label>连接数：2697</label>
                        <label class="star">
                            评分：
                            <span>
                                <em class="star0"></em>

                            </span>
                        </label>
                    </p>
                    <p>套餐类型：包月,计次</p>
                </div>
                <div class="d_right">
                    <a class="b_btn">申请数据</a>
                </div>
            </dd>
        </dl>
        <dl class="data_list">
            <dt>
                <span></span>
                <img src="@/assets/images/api/215.png" style="padding-top:8%">
            </dt>
            <dd>
                <p>
                    <a class="d_ttl">企业附加信息查询</a>
                </p>

                <p class="desc">提供企业发展历程、联系方式、招聘、新闻公司简介等信息查询</p>
                <div class="d_left">
                    <p>

                        <label>连接数：1857</label>
                        <label class="star">
                            评分：
                            <span>
                                <em class="star0"></em>

                            </span>
                        </label>
                    </p>
                    <p>套餐类型：包月,计次</p>
                </div>
                <div class="d_right">
                    <a class="b_btn">申请数据</a>
                </div>
            </dd>
        </dl>
        <dl class="data_list">
            <dt>
                <span></span>
                <img src="@/assets/images/api/216.png" style="padding-top:8%">
            </dt>
            <dd>
                <p>
                    <a class="d_ttl">企业三要素验证</a>
                </p>

                <p class="desc">验证公司名，法人，注册号或统一信用代码，是否匹配</p>
                <div class="d_left">
                    <p>

                        <label>连接数：4407</label>
                        <label class="star">
                            评分：
                            <span>
                                <em class="star0"></em>

                            </span>
                        </label>
                    </p>
                    <p>套餐类型：计次</p>
                </div>
                <div class="d_right">
                    <a class="b_btn">申请数据</a>
                </div>
            </dd>
        </dl>
        <dl class="data_list">
            <dt>
                <span></span>
                <img src="@/assets/images/api/231.png" style="padding-top:8%">
            </dt>
            <dd>
                <p>
                    <a href="/DataApi/Api?apiCode=231" class="d_ttl">全国商标查询</a>
                </p>

                <p class="desc">支持全国商标查询，了解最新的商标信息以及申请进度
                </p>
                <div class="d_left">
                    <p>

                        <label>连接数：1578</label>
                        <label class="star">
                            评分：
                            <span>
                                <em class="star0"></em>

                            </span>
                        </label>
                    </p>
                    <p>套餐类型：包月,计次</p>
                </div>
                <div class="d_right">
                    <a href="/DataCenter/ApplyData" class="b_btn">申请数据</a>
                </div>
            </dd>
        </dl>
        <dl class="data_list">
            <dt>
                <span></span>
                <img src="@/assets/images/api/233.png" style="padding-top:8%">
            </dt>
            <dd>
                <p>
                    <a href="/DataApi/Api?apiCode=233" class="d_ttl">著作权软著查询</a>
                </p>

                <p class="desc">支持全国企业著作权、软件著作权信息查询</p>
                <div class="d_left">
                    <p>

                        <label>连接数：1360</label>
                        <label class="star">
                            评分：
                            <span>
                                <em class="star0"></em>

                            </span>
                        </label>
                    </p>
                    <p>套餐类型：包月,计次</p>
                </div>
                <div class="d_right">
                    <a href="/DataCenter/ApplyData" class="b_btn">申请数据</a>
                </div>
            </dd>
        </dl>
        <dl class="data_list">
            <dt>
                <span></span>
                <img src="@/assets/images/api/255.png" style="padding-top:8%">
            </dt>
            <dd>
                <p>
                    <a href="/DataApi/Api?apiCode=255" class="d_ttl">企业证书查询</a>
                </p>

                <p class="desc">企业证书查询</p>
                <div class="d_left">
                    <p>

                        <label>连接数：1328</label>
                        <label class="star">
                            评分：
                            <span>
                                <em class="star0"></em>

                            </span>
                        </label>
                    </p>
                    <p>套餐类型：包月,计次</p>
                </div>
                <div class="d_right">
                    <a href="/DataCenter/ApplyData" class="b_btn">申请数据</a>
                </div>
            </dd>
        </dl>
        <dl class="data_list">
            <dt>
                <span></span>
                <img src="@/assets/images/api/260.png" style="padding-top:8%">
            </dt>
            <dd>
                <p>
                    <a href="/DataApi/Api?apiCode=260" class="d_ttl">企业信用报告</a>
                </p>

                <p class="desc">企业信用报告</p>
                <div class="d_left">
                    <p>

                        <label>连接数：1472</label>
                        <label class="star">
                            评分：
                            <span>
                                <em class="star0"></em>

                            </span>
                        </label>
                    </p>
                    <p>套餐类型：计次</p>
                </div>
                <div class="d_right">
                    <a href="/DataCenter/ApplyData" class="b_btn">申请数据</a>
                </div>
            </dd>
        </dl> -->

        <!-- <div class="paging">
            <a data-ajax="true" data-ajax-method="post" data-ajax-mode="replace" data-ajax-update="#apiList" href="/DataApi?PageIndex=0&amp;PageSize=10">首页</a>
            <a class="selected" data-ajax="true" data-ajax-method="post" data-ajax-mode="replace" data-ajax-update="#apiList" href="/DataApi?PageIndex=0&amp;PageSize=10">1</a>
            <a data-ajax="true" data-ajax-method="post" data-ajax-mode="replace" data-ajax-update="#apiList" href="/DataApi?PageIndex=1&amp;PageSize=10">2</a>
            <a data-ajax="true" data-ajax-method="post" data-ajax-mode="replace" data-ajax-update="#apiList" href="/DataApi?PageIndex=2&amp;PageSize=10">3</a>
            <a data-ajax="true" data-ajax-method="post" data-ajax-mode="replace" data-ajax-update="#apiList" href="/DataApi?PageIndex=3&amp;PageSize=10">4</a>
            <a data-ajax="true" data-ajax-method="post" data-ajax-mode="replace" data-ajax-update="#apiList" href="/DataApi?PageIndex=1&amp;PageSize=10">&gt;&gt;</a>
            <a data-ajax="true" data-ajax-method="post" data-ajax-mode="replace" data-ajax-update="#apiList" href="/DataApi?PageIndex=3&amp;PageSize=10">尾页</a>
        </div> -->

    </div>

</template>
<script>
import {IsLogin} from "@/util/common"
import {GetKey} from '@/util/Http.js'

import MathImg from '@/pages/api/mathImg'


export default {
    name: "",
    props:{
        json:""
    },
    components: {
        MathImg
    },
    data() {
        return {
            keyArr:''
        };
    },
    created() {
        // this.initData()
    },
    methods: {
       
        update(val) {
            this.string = val;
        },
        applyKey(id){
              let that = this;
              if(IsLogin(that)) return;

               this.$confirm('确认是否申请此接口？')
                .then(_ => {
                    
                     this.$axios({
                        type: "get",
                        url: "api/userUrlApply/applyApi.do",
                        data: {userId:this.$store.state.userInfo.userId,apiIds:id},
                        success: res => {
                      
                            if (res.data.code === 0) {
                                this.$message({
                                    message: '申请成功',
                                    type: 'success',
                                    duration:1500,
                                    onClose:()=>{
                                         this.$router.push("/MyData")
                                    }
                                });
                               
                            } else {
                                this.$alert(res.data.msg);
                            }
                        }
                    },1);
                })
                .catch(_ => {
                    
                });
        },
        goApikey(id){
             sessionStorage.setItem("apiId",id)
            this.$router.push("/Apikey")
        }
    }
};
</script>
<style >
</style>