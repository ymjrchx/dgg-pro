<template>
  <div class="smartRegister-page smartRegister3-page">
    <div class="progressBar clearfix">
      <ul>
        <li class="step step-2 finish">
          <em class="icon-bar-circle"></em>
          <dl class="explain">
            <dt class="text">填写商标信息</dt>
          </dl>
        </li>
        <li class="line finish"></li>
        <li class="step step-3 finish">
          <em class="icon-bar-circle"></em>
          <dl class="explain">
            <dt class="text">填写申请人信息</dt>
          </dl>
        </li>
        <li class="line finish"></li>
        <li class="step step-4 finish">
          <em class="icon-bar-circle"></em>
          <dl class="explain">
            <dt class="text">申请信息确认</dt>
          </dl>
        </li>
        <li class="line"></li>
        <li class="step step-5">
          <em class="icon-bar-circle"></em>
          <dl class="explain">
            <dt class="text">支付交官费用</dt>
          </dl>
        </li>
      </ul>
    </div>
    <div class="agent" data-v-127632e4="">
      <h6 class="agent-header" data-v-127632e4="">
        <i class="iconfont icon-consult" data-v-127632e4=""></i>商标顾问与帮助</h6>
      <ul class="agent-bodyer">
        <li class="list">
          <dl class="clearfix">
            <dt><img src="../../assets/images/show/p4.jpg" alt="代理人"></dt>
            <dd>
              <span class="r-1">刘朝阳
                <i class="icon-renzheng">已认证</i>
              </span>
              <span class="r-2">好评率：97.26%</span>
              <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">咨询一下</a>
            </dd>
          </dl>
        </li>
        <li class="list">
          <dl class="clearfix">
            <dt><img src="../../assets/images/show/p2.jpg" alt="代理人"></dt>
            <dd>
              <span class="r-1">李亚南
                <i class="icon-renzheng">已认证</i>
              </span>
              <span class="r-2">好评率：100%</span>
              <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">咨询一下</a>
            </dd>
          </dl>
        </li>
        <li class="list">
          <dl class="clearfix">
            <dt><img src="../../assets/images/show/p3.jpg" alt="代理人"></dt>
            <dd>
              <span class="r-1">高峰
                <i class="icon-renzheng">已认证</i>
              </span>
              <span class="r-2">好评率：100%</span>
              <!--<a class="r-3" href="/userim/im?agent=5a7234566f6a7855736d6973687334487366677079773d3d" target="_blank">咨询一下</a>-->
              <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">咨询一下</a>
            </dd>
          </dl>
        </li>
      </ul>
    </div>
    <div class="orderinfo-wrap" v-if="nameType">
      <h2 class="order-title">商标信息
        <a href="/order/order" class="edit">编辑</a>
      </h2>
      <div class="order-content">
        <ul>
          <li>
            <label>商标类型：</label>
            <span v-if="nameType == 1">文字</span>
            <span v-if="nameType == 2">图形</span>
            <span v-if="nameType == 3">图形及文字</span>
          </li>
          <li v-if="name">
            <label>商标名称：</label>
            {{name}} </li>
          <li v-if="createdRes">
            <label>图标样式：</label>
            <ul class="showPhotos clearfix">
              <li class="list-photo show">
                <div class="top">
                  <div class="top-container">
                    <div class="photo-box">
                      <img class="photo fbrandPicpic" :src="createdRes.fsHost+createdRes.fsPath">
                    </div>
                  </div>
                </div>
                <div class="bottom">图标样式</div>
              </li>
            </ul>
          </li>
          <li v-if="uploadRes">
            <label>图标样式：</label>
            <ul class="showPhotos clearfix">
              <li class="list-photo show">
                <div class="top">
                  <div class="top-container">
                    <div class="photo-box">
                      <img class="photo fbrandPicpic" :src="uploadRes.fsHost+uploadRes.fsPath">
                    </div>
                  </div>
                </div>
                <div class="bottom">图标样式</div>
              </li>
            </ul>
          </li>
          <li>
            <label>商标类别：</label>
            <div class="item-bodyer" v-if="selectList.length">
              <dl v-for="item in selectList" v-if="item" :key="item.id">
                <dt>第{{item.number}}类 {{item.name}}</dt>
                <dd class="clearfix" v-if="item.sonArr">
                  <span v-for="childitem in item.sonArr" :key="childitem.id" class="choosecg">{{childitem.number}}{{childitem.name}}</span>
                </dd>
              </dl>
            </div>
            <div class="item-bodyer" v-if="selectAutoList.length">
              <dl v-for="item in selectAutoList" v-if="item" :key="item.id">
                <dt>第{{item.number}}类 {{item.name}}</dt>
                <dd class="clearfix" v-if="item.sonArr">
                  <span v-for="childitem in item.sonArr" :key="childitem.id" class="choosecg">{{childitem.number}}{{childitem.name}}</span>
                </dd>
              </dl>
            </div>
          </li>
        </ul>
      </div>
      <h2 class="order-title">申请人及联系人信息
        <a href="/order/ownermsg" class="edit">编辑</a>
      </h2>
      <div class="order-content order-applicant" v-if="company.postalcode">
        <ul>
          <li>
            <label>申请人资质：</label>
            公司 </li>
          <li>
            <label>邮政编码：</label>
            {{company.postalcode}} </li>
          <li>
            <label>联系人：</label>
            {{company.contactName}} </li>
          <li>
            <label>电子邮箱：</label>
            {{company.contactEmail}} </li>
        </ul>
        <ul>
          <li>
            <label>公司名称：</label>
            {{company.applicantName}} </li>
          <li>
            <label>公司地址：</label>
            {{company.businessLicenceAddress}} </li>
          <li>
            <label>联系电话：</label>
            {{company.contactPhone}} </li>
          <li>
            <label>座机电话：</label>
            {{company.contactTelephone}} </li>
        </ul>
      </div>
      <div class="order-content order-applicant" v-if="person.postalcode">
        <ul>
          <li>
            <label>申请人资质：</label>
            个人 </li>
          <li>
            <label>邮政编码：</label>
            {{person.postalcode}} </li>
          <li>
            <label>联系人：</label>
            {{person.contactName}} </li>
          <li>
            <label>电子邮箱：</label>
            {{person.contactEmail}} </li>
        </ul>
        <ul>
          <li>
            <label>个人姓名：</label>
            {{person.applicantName}} </li>
          <li>
            <label>营业地址：</label>
            {{person.businessLicenceAddress}} </li>
          <li>
            <label>联系电话：</label>
            {{person.contactPhone}} </li>
          <li>
            <label>座机电话：</label>
            {{person.contactTelephone}} </li>
        </ul>
      </div>
      <!-- start 委托材料 -->
      <h2 class="order-title">
        材料信息
        <span class="file-info-tips">
          <!-- （可在支付完成后，在订单中进行上传） -->
        </span>
      </h2>
      <div class="order-content order-files order-applicant">
        <ul class="showPhotos clearfix">
          <li class="list-photo has-photo show" v-if="concatType == 0">
            <input type="hidden" name="flicense" value="">
            <div class="top">
              <div class="top-container">
                <div class="photo-box">
                  <img class="photo flicensepic" v-if="!person.copy" src="../../assets/images/order/add-img-bg.png">
                  <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="person.copy" :src="person.copy.fsHost+person.copy.fsPath ">
                  <div class="bg">
                    <span class="icon">
                      <i class="iconfont icon-jiahao"></i>
                    </span>
                    <span class="text">（需盖公章）</span>
                  </div>
                </div>
                <dl class="shadow-box">
                  <i class="shadow"></i>
                  <dd class="mybtns">
                    <span class="box">
                      <a class="btn flicenselink" v-if="person.copy" :href="person.copy.fsHost+person.copy.fsPath" target="_blank">查看大图</a>
                      <a class="btn btnupload" @click="upload('copy')" type="flicense" href="javascript:;">重新上传</a>
                    </span>
                  </dd>
                </dl>
                <div class="moxie-shim moxie-shim-html5"><input type="file" ref="copy" accept="image/jpeg,.jpg,application/pdf,.pdf"></div>
              </div>
            </div>
            <div class="bottom">身份证复印件</div>
          </li>

          <li class="list-photo has-photo show" v-if="concatType == 0">
            <input type="hidden" name="flicense" value="">
            <div class="top">
              <div class="top-container">
                <div class="photo-box">
                  <img class="photo flicensepic" v-if="!person.personBusiness" src="../../assets/images/order/add-img-bg.png">
                  <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="person.personBusiness" :src="person.personBusiness.fsHost+person.personBusiness.fsPath ">
                  <div class="bg">
                    <span class="icon">
                      <i class="iconfont icon-jiahao"></i>
                    </span>
                    <span class="text">（需盖公章）</span>
                  </div>
                </div>
                <dl class="shadow-box">
                  <i class="shadow"></i>
                  <dd class="mybtns">
                    <span class="box">
                      <a class="btn flicenselink" v-if="person.personBusiness" :href="person.personBusiness.fsHost+person.personBusiness.fsPath" target="_blank">查看大图</a>
                      <a class="btn btnupload" @click="upload('personBusiness')" type="flicense" href="javascript:;">重新上传</a>
                    </span>
                  </dd>
                </dl>
                <div class="moxie-shim moxie-shim-html5"><input type="file" ref="personBusiness" accept="image/jpeg,.jpg,application/pdf,.pdf"></div>
              </div>
            </div>
            <div class="bottom">个体户营业执照</div>
          </li>

          <li class="list-photo has-photo show" v-if="concatType == 1">
            <input type="hidden" name="flicense" value="">
            <div class="top">
              <div class="top-container">
                <div class="photo-box">
                  <img class="photo flicensepic" v-if="!company.business" src="../../assets/images/order/add-img-bg.png">
                  <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="company.business" :src="company.business.fsHost+company.business.fsPath ">
                  <div class="bg">
                    <span class="icon">
                      <i class="iconfont icon-jiahao"></i>
                    </span>
                    <span class="text">（需盖公章）</span>
                  </div>
                </div>
                <dl class="shadow-box">
                  <i class="shadow"></i>
                  <dd class="mybtns">
                    <span class="box">
                      <a class="btn flicenselink" v-if="company.business" :href="company.business.fsHost+company.business.fsPath" target="_blank">查看大图</a>
                      <a class="btn btnupload" @click="upload('business')" type="flicense" href="javascript:;">重新上传</a>
                    </span>
                  </dd>
                </dl>
                <div class="moxie-shim moxie-shim-html5"><input type="file" ref="business" accept="image/jpeg,.jpg,application/pdf,.pdf"></div>
              </div>
            </div>
            <div class="bottom">企业营业执照</div>
          </li>
          <li class="list-photo has-photo show">
            <input type="hidden" name="fproxy" value="">
            <div class="top">
              <div class="top-container">
                <div class="photo-box ">
                  <img class="photo fproxypic" v-if="!uploadFile.attorney" src="../../assets/images/order/add-img-bg.png">
                  <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="uploadFile.attorney" :src="uploadFile.attorney.fsHost+uploadFile.attorney.fsPath ">
                  <div class="bg">
                    <span class="icon">
                      <i class="iconfont icon-jiahao"></i>
                    </span>
                    <span class="text">（需盖公章）</span>
                  </div>
                </div>
                <dl class="shadow-box">
                  <i class="shadow"></i>
                  <dd class="mybtns">
                    <span class="box">
                      <a class="btn fproxylink" v-if="uploadFile.attorney" :href="uploadFile.attorney.fsHost+uploadFile.attorney.fsPath " target="_blank">查看大图</a>
                      <a class="btn btnupload" @click="upload('attorney')" type="fproxy" href="javascript:;">重新上传</a>
                    </span>
                  </dd>
                </dl>
                <div class="moxie-shim moxie-shim-html5"><input type="file" ref="attorney" accept="image/jpeg,.jpg,application/pdf,.pdf"></div>
              </div>
            </div>
            <div class="bottom"> 委托书 <br>
              <span class="shili" v-if="templateUrl">
                <a class="btn createpower " style="color: #f08b2f;" :href="templateUrl[3].fileUrl">下载模板</a>
              </span>
            </div>
          </li>
          <li class="list-photo has-photo show">
            <input type="hidden" name="fpriority" value="">
            <div class="top">
              <div class="top-container">
                <div class="photo-box">
                  <img class="photo fprioritypic" v-if="!uploadFile.priority" src="../../assets/images/order/add-img-bg.png">
                  <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="uploadFile.priority" :src="uploadFile.priority.fsHost+uploadFile.priority.fsPath ">
                  <div class="bg">
                    <span class="icon">
                      <i class="iconfont icon-jiahao"></i>
                    </span>
                  </div>
                </div>
                <dl class="shadow-box">
                  <i class="shadow"></i>
                  <dd class="mybtns">
                    <span class="box">
                      <a class="btn fprioritylink" v-if="uploadFile.priority" :href="uploadFile.priority.fsHost+uploadFile.priority.fsPath" target="_blank">查看大图</a>
                      <a class="btn btnupload" @click="upload('priority')" type="fpriority" href="javascript:;">重新上传</a>
                    </span>
                  </dd>
                </dl>
                <div class="moxie-shim moxie-shim-html5"><input type="file" ref="priority" accept="image/jpeg,.jpg,application/pdf,.pdf"></div>
              </div>
            </div>
            <div class="bottom">优先权证明
              <span class="shili" v-if="0">
                <a href="/about/13.html" target="_blank">?</a>
              </span>
            </div>
          </li>
        </ul>
      </div>
      <!-- end 委托材料 -->
      <h2 class="order-title">订单备注</h2>
      <div class="order-content">
        <textarea name="message" maxlength="500" v-model="remark" placeholder="如有特殊需要，请填写订单备注"></textarea>
      </div>
      <h2 class="order-title">订单金额</h2>
      <div class="last-pay personal-last-pay">
        <ul class="list-box">
          <li class="row-officer">官费总额：￥
            <i>{{officeTotal}}</i>
          </li>
          <li class="row-sense">应付总额：
            <em>￥
              <i>{{officeTotal}}</i>
            </em>
          </li>
        </ul>
        <div class="btns">
          <div class="file-agree-box">
            <i v-if="!isAgree" @click="isAgree=true" class="iconfont checkbox2" data-id="">&#xe65d;</i>
            <i v-if="isAgree" @click="isAgree=false" class="iconfont checkbox2 checked" data-id="">&#xe612;</i>
            <span>
              我已阅读
              <a href="/about/8.html" target="_blank" id="show-contract">《商标服务合同》</a>且同意该合同内容
            </span>
          </div>
          <a class="btn-step btn-next topay" :class="{'disabled': !isAgree}" @click="submit" href="javascript:;">提交订单</a>
        </div>
      </div>
    </div>
    <to-top />
  </div>
