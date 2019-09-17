<template>
    <div class="noFind clear2">
        <div class="msgBox fr">
            <h1 class="col weight f20 mb20">温馨提示：</h1>
            <p class="col-h mb10">亲，未检测到您关注的{{type}}，请换个词试试~</p>
            <p class="col-h mb10">不过你可以就
                <span class="key" @click="gogo">{{keyWord}}</span> 提起{{type}}申请哦</p>
        </div>
        <img src="../assets/images/nofind.png" alt="知千秋" class="fr" title='知千秋'>
    </div>
</template>
<script>
//  <pagination :totalPage="total" @pagechange="pagechange" />    totalPage总条数  pagechange函数名返回一个参数第几页
import { openNewPage } from "~/assets/js/common.js";
export default {
    props: {
        keyWord: {
            //显示的关键字
            default: "此商标"
        },
        type: {
            //搜索哪一类
            default: "商标"
        }
    },
    data() {
        return {};
    },
    methods: {
        gogo() {
            if (this.type == "专利") {
                openNewPage(this, "/show", {
                    code: "navigation_patent_domestic_01",
                    id: "S7741445307712569344"
                });
            } else {
                openNewPage(this, "/show", {
                    code: "navigation_trademark_register_01",
                    id: "S7741101919677030400"
                });
            }
        }
    }
};
</script>
<style scoped>
.noFind {
    width: 100%;
    padding: 100px 0 70px 0;
}
.msgBox {
    padding-top: 40px;
    width: 65%;
    line-height: 1em;
    text-align: left;
    padding-left: 20px;
}
.msgBox .key {
    color: #f08b2f;
    text-decoration: underline;
    cursor: pointer;
}
.msgBox .mb20 {
    margin-bottom: 20px;
}
</style>
