<template>
    <div class="panel panel-default patentdetailbox" id="searchlist" style="min-height:500px">
        <table class="table table-bordered" v-if="json">
            <tbody>
                <tr>
                    <td width="30%">商标名称： </td>
                    <td>{{json.name || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">注册号： </td>
                    <td>{{json.regNo || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">分类： </td>
                    <td>{{json.intCls || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">商标状态： </td>
                    <td>{{json.status || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">申请人： </td>
                    <td>{{json.applicantCn || " - "}} {{json.applicantEn}}</td>
                </tr>
                <tr>
                    <td width="30%">申请日期： </td>
                    <td>
                        {{json.appDate || " - "}}
                    </td>
                </tr>
                <tr>
                    <td width="30%">图片： </td>
                    <td>
                        <img :src="json.imageUrl ? json.imageUrl : '' " :onerror="errorImg" style="max-width:100px;">
                    </td>
                </tr>
                <tr>
                    <td width="30%">商品/服务列表： </td>
                    <td>
                        {{json.tmGoodsService || " - "}}
                    </td>
                </tr>
                <tr>
                    <td width="30%">商标流程： </td>
                    <td v-html="addBr(json.tmApplyFlow)">
                    </td>
                </tr>
                <tr>
                    <td width="30%">使用期限</td>
                    <td>{{json.validPeriod || " - "}} </td>
                </tr>
                <tr>
                    <td width="30%">代理机构： </td>
                    <!-- <td><a class="text-primary">{{json.tmSection || " - "}}</a></td> -->
                    <td>{{json.tmSection || " - "}}</td>
                </tr>
            </tbody>
        </table>
    </div>

</template>
<script>
import NoImg from "@/assets/images/no_image.png";

export default {
    components: {},
    data() {
        return {
            errorImg: 'this.src="' + NoImg + '"',
            json: ""
        };
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage() {
            let obj = {
                type: "brand",
                key: "regNo",
                val: this.$route.query.id
            };
            this.$axios(
                {
                    type: "get",
                    url: "companyItem/mixDetail",
                    data: obj,
                    success: res => {
                        //    console.log("数据",res)
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
        addBr(str) {
            if (!str || str == " ") return "-";
            if (str.indexOf("、") != -1) {
                return str.replace(/、/g, "<br>");
            }
            return str;
        }
    }
};
</script>
<style scoped>
/* @import "../assets/test.css"; */
</style>