<template>
    <div class="panel panel-default patentdetailbox" id="searchlist" style="min-height:500px">
        <table class="table table-bordered" v-if="json">
            <tbody>
                <tr>
                    <td width="30%">名称： </td>
                    <td>{{json.piInventName || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">申请号： </td>
                    <td>{{json.applicationNumber || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">申请日： </td>
                    <td>{{json.applicationDate || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">申请公布号： </td>
                    <td>{{json.piApplyPublishNum || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">申请公布日： </td>
                    <td>{{json.piApplyAnnounceDate || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">发明人： </td>
                    <td>
                        {{json.inventorString || " - "}}
                    </td>
                </tr>
                <tr>
                    <td width="30%">类型： </td>
                    <td>{{json.kindCodeDesc || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">专利代理机构： </td>
                    <td>
                        {{json.agency || " - "}}
                    </td>
                </tr>
                <tr>
                    <td width="30%">法律状态： </td>
                    <td>
                        {{json.legalStatusDesc || " - "}}
                    </td>
                </tr>
                <tr>
                    <td width="30%">法律历史状态</td>
                    <td>
                        {{json.legalStatusDate || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">摘要： </td>
                    <td>{{json.abstracts || " - "}}</td>
                </tr>
                <tr>
                    <td width="30%">摘要附图： </td>
                    <td> <img :src="json.patentImage ? json.patentImage : '' " :onerror="errorImg" style="max-width:200px;"></td>
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
                type: "patent",
                key: "applicationNumber",
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
        }
    }
};
</script>
<style scoped>
/* @import "../assets/test.css"; */
</style>