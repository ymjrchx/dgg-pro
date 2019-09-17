<template>
    <div style="min-height:500px">
        <div class="v3_grey_bg">
            <div class="page">
                <div class="v3_page_narrow allfl">
                    <ul class="category">
                        <li>  
                            <a  v-for="(item,idx) in keyArr" :key='idx' @click="switchTab(idx)" :class="{'selected': checked == idx}">
                                <img src="../../assets/images/api/cate_all.png" v-if=" item.code == 'all' ">
                                <img src="../../assets/images/api/ECI.png" v-else-if=" item.code == 'API_TYPE_IC' ">
                                <img src="../../assets/images/api/LAWSUIT.png" v-else-if=" item.code == 'API_TYPE_LAW' ">
                                <img src="../../assets/images/api/INTEPROP.png" v-else-if=" item.code == 'API_TYPE_COPYRIGHT' ">
                                <img src="../../assets/images/api/RELEVANCE.png" v-else-if=" item.code == 'API_TYPE_RELATION' ">
                                <img src="../../assets/images/api/MANAGE.png" v-else-if=" item.code == 'API_TYPE_MANAGEMENT' ">
                                <img src="../../assets/images/api/BUSRiSK.png" v-else-if=" item.code == 'API_TYPE_RISK' ">
                                <img src="../../assets/images/api/ENTLIB.png" v-else-if=" item.code == 'API_TYPE_LIBRARY' ">
                                <img src="../../assets/images/api/EXTRAINFO.png" v-else-if=" item.code == 'API_TYPE_REPORT' ">
                                <img src="../../assets/images/api/TAXINFO.png" v-else-if=" item.code == 'API_TYPE_TAXATION' ">
                                <img src="../../assets/images/api/EXTRASER.png" v-else-if=" item.code == 'API_TYPE_SERVICE' ">
                                &nbsp;&nbsp;&nbsp;&nbsp;<span>{{item.name}}</span>
                            </a>
                            <!-- <a><img src="../../assets/images/api/ECI.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>工商信息</span>
                            </a>
                            <a><img src="../../assets/images/api/LAWSUIT.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>法律诉讼</span>
                            </a>
                            <a><img src="../../assets/images/api/INTEPROP.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>知识产权</span>
                            </a>
                            <a><img src="../../assets/images/api/RELEVANCE.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>关联族谱</span>
                            </a>
                            <a><img src="../../assets/images/api/MANAGE.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>经营状况</span>
                            </a>
                            <a><img src="../../assets/images/api/BUSRiSK.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>经营风险</span>
                            </a>
                            <a><img src="../../assets/images/api/ENTLIB.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>企业库</span>
                            </a>
                            <a><img src="../../assets/images/api/EXTRASER.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>增值服务</span>
                            </a>
                            <a><img src="../../assets/images/api/EXTRAINFO.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>监控报告</span>
                            </a>
                            <a><img src="../../assets/images/api/TAXINFO.png">&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>税务信息</span>
                            </a> -->
                        </li>
                    </ul>
                </div>
               <All v-if="json" :json='json'/>
            </div>
        </div>
    </div>

</template>
<script>
import All from "./Tab_content_r/All"
export default {
    name: "saveDataContent",
    components: {
        All
    },
    data() {
        return {
            checked:0,
            keyArr:"",
            allAataArr:"",
            json:""
        };
    },
    created() {
        this.initData()
    },
    methods: {
        initData(){
            this.$axios({
                type: "get",
                url: "apiInfo/getAllApiInfo.do",
                data: {},
                success: res => {
                  
                    if (res.data.code === 0) {
                      
                        let newArr=[],obj={code:"all",name:"所有服务"}
                        for(let item of res.data.data){
                            newArr.push(...(item.urlArr))
                        }
                        obj.urlArr=newArr;
                            newArr=res.data.data;
                            newArr.unshift(obj)
                        this.json = obj  ;
                        this.keyArr=newArr;
                    } else {
                        this.$alert(res.data.msg);
                    }
                }
            },1);
        },
        switchTab(num){
            if(  this.checked == num ) return;
            this.checked = num;
            if(this.keyArr[num].urlArr && this.keyArr[num].urlArr.length > 0){
                this.json = this.keyArr[num]
            }
        }
    }
};
</script>
<style>
@import "../../assets/api.css";

.selected span{
    color: #3b97f5;
}
</style>