</template>
<script>
import toTop from "@/components/toTop";
import popup from "~/components/popup"; //消息
import axios from "axios";
import Cookies from "js-cookie";
export default {
    middleware: "auth",
    head() {
        return {
            title: "智能订单-知千秋 ",
            meta: [
                {
                    name: "keywords ",
                    hid: "keywords ",
                    content:
                        "注册商标查询，中国商标官网查询，商标搜索，商标检索,知千秋,权大师 "
                },
                {
                    name: "description ",
                    hid: "description ",
                    content:
                        "免费精准的商标查询平台,中国更全的商标信息库,精准智能的商标相似查询结果,为商标申请人降低商标驳回风险,更快,更准的定位商标注册成功概率。知千秋 "
                }
            ]
        };
    },
    data() {
        return {
            isAgree: true,
            name: "",
            uploadRes: "",
            createdRes: "",
            nameType: "",
            brandType: "",
            autoType: "",
            doMain: "",
            templateUrl: "",
            doMainIndex: "",
            doMainchildIndex: "",
            selectAutoList: "",
            selectList: "",
            trademarkList: "",
            type: 0,
            concatType: "",
            person: "",
            company: "",
            uploadFile: "",
            remark: "",
            product: Cookies.getJSON("product")
        };
    },
    mounted() {
        if (sessionStorage.getItem("concatInfo")) {
            let obj = JSON.parse(sessionStorage.getItem("concatInfo"));
            this.concatType = obj.type;
            this.person = JSON.parse(obj.person);
            this.company = JSON.parse(obj.company);
            this.uploadFile = JSON.parse(obj.uploadFile);
        }
        if (sessionStorage.getItem("brandInfo")) {
            let obj = JSON.parse(sessionStorage.getItem("brandInfo"));
            this.name = obj.name;
            this.uploadRes = JSON.parse(obj.uploadRes);
            this.createdRes = JSON.parse(obj.createdRes);
            this.nameType = obj.type;
            this.brandType = obj.brandType;
            this.autoType = obj.autoType;
            this.doMain = JSON.parse(obj.doMain);
            this.doMainIndex = obj.doMainIndex;
            this.doMainchildIndex = obj.doMainchildIndex;
            this.selectAutoList = JSON.parse(obj.selectAutoList);
            this.selectList = JSON.parse(obj.selectList);
            this.trademarkList = JSON.parse(obj.trademarkList);
        }
        this.loadTemplateUrl();
    },
    methods: {
        tabFn(e) {
            let type = e.target.dataset.type;
            this.type = type;
        },
        loadTemplateUrl() {
            this.$Http(
                "/template/findTemplateUrl",
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                this.templateUrl = res.data.data;
            });
        },
        // 上传图片
        upload(type) {
            this.$refs[type].click();
            this.$refs[type].onchange = e => {
                let file = e.target.files[0];
                if (!file) {
                    return false;
                }
                let param = new FormData(); // 创建form对象
                param.append("file", file);
                param.append("userId", this.$store.state.userInfo.userId);
                param.append("type", "upload_file_type_04");
                if (file.size > 10485760) {
                    popup.created({
                        content: "上传文件不能大于10M",
                        time: 2000
                    });
                    return false;
                }
                if (
                    file.type != "image/jpeg" &&
                    file.type != "application/pdf"
                ) {
                    popup.created({
                        content: "只能上传.jpg或.pdf文件",
                        time: 2000
                    });
                    return false;
                }
                let loading = popup.created({
                    type: "loading",
                    icon: "&#xe61c",
                    content: "上传中..."
                });
                axios
                    .post(`${this.$baseUrl}/file/singleUpload`, param, {
                        headers: { "Content-Type": "multipart/form-data" }
                    })
                    .then(res => {
                        loading.close();
                        if (res.data.code == 0) {
                            popup.created({
                                content: "上传成功",
                                time: 2000
                            });
                            if (type == "attorney" || type == "priority") {
                                this.$set(this.uploadFile, type, res.data.data);
                            }

                            if (type == "copy") {
                                this.$set(this.person, type, res.data.data);
                            }

                            if (type == "business") {
                                this.$set(this.company, type, res.data.data);
                            }
                            if (type == "personBusiness") {
                                this.$set(this.person, type, res.data.data);
                            }

                            sessionStorage.setItem(
                                "concatInfo",
                                JSON.stringify({
                                    type: this.concatType,
                                    person: JSON.stringify(this.person),
                                    company: JSON.stringify(this.company),
                                    uploadFile: JSON.stringify(this.uploadFile)
                                })
                            );
                        } else {
                            popup.created({
                                content: "上传错误，稍后重试",
                                time: 2000
                            });
                        }
                    })
                    .catch(() => {
                        loading.close();
                        popup.created({
                            content: "上传错误，稍后重试",
                            time: 2000
                        });
                    });
            };
        },
        submit() {
            sessionStorage.setItem("isFrom", true);
            if (!this.isAgree) {
                popup.created({
                    content: "请先阅读商标服务合同",
                    time: 2000
                });
                return false;
            }
            if (!this.product) {
                popup.created({
                    content: "产品过期，请重新下单",
                    time: 2000
                });
                Cookies.remove("product");
                sessionStorage.removeItem("brandInfo");
                sessionStorage.removeItem("concatInfo");
                window.history.href = "/";
                return false;
            }
            let arr = [
                "",
                "trademark_type1",
                "trademark_type2",
                "trademark_type3"
            ];
            let exampleType = ["", "example_type1", "example_type2"];
            let classCreateWay = ["class_create_way1", "class_create_way2"];
            let applicantType = ["applicant_type2", "applicant_type1"];
            let codes = [];
            this.selectList.map(item => {
                item.sonArr.map(childItem => {
                    codes.push({
                        classLevel1Code: item.number,
                        classLevel1Name: item.name,
                        classLevel3Code: childItem.number,
                        classLevel3Name: childItem.name
                    });
                });
            });
            this.selectAutoList.map(item => {
                if (item) {
                    item.sonArr.map(childItem => {
                        codes.push({
                            classLevel1Code: item.number,
                            classLevel1Name: item.name,
                            classLevel3Code: childItem.number,
                            classLevel3Name: childItem.name
                        });
                    });
                }
            });
            let obj = {
                orderJson: JSON.stringify({
                    invoiceFee: 0,
                    userId: this.$store.state.userInfo.userId,
                    serviceId: this.product.serviceId,
                    serviceAttrId: this.product.serviceAttrId
                        ? this.product.serviceAttrId
                        : null,
                    allPrice: this.officeTotal,
                    allNum: this.product.num,
                    contactName: this.company.contactName
                        ? this.company.contactName
                        : this.person.contactName,
                    contactPhone: this.company.contactPhone
                        ? this.company.contactPhone
                        : this.person.contactPhone,
                    contactEmail: this.company.contactEmail
                        ? this.company.contactEmail
                        : this.person.contactEmail,
                    contactTelephone: this.company.contactTelephone
                        ? this.company.contactTelephone
                        : this.person.contactTelephone,
                    remark: this.remark
                }),
                trademarkAndApplicantJson: JSON.stringify({
                    type: arr[this.nameType],
                    name: this.name,
                    exampleType: exampleType[this.autoType],
                    exampleAddress: this.createdRes
                        ? this.createdRes.fsPath
                        : this.uploadRes.fsPath,
                    classCreateWay: classCreateWay[this.brandType],
                    suggestedFirstLevel: this.doMainIndex
                        ? this.doMain[parseInt(this.doMainIndex)].id
                        : null,
                    suggestedSecondLevel: this.doMainchildIndex,
                    applicantType: applicantType[this.concatType],
                    applicantName: this.company.applicantName
                        ? this.company.applicantName
                        : this.person.applicantName,
                    applicantCardNo: this.company.applicantCardNo
                        ? this.company.applicantCardNo
                        : this.person.applicantCardNo,
                    businessLicenceArea: this.company.businessLicenceArea
                        ? this.company.businessLicenceArea
                        : this.person.businessLicenceArea,
                    postalcode: this.company.postalcode
                        ? this.company.postalcode
                        : this.person.postalcode,
                    businessLicenceAddress: this.company.businessLicenceAddress
                        ? this.company.businessLicenceAddress
                        : this.person.businessLicenceAddress,
                    applicantCardFile: this.company.applicantCardFile
                        ? this.company.applicantCardFile
                        : this.person.copy.fsPath,
                    businessLicenceFile: this.company.business
                        ? this.company.business.fsPath
                        : this.person.personBusiness.fsPath,
                    proxyFile: this.uploadFile.attorney.fsPath,
                    priorityFile: this.uploadFile.priority.fsPath
                }),
                trademarkClassListJson: JSON.stringify(codes)
            };
            this.$Http("/order/saveAutoOrder", obj, "post", true).then(res => {
                Cookies.remove("product");
                sessionStorage.removeItem("brandInfo");
                sessionStorage.removeItem("concatInfo");
                this.$router.replace("/order/pay/" + res.data.data);
            });
        }
    },
    components: {
        toTop
    },
    computed: {
        officeTotal() {
            let total = 0;
            if (parseInt(this.brandType)) {
                this.selectAutoList.map(item => {
                    if (item) {
                        if (item.sonArr.length <= 10) {
                            total += 300;
                        } else {
                            total += item.sonArr.length * 30;
                        }
                    }
                });
                return total;
            }
            this.selectList.map(item => {
                if (item.sonArr.length <= 10) {
                    total += 300;
                } else {
                    total += item.sonArr.length * 30;
                }
            });
            return total;
        }
    }
};
</script>
<style scoped>
@import url("../../assets/css/order.css");

.order-title {
    padding-left: 10px;
    line-height: 1em;
    height: 1em;
    border-left: 3px solid #f08b2f;
    font-size: 14px;
    font-weight: normal;
}

.order-main-title a.edit,
.order-title a.edit {
    margin-left: 10px;
    padding-left: 18px;
    font-size: 12px;
    color: #f08b2f;
    background: url(../../assets/images/order/icon-edit.png) no-repeat 0 center;
    background-size: 14px;
}

.order-detail-box h2 a.edit {
    float: right;
    margin-left: 8px;
    font-size: 14px;
}

.file-agree-box {
    margin-top: 20px;
    margin-bottom: 8px;
    font-size: 12px;
    color: #666;
    text-align: center;
    -webkit-user-select: none;
}

.file-agree-box .checkbox {
    width: 13px;
    height: 13px;
    background-size: cover;
    vertical-align: middle;
}

.file-agree-box a:hover {
    color: #f08b2f;
}
.file-agree-box a {
    color: #f08b2f;
}

.file-agree-box span {
    vertical-align: middle;
}
.checkbox2 {
    font-size: 21px;
    color: #666;
    vertical-align: middle;
}
.checkbox2.checked {
    font-size: 20px;
    color: #f08b2f;
}
</style>
