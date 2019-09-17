<template>
  <div class="manage">
    <div class="spBar3">
      <ul class="speedProgress">
        <li class="step-1 finished">
          <em class="icon-bar-circle">
            <a href="/brandmanage/"></a>
          </em>
          <dl>
            <dt class="txt">
              <a href="/brandmanage">导入商标数据</a>
            </dt>
          </dl>
        </li>
        <li class="step-2">
          <em class="icon-bar-circle">
            <a href="/brandmanage/list"></a>
          </em>
          <dl>
            <dt class="txt">
              <a href="/brandmanage/list">数据显示</a>
            </dt>
          </dl>
        </li>
      </ul>
    </div>
    <div class="brandManageWrap3">
      <ul class="shuoming">
        <h6>知千秋为您提供1种商标导入的方式：</h6>
        <li>1. 以申请人名称导入，输入公司名称，导入该公司旗下所有商标；</li>
      </ul>
      <ul class="content">
        <li class="xtzddr active" data="1">
          <a href="/brandmanage/add">
            <em class="tuijian">推荐</em>
            <em class="icon"></em>
            <span>以申请人名称导入</span>
          </a>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
    middleware: "auth",
    head() {
        return {
            title: "商标管理_专注于商标查询、专利查询-知千秋官网",
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "商标注册，商标免费查询，商标注册查询，商标注册流程及费用，中国商标网，专利申请，专利检索，发明专利"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可"
                }
            ]
        };
    },
    data() {
        return {};
    },
    components: {},
    methods: {}
};
</script>
<style scoped>
/* ------------------ start 监控数据1进度条 ------------------ */
.manage {
    min-height: 500px;
}
.zoom {
    position: fixed;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.6);
    z-index: 100;
}
.spBar3 {
    width: 1200px;
    margin: auto;
    margin-top: 60px;
    *zoom: 1;
    border-bottom: solid 1px #dbdbdb;
    padding-bottom: 50px;
}

.spBar3:after {
    content: "";
    display: block;
    clear: both;
    height: 0;
    visibility: hidden;
}

.spBar3 .speedProgress li {
    float: left;
    width: 600px;
    position: relative;
    box-sizing: border-box;
}

.spBar3 .speedProgress li .icon-bar-circle {
    display: block;
    width: 100%;
    height: 66px;
    background-image: url(../../assets/images/manage/spBar2-bg-1.png);
    background-repeat: no-repeat;
}

.spBar3 .speedProgress li .icon-bar-circle a {
    display: block;
    margin: auto;
    width: 64px;
    height: 64px;
}

.spBar3 .speedProgress li dl {
    text-align: center;
}

.spBar3 .speedProgress li dl .txt {
    font-size: 16px;
    margin-top: 10px;
}

.spBar3 .speedProgress .finished {
    color: #008cd6;
}

.spBar3 .speedProgress .step-1 .icon-bar-circle {
    background-position: -28px 0px;
}

.spBar3 .speedProgress .step-1.finished .icon-bar-circle {
    background-position: -28px -66px;
}

.spBar3 .speedProgress .step-2 .icon-bar-circle {
    background-position: -580px 0px;
}

.spBar3 .speedProgress .step-2.finished .icon-bar-circle {
    background-position: -580px -66px;
}

/* ------------------ end 监控数据1进度条------------------- */

.brandManageWrap3 {
    width: 1200px;
    margin: auto;
    margin-top: 20px;
}

.brandManageWrap3 .b-header {
    height: 40px;
    font-weight: normal;
    font-size: 16px;
}

.brandManageWrap3 .b-header .add-user {
    display: block;
    float: right;
    margin-right: 174px;
}

.brandManageWrap3 .b-header .add-user .icon {
    display: block;
    float: left;
    width: 18px;
    height: 20px;
    background: transparent url("../../assets/images/manage/icon-xtzddr.png")
        no-repeat -140px 0px;
}

.brandManageWrap3 .content {
    width: 1200px;
    margin: auto;
    margin-top: 20px;
    margin-bottom: 100px;
    overflow: hidden;
    text-align: center;
    font-size: 0;
}

.brandManageWrap3 .content li {
    display: inline-block;
    margin: 0 25px;
}

.brandManageWrap3 .content li a {
    display: block;
    width: 250px;
    height: 120px;
    background-color: #aabbbc;
    position: relative;
}

.brandManageWrap3 .content li a:hover {
    background-color: #ee8b3c;
}

.brandManageWrap3 .content li .icon {
    display: inline-block;
    width: 44px;
    height: 46px;
    margin: 18px 0 10px;
    background: transparent url("../../assets/images/manage/icon-xtzddr.png")
        no-repeat left top;
    background-repeat: no-repeat;
}

.brandManageWrap3 .content li.xtzddr a {
    background-color: #ee8b3c;
}

.brandManageWrap3 .content li.xtzddr a .tuijian {
    display: block;
    width: 45px;
    height: 19px;
    line-height: 19px;
    text-indent: 10px;
    position: absolute;
    top: 8px;
    right: 0px;
    font-size: 12px;
    color: #ae7300;
    background: transparent url("../../assets/images/manage/icon-xtzddr.png")
        no-repeat -148px -30px;
}

.brandManageWrap3 .content li.xtzddr .icon {
    background-position: 2px 6px;
}

.brandManageWrap3 .content li.tjdgsb .icon {
    background-position: -48px 0px;
}

.brandManageWrap3 .content li.pldr .icon {
    background-position: -93px 6px;
}

.brandManageWrap3 .content li span {
    display: block;
    width: 100%;
    font-size: 20px;
    color: #fff;
}

.brandManageWrap3 .btns {
    text-align: center;
    margin-bottom: 100px;
}

.brandManageWrap3 .content li.xtzddr a .tuijian {
    display: block;
    width: 45px;
    height: 19px;
    line-height: 19px;
    text-indent: 10px;
    position: absolute;
    top: 8px;
    right: 0px;
    font-size: 12px;
    color: #ae7300;
    background: transparent url("../../assets/images/manage/icon-xtzddr.png")
        no-repeat -148px -30px;
}

.brandManageWrap3 .content {
}
.brandManageWrap3 .shuoming {
    margin: 20px 0 40px;
    font-size: 14px;
    color: #999;
}
.brandManageWrap3 .shuoming h6 {
    font-size: 14px;
    margin: 0 0 5px;
    font-weight: normal;
}
.applyNumberPop {
    top: -50%;
    left: 50%;
    margin-left: -150px;
    transition: all 0.5s;
}
.applyNumberPop.atthis {
    top: 50%;
    margin-top: -125px;
}
.applyNumberPop.popUI .popUI-header {
    background-color: #fff;
    height: 40px;
}
.applyNumberPop .popUI-body {
    text-align: center;
    height: 200px;
}
.applyNumberPop .popUI-body h4 {
    font-weight: normal;
    font-size: 16px;
}
.applyNumberPop .popUI-body .input {
    padding: 20px 0;
}
.applyNumberPop .popUI-body .input input {
    width: 250px;
    height: 36px;
    padding: 0 8px;
    border: solid 1px #bfbfbf;
}
.applyNumberPop .popUI-body .btns .btn {
    border: 0;
    margin: 0 18px;
}
.applyNumberPop .popUI-body .btns .cancel {
    border: solid 1px #008cd6;
}

#muban .popUI-body a {
    display: inline-block;
    margin: 50px 46px;
    width: 167px;
    height: 74px;
    background-color: #ee8b3c;
    color: #fff;
}
</style>
