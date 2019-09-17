<template>
    <div>
        <template>
            <mini-head />
        </template>
        <nuxt />    
        <template>
            <main-foot />
        </template>
        <toolBox/>
        <to-top />
    </div>
</template>

<script>
import miniHead from "@/components/header/mini";
import mainFoot from "@/components/foot/mainFoot";
import toTop from "@/components/toTop";
import toolBox from "@/components/toolBox";
export default {
    components: {
        miniHead,
        mainFoot,
        toTop,
        toolBox
    },
    validate({ params }) {
        return !!params;
    },
    data() {
        return {
            isShow: false,
            typeIndex: 0,
            keywords: "",
            product: ""
        };
    },
    created() {
        if (this.$route.params.type) {
            let typeArr = {
                navigation_trademark: 0,
                navigation_patent: 1,
                navigation_copyright: 2
            };
            let params = this.$route.params.type.split(".")[0].split(",");
            this.keywords = params[2];
            this.typeIndex = typeArr[params[0]];
        }
    },
    mounted() {
        this.$Http(
            `/serviceItem/findServiceItemByNumber`,
            {
                numbers: `S7741101919677030400,S7741102108546539521,S7741102510755127297`
            },
            "get"
        ).then(res => {
            if (res.data.code == 0) {
                this.product = res.data.data;
            }
        });
    },
    methods: {
        showType() {
            this.isShow = true;
        },
        getUrl(code, id) {
            if (id) {
                return `/show/${code}&${id}.html`;
            }
            return `/show/${code}.html`;
        },
        hideType() {
            this.isShow = false;
        },
        selectType(value) {
            this.typeIndex = value;
        },
        search() {
            this.$router.push(
                `/question/${this.$store.state.docArr[this.typeIndex].code},1,${
                    this.keywords
                }.html`
            );
        }
    }
};
</script>

<style scoped>
.qianzhidaoQA-wrap {
    width: 100%;
    min-width: 1240px;
}

.qianzhidaoQA-wrap .qianzhidaoQA {
    padding: 0px 0px 75px;
    overflow: hidden;
}
/* 第一层tab商标业务、专利业务、版权业务、创业服务、其他业务 */

.qianzhidaoQA-wrap .mytab .mytap-header-1 {
    width: 100%;
    padding-top: 20px;
    background: #fff;
}

.qianzhidaoQA-wrap .mytab .mytap-header-1 .box {
    width: 1240px;
    margin: auto;
}

.qianzhidaoQA-wrap .mytab .mytap-header-1 .item {
    width: 516px;
    height: 56px;
    padding: 16px 0px 0px 85px;
    background: #ffff;
    margin: 0 auto;
    box-sizing: content-box;
}

.qianzhidaoQA-wrap .mytab .mytap-header-1 .item .list {
    margin-right: 12px;
}

.qianzhidaoQA-wrap .mytab .mytap-header-1 .item .list a {
    padding: 0 50px;
    -webkit-transition: 0.3s;
    -moz-transition: 0.3s;
    -ms-transition: 0.3s;
    -o-transition: 0.3s;
    transition: 0.3s;
    font-size: 14px;
}

.qianzhidaoQA-wrap .mytab .mytap-body {
    width: 1240px;
    margin: auto;
    overflow: hidden;
}

.qianzhidaoQA-wrap .mytab .mytap-body-1 {
    margin-top: 35px;
}

/*右侧：代理人*/

.qianzhidaoQA-wrap .c-right {
    width: 250px;
    float: right;
}

.qianzhidaoQA-wrap .c-right .item {
    width: 100%;
    box-sizing: border-box;
    border: solid 1px #f0f0f0;
    margin-top: 15px;
}

.qianzhidaoQA-wrap .c-right .item .i-header {
    padding: 0 10px;
    height: 34px;
    border-bottom: solid 1px #f0f0f0;
    color: #818181;
}

.qianzhidaoQA-wrap .c-right .item .i-header .text {
    display: block;
    float: left;
    margin: 10px 0 0;
}

.qianzhidaoQA-wrap .c-right .item .i-header .icon-refresh {
    width: 12px;
    height: 12px;
    float: right;
    margin: 10px 0 0;
}

.qianzhidaoQA-wrap .c-right .item .i-body {
    padding: 0 10px;
}

.qianzhidaoQA-wrap .c-right .item .i-body dl {
    overflow: hidden;
    padding: 12px 0;
    margin-bottom: -1px;
    border-bottom: solid 1px #ececec;
    white-space: nowrap;
}

.qianzhidaoQA-wrap .c-right .item .i-body dl dt {
    float: left;
    width: 110px;
    height: 80px;
    overflow: hidden;
}

.qianzhidaoQA-wrap .c-right .item .i-body dl dt img {
    width: 100%;
    min-height: 100%;
}

.qianzhidaoQA-wrap .c-right .item .i-body dl dd {
    float: right;
    width: 108px;
    height: 80px;
    padding: 6px 0 0;
    font-size: 12px;
    overflow: hidden;
}

.qianzhidaoQA-wrap .c-right .item .i-body dl dd a {
    color: #818181;
    font-size: 12px;
}

