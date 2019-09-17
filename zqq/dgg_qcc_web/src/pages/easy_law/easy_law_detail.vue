<template>
    <div class="panel panel-default patentdetailbox" style="min-height:500px;padding-bottom: 40px">
        <h2 class="law_name">{{json.caseName}}</h2>
        <div class="law_de">
            <h4>基本信息</h4>
            <div class="content">
                <dl v-if="json.court" class="ng-scope">
                    <dt>审理法院：</dt>
                    <dd>{{json.court}}</dd>
                </dl>
                <dl v-if="json.caseNo" class="ng-scope">
                    <dt>案　　号：</dt>
                    <dd>
                        {{json.wbsb}}
                    </dd>
                </dl>
                <dl v-if="json.caseTypeCode" class="ng-scope">
                    <dt>裁判文书类型编号:</dt>
                    <dd class="ng-binding">{{json.caseTypeCode}}</dd>
                </dl>
                <dl v-if="json.caseType" class="ng-scope">
                    <dt>案件类型：</dt>
                    <dd class="ng-binding">{{json.caseType}}</dd>
                </dl>
                <dl v-if="json.caseReason" class="ng-scope">
                    <dt>案　　由：</dt>
                    <dd>
                        {{json.caseReason}}
                    </dd>
                </dl>
                <dl v-if="json.judgeDate" class="ng-scope">
                    <dt>裁判日期：</dt>
                    <dd class="ng-binding">{{json.judgeDate}}</dd>
                </dl>
                <dl v-if="json.judges" class="ng-scope">
                    <dt class="ng-binding">
                        合 议 庭 ：
                    </dt>
                    <template v-if="json.judges">
                        <dd v-for="(item,idx) in isArr(json.judges)" :key='idx'>{{item}}</dd>
                    </template>
                </dl>
                <dl v-if="json.trialRound" class="ng-scope">
                    <dt>审理程序：</dt>
                    <dd class="ng-binding">{{json.trialRound}}</dd>
                </dl>
                <dl v-if="json.prosecutorlist" class="ng-scope">
                    <dt>原　　告：</dt>
                    <dd class="ng-scope">
                        {{json.prosecutorlist}}
                    </dd>
                </dl>
                <dl v-if="json.defendantlist" class="ng-scope">
                    <dt>被　　告：</dt>
                    <dd class="ng-scope">
                        {{json.defendantlist}}
                    </dd>
                </dl>
            </div>
        </div>
        <div class="law_de">
            <h4 style="margin-bottom:20px;">文书正文</h4>
            <div class="content">
                <div class="section mb16" v-if="json.dsrxx">
                    <h4 style="font-size:16px;color:#000; border-left:4px solid #0084ff;padding-left:4px;marin:14px 0;">当事人信息</h4>
                    <p style="line-height:2" v-html="repStr(json.dsrxx)" class="ml10"></p>
                </div>
                <div class="section mb16" v-if="json.ssjl">
                    <h4 style="font-size:16px;color:#000; border-left:4px solid #0084ff;padding-left:4px;marin:14px 0;">申诉经过</h4>
                    <p style="line-height:2" v-html="repStr(json.ssjl)" class="ml10"></p>
                </div>
                <div class="section mb16" v-if="json.cpyz">
                    <h4 style="font-size:16px;color:#000; border-left:4px solid #0084ff;padding-left:4px;marin:14px 0;">裁判由证</h4>
                    <p style="line-height:2" v-html="repStr(json.cpyz)" class="ml10"></p>
                </div>
                <div class="section mb16" v-if="json.pjjg">
                    <h4 style="font-size:16px;color:#000; border-left:4px solid #0084ff;padding-left:4px;marin:14px 0;">判决结果</h4>
                    <p style="line-height:2" v-html="repStr(json.pjjg)" class="ml10"></p>
                </div>
                <div class="section mb16" v-if="json.wbwb">
                    <h4 style="font-size:16px;color:#000; border-left:4px solid #0084ff;padding-left:4px;marin:14px 0;">审判人员</h4>
                    <p style="line-height:2" v-html="repStr(json.wbwb)" class="ml10"></p>
                </div>
            </div>
        </div>
         <div class="law_de" v-if='json.legalBase'>
            <h4 style="margin-bottom:20px;">法律依据</h4>
            <div class="content">
                <dl v-for="(item,idx) in json.legalBase" :key="idx">
                    <dt>{{item.fgmc}}</dt>
                    <dd v-for="(item2,idx2) in item.fgItems" :key="idx2" class="ml10">
                        <span class="col"> {{item2.ftmc}}</span><br><span v-html="replaceStr(item2.ftnr)"></span>
                    </dd>
                </dl>
            </div>
        </div>
    </div>

</template>
<script>

export default {
    components: {},
    data() {
        return {
            json: ""
        };
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage() {
            let obj = {
                type: "cpwsts", //9.29日修改
                key: "id",
                val: this.$route.query.id
            };
            this.$axios(
                {
                    type: "get",
                    url: "companyItem/mixDetail",
                    data: obj,
                    success: res => {
                        if (res.data.code == 0) {
                            this.json = res.data.data[0];
                        } else {
                            this.$alert(res.data.msg);
                        }
                    }
                },
                1
            );
        },
        isArr(str) {
            return str.split("、");
        },
        replaceStr(str){
            return  str.replace(/&amp;#xA;/g,"<br>")
        },
        repStr(str){
            return  str.replace(/、/g,"<br>")
        },
    }
};
</script>
<style scoped>
/* @import "../assets/test.css"; */
.law_name {
    font-size: 20px;
    color: #565656;
    padding-top: 30px;
    padding-bottom: 20px;
    text-align: center;
    font-weight: bold;
}
.law_de {
    margin-top: 20px;
}
.law_de > h4 {
    font-size: 16px;
    font-weight: bold;
    color: #333;
    border-left: 8px solid #0084ff;
    padding-left: 20px;
}
.law_de > div {
    padding: 0 26px;
}
.law_de .ng-scope {
    margin-bottom: 0;
    line-height: 30px;
}
.law_de .ng-scope dt {
    display: inline-block;
    min-width: 100px;
    font-weight: normal;
}
.law_de .ng-scope dd {
    display: inline-block;
    margin-right: 20px;
}
    .section.mb16{
        margin: 16px 0;
    }
</style>