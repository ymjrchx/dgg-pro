<template>
  <div class="content">
    <div class="privateinfo-page order-page" v-if="!isEdit">
      <!-- start section1 -->
      <dl class="section1 clearfix">
        <dt>
          <div class="pic">
            <img width="100%" height="100%" class="zhanshipic" src="../../assets/images/bg-6.png" alt="个人头像" title="个人头像">
          </div>
        </dt>
        <dd>
          <div class="row-1">
            <span class="s1">Hi,
              <i class="color">{{userInfo.nickname?userInfo.nickname:userInfo.phoneNo}}</i>
            </span>
            <i class="icon icon-renzheng"></i>
          </div>
          <div class="row-2">
            <a class="safe-level" href="/center/account" target="_blank">
              <span class="s1">安全级别：</span>
              <span class="s2" v-if="safeInfo>=3">
                <i class="bgcolor" :style="'width:'+ (safeInfo/5)*100 +'%;background-color:#FF9900'"></i>
              </span>
              <span class="s2" v-if="safeInfo<3">
                <i class="bgcolor" :style="'width:'+ (safeInfo/5)*100 +'%'"></i>
              </span>
              <span class="s3">
                <i class="icon icon-di"></i>
                <span v-if="safeInfo<3">较低</span>
                <span v-if="safeInfo==3">中</span>
                <span v-if="safeInfo==4">一般</span>
                <span v-if="safeInfo==5">高</span>
              </span>
            </a>
          </div>
        </dd>
      </dl>
      <!-- end section1 -->
      <!-- start section3 -->
      <div class="section3 personal-info">
        <h2 class="order-main-title">
          基本信息
          <a class="edit" @click="isEdit = true" href="javascript:;">编辑信息</a>
        </h2>
        <table>
          <tbody>
            <tr>
              <td width="100px">昵称：</td>
              <td>{{userInfo.nickname?userInfo.nickname: userInfo.phoneNo}}</td>
            </tr>
            <tr>
              <td>性别：</td>
              <td><span v-if="userInfo.sex==1">男</span> <span v-if="userInfo.sex==2">女</span> <span v-if="userInfo.sex!=1 && userInfo.sex!=2">无</span></td>
            </tr>
            <tr>
              <td>出生年月日：</td>
              <td>{{userInfo.birthday?userInfo.birthday:'无'}}</td>
            </tr>
            <tr>
              <td>电子邮箱：</td>
              <td>{{userInfo.email?userInfo.email:'无'}}</td>
            </tr>
            <tr>
              <td colspan="2">
                <div class="btns">
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- end section3 -->
    </div>
    <div class="privateinfo-page  order-page" v-if="isEdit">
      <dl class="section1 clearfix">
        <dt>
          <div class="pic">
            <img width="100%" height="100%" class="zhanshipic" src="../../assets/images/bg-6.png" alt="个人头像" title="个人头像">
          </div>
        </dt>
        <dd>
          <div class="row-1">
            <span class="s1">Hi,
              <i class="color">{{userInfo.nickname?userInfo.nickname: userInfo.phoneNo}}</i>
            </span>
            <i class="icon icon-renzheng"></i>
          </div>
          <div class="row-2">
            <a class="safe-level" href="/center/account">
              <span class="s1">安全级别：</span>
              <span class="s2" v-if="safeInfo>=3">
                <i class="bgcolor" :style="'width:'+ (safeInfo/5)*100 +'%;background-color:#FF9900'"></i>
              </span>
              <span class="s2" v-if="safeInfo<3">
                <i class="bgcolor" :style="'width:'+ (safeInfo/5)*100 +'%'"></i>
              </span>
              <span class="s3">
                <i class="icon icon-di"></i>
                <span v-if="safeInfo<3">较低</span>
                <span v-if="safeInfo==3">中</span>
                <span v-if="safeInfo==4">一般</span>
                <span v-if="safeInfo==5">高</span>
              </span>
            </a>
          </div>
        </dd>
      </dl>
      <!-- end section1 -->
      <!-- start section3 -->
      <div class="section3 personal-info">
        <h2 class="order-main-title" data-v-6020b84f="">
          基本信息
        </h2>
        <table>
          <tbody>
            <tr>
              <td width="100px">昵称：</td>
              <td>
                <input type="text" maxlength="10" class="myinput" v-model="userInfo.nickname" name="name">
              </td>
            </tr>
            <tr>
              <td>性别：</td>
              <td>
                <label class="labelCheck">
                  <input type="radio" name="sex" v-model="userInfo.sex" value="1" >男</label>
                <label class="labelCheck">
                  <input type="radio" name="sex" v-model="userInfo.sex" value="2">女</label>
              </td>
            </tr>
            <tr>
              <td>出生年月日：</td>
              <td>
                <span class="myDate">
                    <el-date-picker
                        v-model="userInfo.birthday"
                        :picker-options="pickerOptions"
                        size="small"
                        value-format="yyyy-MM-dd"
                        type="date"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
                    </el-date-picker>
                </span>
              </td>
            </tr>
            <tr>
              <td>电子邮箱：</td>
              <td>
                <i class="email"></i>
                <a target="_blank" v-if="!userInfo.email" href="/center/flow?type=email" style="color:#f08b2f;">编辑</a>
                <a target="_blank" v-if="userInfo.email" href="/center/flow?type=email&bd=1" style="color:#f08b2f;">编辑</a>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <div class="btns">
                  <a class="mybtn mybtn-inverse mybtn-lg" @click="updateSave" href="javascript:;">保 存</a>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- end section3 -->
    </div>
  </div>