.qianzhidaoQA-wrap .c-right .item .i-body dl dd span {
    display: block;
    margin: 5px 0;
}

.qianzhidaoQA-wrap .c-right .item .i-body dl dd .s1 {
    color: #818181;
}

.qianzhidaoQA-wrap .c-right .item .i-body dl dd .s2 {
    color: #999;
}

.qianzhidaoQA-wrap .c-right .item .i-body dl dd .s3 {
    color: #999;
}

.qianzhidaoQA-wrap .c-right .item1 {
    margin-top: 0;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dt {
    border-radius: 50%;
    width: 80px;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd {
    width: 130px;
    padding-top: 0;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p1 {
    overflow: hidden;
    margin: 0px 0 2px;
    font-size: 0;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p1 .s1 {
    display: inline-block;
    font-size: 16px;
    color: #555;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p1 .icon {
    display: inline-block;
    width: 33px;
    height: 13px;
    margin: 0 0 0 6px;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p2 {
    color: #999;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p3 {
    font-size: 12px;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p3 span {
    float: left;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p3 .s2 {
    margin-left: 12px;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p3 .s2 .icon {
    margin-right: 4px;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p3 .icon {
    width: 12px;
    height: 12px;
    float: left;
}

.qianzhidaoQA-wrap .c-right .item1 .i-body dl dd .p3 .color {
    color: #ee8c3c;
}

.qianzhidaoQA-wrap .c-right .item2 .i-body dl:hover dd span {
    color: #ee8c3c;
}

.qianzhidaoQA-wrap .c-right .item-ad {
    padding: 5px;
}

.qianzhidaoQA-wrap .c-right .item-ad img {
    width: 100%;
}

/* ---------- start 搜索框 ---------- */

.qds-search-common {
    *zoom: 1;
    display: inline-block;
}

.qds-search-common:after {
    content: "";
    display: block;
    clear: both;
    height: 0;
    visibility: hidden;
}

.qds-search-common .search-select {
    cursor: default;
    display: block;
    float: left;
    margin-right: -1px;
    position: relative;
    color: #999;
    font-size: 16px;
}

.qds-search-common .search-select .select-title {
    display: block;
    width: 110px;
    height: 50px;
    line-height: 50px;
    border: solid 1px #f0f0f0;
    border-right: 0;
    background-color: #fff;
}

.qds-search-common .search-select .select-title .text {
    display: block;
    float: left;
    margin: 0 12px 0 26px;
}

.qds-search-common .search-select .select-title .iconfont {
    float: left;
    margin: 1px 0 0 -3px;
    width: 11px;
    height: 6px;
    font-size: 24px;
}

.qds-search-common .search-select .select-body {
    opacity: 0;
    display: none;
    overflow: hidden;
    position: absolute;
    z-index: 2;
    width: 100%;
    top: 60px;
    left: 0px;
    box-sizing: border-box;
    background-color: #fff;
    border: solid 1px #f0f0f0;
    -webkit-transition: 0.3s;
    -moz-transition: 0.3s;
    -ms-transition: 0.3s;
    -o-transition: 0.3s;
    transition: 0.3s;

    -webkit-transform: translate(0, -10px);
    -moz-transform: translate(0, -10px);
    -ms-transform: translate(0, -10px);
    -o-transform: translate(0, -10px);
    transform: translate(0, -10px);
}

.qds-search-common .search-select .select-body.atthis {
    opacity: 1;
    display: block;
}

.qds-search-common .search-select .select-body .item {
    display: block;
    width: 110px;
    padding-left: 26px;
    height: 50px;
    line-height: 50px;
    border-top: solid 1px #fff;
    margin-top: -1px;
    text-align: left;
    cursor: pointer;
}

.qds-search-common .search-text {
    position: relative;
    float: left;
    width: 725px;
    height: 50px;
    line-height: 50px;
    padding: 0 15px;
    border: solid 1px #f0f0f0;
    font-size: 16px;
    color: #999;
    background-color: #fff;
}

.qds-search-common .search-text::-webkit-input-placeholder {
    color: #bababa;
    font-size: 12px;
    font-family: "宋体", SimSun;
}

.qds-search-common .search-text::-moz-placeholder {
    color: #bababa;
    font-size: 12px;
    font-family: "宋体", SimSun;
}

.qds-search-common .search-text:-ms-input-placeholder {
    color: #bababa;
    font-size: 12px;
    font-family: "宋体", SimSun;
}

.qds-search-common .search-btn {
    float: left;
    border: none;
    width: 125px;
    height: 50px;
    background-color: #ee8b3c;
    color: #fff;
    font-size: 18px;
    cursor: pointer;
}

.qds-search-common .search-select.open .select-body {
    opacity: 1;
    visibility: visible;
    -webkit-transform: translate(0, 0);
    -moz-transform: translate(0, 0);
    -ms-transform: translate(0, 0);
    -o-transform: translate(0, 0);
    transform: translate(0, 0);
}

/* ---------- end 搜索框 ---------- */

/* start 代理人 */
.agent .agent-header {
    color: #666;
    padding-bottom: 5px;
    font-size: 16px;
}

.agent .agent-header .icon-consult {
    display: block;
    float: left;
    width: 19px;
    height: 18px;
    margin: 4px 7px 0 0;
}

.agent .agent-bodyer .list {
    padding: 11px 0;
    font-size: 13px;
}

.agent .agent-bodyer .list dt {
    float: left;
    width: 80px;
    height: 80px;
    border: solid 1px #ddd;
    border-radius: 4px;
    overflow: hidden;
}

.agent .agent-bodyer .list dt img {
    width: 100%;
}

.agent .agent-bodyer .list dd {
    float: left;
    margin-left: 18px;
    padding-top: 2px;
}

.agent .agent-bodyer .list dd .icon-renzheng {
    display: inline-block;
    padding: 0 4px;
    height: 15px;
    line-height: 14px;
    margin-left: 5px;
    font-size: 10px;
    color: #fff;
    background: #3fb34f;
}

.agent .agent-bodyer .list dd span {
    display: block;
}

.agent .agent-bodyer .list dd .r-1 {
    color: #666;
}

.agent .agent-bodyer .list dd .r-2 {
    color: #888;
    padding: 4px 0 8px;
}

.agent .agent-bodyer .list dd .r-3 {
    display: block;
    width: 78px;
    border: solid 1px #f08b2f;
    text-align: center;
    line-height: 25px;
    border-radius: 3px;
    color: #f08b2f;
}

.qianzhidaoQA-wrap .mytab .mytap-body .agent .list {
    padding: 12px 0 15px 12px;
    border-bottom: 0px;
}

/* end 代理人 */
/* ---------- start mytab ---------- */
.mytab .mytap-header {
    overflow: hidden;
    border-left: solid 1px #eee;
}
.mytab .mytap-header .list {
    float: left;
    margin-left: -1px;
}
.mytab .mytap-header .list a {
    display: block;
    padding: 0 15px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    color: #555;
    border: solid 1px #eee;
}
.mytab .mytap-header .list a:hover {
    color: #fff;
    background-color: #f08b30;
    border: solid 1px #f08b30;
}
.mytab .mytap-header .list.selected a {
    color: #fff;
    background-color: #f08b30;
    border: solid 1px #f08b30;
}
.mytab .mytap-body .content {
    display: none;
}
.mytab .mytap-body .content.selected {
    display: block;
}
/* ---------- end mytab ---------- */

.qds-search-common .search-select .select-body .item {
    display: block;
    width: 110px;
    padding-left: 26px;
    height: 50px;
    line-height: 50px;
    border-top: solid 1px #fff;
    margin-top: -1px;
    text-align: left;
}
.qds-search-common .search-text {
    position: relative;
    float: left;
    width: 725px;
    height: 50px;
    line-height: 50px;
    padding: 0 15px;
    border: solid 1px #f0f0f0;
    font-size: 16px;
    color: #999;
    background-color: #fff;
}
.qds-search-common .search-text::-webkit-input-placeholder {
    color: #bababa;
    font-size: 12px;
    font-family: "宋体", SimSun;
}
.qds-search-common .search-text::-moz-placeholder {
    color: #bababa;
    font-size: 12px;
    font-family: "宋体", SimSun;
}
.qds-search-common .search-text:-ms-input-placeholder {
    color: #bababa;
    font-size: 12px;
    font-family: "宋体", SimSun;
}
.qds-search-common .search-btn {
    float: left;
    border: none;
    width: 125px;
    height: 50px;
    background-color: #ee8b3c;
    color: #fff;
    font-size: 18px;
    cursor: pointer;
}
.qds-search-common .search-select.open .select-body {
    opacity: 1;
    visibility: visible;
    -webkit-transform: translate(0, 0);
    -moz-transform: translate(0, 0);
    -ms-transform: translate(0, 0);
    -o-transform: translate(0, 0);
    transform: translate(0, 0);
}
/* 搜索框 */
.qianzhidaoQA-wrap .n-box {
    height: 98px;
    text-align: center;
    padding: 35px 0px 0px;
    font-size: 0;
    background-color: #282b3a;
}
.qianzhidaoQA-wrap .n-box .qds-search-common {
    border: solid 4px #666;
}
.qianzhidaoQA-wrap .n-box .qds-search-common .search-select .select-title {
    border: 0;
}
.qianzhidaoQA-wrap .n-box .qds-search-common .search-text {
    border: 0;
    border-left: solid 1px #f0f0f0;
}
.qianzhidaoQA-wrap .n-box .qds-search-common .search-btn {
    width: 52px;
    height: 50px;
    background-color: #fff;
    font-size: 24px;
    color: #999;
    font-weight: bold;
    line-height: 50px;
}
.qianzhidaoQA-wrap .n-box .btn-fubu-order {
    display: inline-block;
    margin-left: 28px;
    width: 150px;
    height: 58px;
    line-height: 52px;
    text-align: center;
    color: #fff;
    font-size: 14px;
    position: relative;
    vertical-align: top;
    background-color: #f08c2f;
    border: solid 4px #f2b67f;
    overflow: hidden;
    cursor: pointer;
}
</style>