</template>
<script>
import popup from "~/components/popup"; //消息

export default {
    head() {
        return {
            title: "个人中心-知千秋官网",
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
                        "知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可。"
                }
            ]
        };
    },
    layout: "center",
    components: {
    },
    data() {
        return {
            pickerOptions: {
              disabledDate(time) {
                return time.getTime() > Date.now();
              }
            },
            userInfo: "",
            safeInfo: 0,
            isEdit: false
        };
    },
    mounted() {
        this.loadUserInfo();
        this.loadSafeInfo();
    },
    methods: {
        loadUserInfo() {
            this.$Http(
                "/persional/Info",
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                this.userInfo = res.data.data;
            });
        },
        loadSafeInfo() {
            this.$Http(
                "/persional/security",
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                let obj = res.data.data;
                for (let k in obj) {
                    if (obj[k]) {
                        this.safeInfo++;
                    }
                }
            });
        },
        updateSave() {
            if (!this.userInfo.nickname) {
                popup.created({
                    content: "必须输入用户名",
                    time: 2000
                });
                return false;
            }
            if (this.userInfo.nickname.length > 10) {
                popup.created({
                    content: "用户名不能超过10位",
                    time: 2000
                });
                return false;
            }
            if (!this.userInfo.sex) {
                popup.created({
                    content: "必须选择性别",
                    time: 2000
                });
                return false;
            }
            if (!this.userInfo.birthday) {
                popup.created({
                    content: "必须输入生日",
                    time: 2000
                });
                return false;
            }
            this.$Http(
                "/persional/update",
                {
                    userId: this.$store.state.userInfo.userId,
                    nickname: this.userInfo.nickname,
                    sex: this.userInfo.sex,
                    birthday: this.userInfo.birthday
                },
                "get",
                true
            ).then(res => {
                popup.created({
                    content: "保存成功",
                    time: 2000
                });
                this.isEdit = false;
            });
        }
    }
};
</script>
<style scoped>
/* section1 */
.vdp-datepicker {
    border: 1px solid #ccc;
    padding-left: 5px;
}
.privateinfo-page .section1 dt {
    float: left;
    width: 105px;
}

.labelCheck {
    margin-right: 15px;
}

.privateinfo-page .section1 dt .pic {
    display: block;
    width: 105px;
    height: 105px;
    overflow: hidden;
    border-radius: 50%;
    position: relative;
}

.privateinfo-page .section1 dt .pic .uploadPic-btn {
    display: none;
    cursor: pointer;
    width: 100%;
    height: 50px;
    line-height: 40px;
    background-color: #000;
    color: #fff;
    opacity: 0.6;
    filter: alpha(opacity=60);
    text-align: center;
    position: absolute;
    bottom: 0;
    left: 0;
    font-size: 12px;
}

.privateinfo-page .section1 dt .pic:hover .uploadPic-btn {
    display: block;
}

.privateinfo-page .section1 dd {
    float: left;
    margin-left: 20px;
}

.privateinfo-page .section1 dd .row-1 {
    overflow: hidden;
    margin: 12px 0px 0px;
}

.privateinfo-page .section1 dd .row-1 .s1 {
    display: block;
    float: left;
    font-size: 18px;
    color: #999;
}

.privateinfo-page .section1 dd .row-1 .icon {
    float: left;
    width: 33px;
    height: 13px;
    margin: 6px 0 0 9px;
}

.privateinfo-page .section1 dd .row-1 .icon-usergrade {
    width: 56px;
    height: 17px;
    margin-top: 5px;
}

.privateinfo-page .section1 dd .row-2 {
    font-size: 12px;
    padding: 12px 0 16px;
    overflow: hidden;
}

.privateinfo-page .section1 dd .row-2 .s1 {
    line-height: 12px;
    color: #555;
}

.privateinfo-page .section1 dd .row-2 .s2 {
    width: 100px;
    height: 12px;
    background-color: #ddd;
}

.privateinfo-page .section1 dd .row-2 .s2 .bgcolor {
    display: block;
    height: 100%;
    background-color: #ff0000;
}

.privateinfo-page .section1 dd .row-3 {
}

.privateinfo-page .section1 dd .row-3 .s1 {
    display: block;
    float: left;
    font-size: 12px;
}

.privateinfo-page .section1 dd .row-3 .s2 {
    display: block;
    float: left;
    font-size: 19px;
    color: #ff7200;
    line-height: 16px;
}

.safe-level {
    overflow: hidden;
    display: inline-block;
    vertical-align: middle;
}

.safe-level-complete {
    display: inline-block;
    margin-left: 15px;
    color: #ff7200;
    vertical-align: middle;
}

.safe-level .s1 {
    display: inline-block;
    vertical-align: middle;
}

.safe-level .s2 {
    display: inline-block;
    vertical-align: middle;
}

.safe-level .s3 {
    display: inline-block;
    line-height: 12px;
    color: #ff0000;
    margin-left: 8px;
    vertical-align: middle;
}

.safe-level .s3 .icon {
    margin-right: 3px;
    vertical-align: middle;
}

.safe-level .s3 span {
    vertical-align: middle;
}

.order-page {
    padding: 20px;
    min-height: 700px;
    background-color: #fff;
    box-shadow: 0 4px 6px 0 rgba(113, 114, 119, 0.1);
}

.order-page .no-pay-tips {
    margin-top: -10px;
    margin-bottom: 15px;
    font-size: 12px;
    color: #999;
}

.order-page .no-pay-tips .icon {
    margin-right: 2px;
    width: 12px;
    height: 12px;
}

.order-page .no-pay-tips span {
    display: inline-block;
    vertical-align: middle;
}

.order-page .price-box {
    padding-right: 20px;
    text-align: right;
}

.order-page .price-box .button {
    margin-left: 20px;
    width: 120px;
    line-height: 32px;
    text-align: center;
}

.order-main-title {
    margin-top: 15px;
    margin-bottom: 15px;
    font-size: 16px;
    line-height: 2.8em;
    font-weight: bold;
    border-bottom: 1px solid #e2e3e8;
}

.color {
    color: #ff7200;
}

/*个人信息*/

.personal-info table {
    margin-left: 10px;
    line-height: 2em;
}

.personal-info table td {
    font-size: 15px;
}

.p-base-info .showPhotos {
    display: inline-block;
    vertical-align: top;
}

.p-base-info table {
    display: inline-table;
    margin-left: 20px;
    width: 360px;
    vertical-align: top;
    font-size: 15px;
}

.p-base-info table.table-edit {
    width: 500px;
}

.p-base-info img {
    width: 100%;
}

.p-base-info table tr {
    height: 2.8em;
}

.table-edit .mybtn {
    display: block;
    background: #f08b2f;
    border-radius: 2px;
    width: 140px;
    color: #fff;
    line-height: 32px;
    text-align: center;
}

.order-main-title a.edit,
.order-title a.edit {
    margin-left: 10px;
    padding-left: 18px;
    font-size: 12px;
    color: #f08b2f;
    background: url(../../assets/images/center/icon-edit.png) no-repeat 0 center;
    background-size: 14px;
}

.myinput {
    height: 28px;
    border: solid 1px #ccc;
    border-radius: 2px;
    font-size: 14px;
    text-indent: 10px;
}

.myinput:focus {
    border-color: #f08b2f;
}

/*按钮*/
.btns .mybtn {
    display: inline-block;
    text-align: center;
    line-height: 28px;
    padding: 0 12px;
    min-width: 60px;
    cursor: pointer;
    font-size: 12px;
    margin: 0 5px;
    background-color: #fff;
    border: solid 1px #ff7200;
    color: #ff7200;
    border-radius: 2px;
}

.btns .mybtn-inverse {
    background-color: #ff7200;
    color: #fff;
}

.btns .mybtn-disable {
    cursor: default;
    background-color: #ddd;
    color: #888;
    border: solid 1px #ddd;
}

.btns .mybtn-lg {
    width: 140px;
    line-height: 32px;
    font-size: 14px;
}

.btns .mybtn-sm {
    line-height: 20px;
    padding: 0 5px;
}

.btns .mybtn .glyphicon {
    margin-right: 5px;
}

.section3 table tr {
    height: 40px;
}
</style>
