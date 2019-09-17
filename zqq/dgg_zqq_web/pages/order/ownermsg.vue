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
        <li class="line"></li>
        <li class="step step-4">
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
                <i class="icon icon-renzheng-1"></i>
              </span>
              <span class="r-2">好评率：100%</span>
              <!--<a class="r-3" target="_blank" href=""><i class="iconfont icon-xiaoxi"></i>咨询一下</a>-->
              <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">
                <i class="iconfont icon-xiaoxi"></i>咨询一下</a>
            </dd>
          </dl>
        </li>
        <li class="list">
          <dl class="clearfix">
            <dt><img src="../../assets/images/show/p2.jpg" alt="代理人"></dt>
            <dd>
              <span class="r-1">李亚南
                <i class="icon icon-renzheng-1"></i>
              </span>
              <span class="r-2">好评率：100%</span>
              <!--<a class="r-3" target="_blank" href=""><i class="iconfont icon-xiaoxi"></i>咨询一下</a>-->
              <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">
                <i class="iconfont icon-xiaoxi"></i>咨询一下</a>
            </dd>
          </dl>
        </li>
        <li class="list">
          <dl class="clearfix">
            <dt><img src="../../assets/images/show/p3.jpg" alt="代理人"></dt>
            <dd>
              <span class="r-1">高峰
                <i class="icon icon-renzheng-1"></i>
              </span>
              <span class="r-2">好评率：100%</span>
              <!--<a class="r-3" target="_blank" href=""><i class="iconfont icon-xiaoxi"></i>咨询一下</a>-->
              <a class="r-3" href="javascript:;" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)">
                <i class="iconfont icon-xiaoxi"></i>咨询一下</a>
            </dd>
          </dl>
        </li>
      </ul>
    </div>
    <div class="agentInfo-wrap applicant-wrap">
      <h2 class="order-title">申请人信息</h2>
      <div class="section-base" :class="{'open': type == 0}">
        <table class="table-1">
          <thead>
            <tr>
              <td class="td-title">
                <i class="i-star">*</i>申请人资质：</td>
              <td class="td-content">
                <a class="btn-choice fownertype " :class="{'active': type == 1}" href="javascript:;" @click="tabFn" data-type="1">
                  <i class="iconfont icon-duigou">&#xe63f;</i>企业</a>
                <a class="btn-choice fownertype" :class="{'active': type == 0}" href="javascript:;" @click="tabFn" data-type="0">
                  <i class="iconfont icon-duigou">&#xe63f;</i>个体工商户</a>
              </td>
            </tr>
          </thead>
        </table>
        <div class="applicant-form">
          <table width="100%" class="table-1 table-applicant table-type1">
            <tbody class="tbody-qiye" :class="{'atthis': type == 1}">
              <tr class="company">
                <td class="td-title">
                  <i class="i-star">*</i>公司名称：</td>
                <td class="td-content contact-select-container">
                  <input type="hidden" name="applicantId" value="35400">
                  <dl class="mySelect mySelectInput contact-select-input">
                    <dt class="select-text">
                      <input class="text" maxlength="30" v-model="company.applicantName" name="cfname" type="text" @input="changeCompany" placeholder="请输入/选择企业名称" isnew="0">
                      <span @click="isOpen=!isOpen" class="arrow">
                        <i class="icon-arrowdown"></i>
                      </span>
                    </dt>
                    <dd class="select-menu" v-if="isOpen">
                      <span v-for="(item,index) in applicantCompanySeleted" @click="seletedCompany(index)" :key="item.id" class="menu">
                        <a>{{item.applicantName}}</a>
                      </span>
                    </dd>
                  </dl>
                </td>
              </tr>
              <tr class="company">
                <td class="td-title td-top">
                  <i class="i-star">*</i>营业执照所在地区：</td>
                <td class="td-content areaFa">
                  <input type="text" maxlength="30" name="cfaddress" id="qiye-street" class="myInput address m-address" v-model="company.businessLicenceArea" readonly placeholder="请选择区县" @click.stop="showAreaBox = 1">
                  <div class="areaBox" v-show="showAreaBox">
                    <div class="navTitle" @click.stop="showAreaBox = 1">
                      <div :class="{active:areaBOx ==0}" @click="switchBox(0)">省份</div>
                      <div :class="{active:areaBOx ==1}" @click="switchBox(1)">城市</div>
                      <div :class="{active:areaBOx ==2}" @click="switchBox(2)">区县</div>
                    </div>
                    <ul class="ulBox" v-show='areaBOx == 0' @click.stop="showAreaBox = 1">
                      <li v-for="(item,idx) in areaJson" :key="idx" :title="item.name" @click="chooseArea('provinceIdx',idx,item.name)">{{provinceName(item.name)}}</li>
                    </ul>
                    <ul class="ulBox" v-if='provinceIdx != -1' v-show=' areaBOx == 1 ' @click.stop="showAreaBox = 1">
                      <li v-for="(item,idx) in areaJson[provinceIdx].child" :key="idx" :title="item.name" @click="chooseArea('cityIdx',idx,item.name)">{{item.name}}</li>
                    </ul>
                    <ul class="ulBox" v-if='cityIdx != -1' v-show=' areaBOx == 2 '>
                      <li v-for="(item,idx) in areaJson[provinceIdx].child[cityIdx].child" :key="idx" :title="item.name" @click="chooseArea('countyIdx',idx,item.nam)">{{item.name}}</li>
                    </ul>
                  </div>
                </td>
              </tr>
              <tr class="company">
                <td class="td-title">
                  <i class="i-star">*</i>邮政编码：</td>
                <td class="td-content">
                  <input type="text" maxlength="6" v-validate="'required|postcode'" v-model="company.postalcode" id="qiye-postcode" name="cfcode" class="myInput code" placeholder="请填写申请人所在行政区县的邮政编码">
                </td>
              </tr>
              <tr class="company">
                <td class="td-title">
                  <i class="i-star">*</i>营业执照详情地址：</td>
                <td class="td-content">
                  <input type="text" maxlength="30" name="cfaddress" id="qiye-street" class="myInput address m-address" v-model="company.businessLicenceAddress" placeholder="请与营业执照区/县后具体地址保持一致">
                </td>
              </tr>
            </tbody>
            <tbody class="tbody-gsh" :class="{'atthis': type == 0}">
              <tr class="personal">
                <td class="td-title">
                  <i class="i-star">*</i>个人名称：</td>
                <td class="td-content contact-select-container">
                  <dl class="mySelect mySelectInput contact-select-input">
                    <dt class="select-text">
                      <input class="text" @input="changePerson" maxlength="10" name="fname" v-model="person.applicantName" type="text" placeholder="请与个体工商户营业执照的字号名称一致" isnew="0">
                      <span class="arrow" @click="isOpen=!isOpen">
                        <i class="icon-arrowdown"></i>
                      </span>
                    </dt>
                    <dd class="select-menu" v-if="isOpen">
                      <span v-for="(item,index) in applicantPersonSeleted" @click="seletedPerson(index)" :key="item.id" class="menu">
                        <a>{{item.applicantName}}</a>
                      </span>
                    </dd>
                  </dl>
                </td>
              </tr>
              <tr class="personal">
                <td class="td-title">
                  <i class="i-star">*</i>身份证号：</td>
                <td class="td-content">
                  <input type="text" v-validate="'required|idcard'" v-model="person.applicantCardNo" maxlength="18" id="geren-idCard" name="fidcardcode" class="myInput" placeholder="请填写营业执照字号名称对应的身份证号">
                </td>
              </tr>
              <tr class="personal">
                <td class="td-title td-top">
                  <i class="i-star">*</i>营业执照所在地区：</td>
                <td class="td-content areaFa">
                  <input type="text" maxlength="30" name="faddress" id="qiye-street" class="myInput address m-address" v-model="person.businessLicenceArea" readonly placeholder="请选择区县" @click.stop="showAreaBox = 1">
                  <div class="areaBox" v-show="showAreaBox">
                    <div class="navTitle" @click.stop="showAreaBox = 1">
                      <div :class="{active:areaBOx ==0}" @click="switchBox(0)">省份</div>
                      <div :class="{active:areaBOx ==1}" @click="switchBox(1)">城市</div>
                      <div :class="{active:areaBOx ==2}" @click="switchBox(2)">区县</div>
                    </div>
                    <ul class="ulBox" v-show='areaBOx == 0' @click.stop="showAreaBox = 1">
                      <li v-for="(item,idx) in areaJson" :key="idx" :title="item.name" @click="chooseArea('provinceIdx',idx,item.name)">{{provinceName(item.name)}}</li>
                    </ul>
                    <ul class="ulBox" v-if='provinceIdx != -1' v-show=' areaBOx == 1 ' @click.stop="showAreaBox = 1">
                      <li v-for="(item,idx) in areaJson[provinceIdx].child" :key="idx" :title="item.name" @click="chooseArea('cityIdx',idx,item.name)">{{item.name}}</li>
                    </ul>
                    <ul class="ulBox" v-if='cityIdx != -1' v-show=' areaBOx == 2 '>
                      <li v-for="(item,idx) in areaJson[provinceIdx].child[cityIdx].child" :key="idx" :title="item.name" @click="chooseArea('countyIdx',idx,item.nam)">{{item.name}}</li>
                    </ul>
                  </div>
                </td>
              </tr>
              <tr class="personal">
                <td class="td-title">
                  <i class="i-star">*</i>邮政编码 ：</td>
                <td class="td-content">
                  <input type="text" v-validate="'required|postcode'" id="geren-postcode" maxlength="6" name="fcode" v-model="person.postalcode" class="myInput code" placeholder="请填写申请人所在行政区县的邮政编码">
                </td>
              </tr>
              <tr class="personal">
                <td class="td-title">
                  <i class="i-star">*</i>营业执照详情地址：</td>
                <td class="td-content">
                  <input type="text" name="faddressl" id="geren-street" class="myInput address m-address" maxlength="30" v-model="person.businessLicenceAddress" placeholder="请与营业执照区/县后具体地址保持一致">
                </td>
              </tr>
            </tbody>
          </table>
          <div id="uploader" class="plupload_modal"></div>
          <h2 class="order-title">订单联系人</h2>
          <table width="100%" class="table-1">
            <tbody class="tbody-qiye" :class="{'atthis': type == 1}">
              <tr class="company">
                <td class="td-title">
                  <i class="i-star">*</i>客户联系人：</td>
                <td class="td-content">
                  <input type="text" maxlength="10" name="cfcontactName" v-model="company.contactName" class="myInput" placeholder="此信息很重要，官方文件送达需与您联系">
                </td>
              </tr>
              <tr class="company">
                <td class="td-title">
                  <i class="i-star">*</i>联系电话：</td>
                <td class="td-content">
                  <input type="text" maxlength="11" v-validate="'required|phone'" name="cfcontactTel" v-model="company.contactPhone" class="myInput" placeholder="请填写11位手机号码">
                </td>
              </tr>
              <tr class="company">
                <td class="td-title">
                  <i class="i-star">*</i>电子邮箱：</td>
                <td class="td-content">
                  <input type="text" maxlength="30" v-validate="'required|email'" name="cfcontactMail" v-model="company.contactEmail" class="myInput" placeholder="重要官文会通过邮件通知，请注意查收并及时回复">
                </td>
              </tr>
              <tr>
                <td class="td-title">
                  <i class="i-star"></i>座机电话：</td>
                <td class="td-content">
                  <input type="text" maxlength="16" name="cftelephone" v-model="company.contactTelephone" class="myInput" placeholder="区号-座机号码">
                </td>
              </tr>
            </tbody>
            <tbody class="tbody-gsh" :class="{'atthis': type == 0}">
              <tr class="personal">
                <td class="td-title">
                  <i class="i-star">*</i>客户联系人：</td>
                <td class="td-content">
                  <input name="fcontactName" v-model="person.contactName" type="text" class="myInput" maxlength="10" placeholder="此信息很重要，官方文件送达需与您联系">
                </td>
              </tr>
              <tr class="personal">
                <td class="td-title">
                  <i class="i-star">*</i>联系电话：</td>
                <td class="td-content">
                  <input type="text" maxlength="11" v-validate="'required|phone'" name="fcontactTel" v-model="person.contactPhone" class="myInput" placeholder="请填写11位手机号码">
                </td>
              </tr>
              <tr class="personal">
                <td class="td-title">
                  <i class="i-star">*</i>电子邮箱：</td>
                <td class="td-content">
                  <input type="text" maxlength="30" v-validate="'required|email'" name="fcontactMail" v-model="person.contactEmail" class="myInput" placeholder="重要官文会通过邮件通知，请注意查收并及时回复">
                </td>
              </tr>
              <tr>
                <td class="td-title">
                  <i class="i-star"></i>座机电话：</td>
                <td class="td-content">
                  <input type="text" maxlength="16" name="ftelephone" v-model="person.contactTelephone" class="myInput" placeholder="区号-座机号码">
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <h2 class="order-title">
          材料信息
          <span class="file-info-tips">（可在支付完成后，在订单中进行上传）
            <i @click="isUpload = true" v-if="!isUpload" class="iconfont">&#xe623;</i>
            <i @click="isUpload = false" v-if="isUpload" class="iconfont">&#xe692;</i>
          </span>
        </h2>
        <div v-if="templateUrl" class="material-form" :class="{'active': isUpload}">
          <a href="javascript:void(0)" class="file-upload-warning" @click="isTips=true">查看上传文件注意事项</a>
          <!--国内文件上传-->
          <div class="file-upload-container table-type1 active">
            <div class="f-content file-qiye tbody-qiye scroll">
              <div class="file-upload-list">
                <label>营业执照<br>副本复印件</label>
                <div class="l-content">
                  <ul class="showPhotos clearfix">
                    <input type="hidden" name="flicense" value="">
                    <li class="list-photo flicenseli ">
                      <div class="top">
                        <div class="top-container">
                          <div class="photo-box btnupload" type="flicense">
                            <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="company.business" :src="company.business.fsHost+company.business.fsPath ">
                            <div v-if="!company.business" class="bg flicensebg" @click="upload('business')">
                              <span class="icon ">
                                <i class="iconfont icon-jiahao ">&#xe623;</i>
                              </span>
                              <span class="text">（需盖公章）</span>
                            </div>
                          </div>
                          <dl class="shadow-box" v-if="company.business">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box">
                                <a class="btn flicenselink " :href="company.business.fsHost+company.business.fsPath" target="_blank ">查看大图</a>
                                <a class="btn btnupload" @click="upload('business')" type="flicense ">重新上传</a>
                              </span>
                            </dd>
                          </dl>
                          <div class="moxie-shim moxie-shim-html5 ">
                            <input ref="business" type="file" accept="image/jpeg,.jpg,application/pdf,.pdf">
                          </div>
                        </div>
                      </div>
                    </li>
                    <li class="list-photo photo-shili show ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box ">
                            <i class="icon-tag-shili "></i>
                            <img class="photo " src="../../assets/images/order/yyzz.jpg">
                          </div>
                          <dl class="shadow-box ">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn " :href="templateUrl[5].fileUrl" target="_blank ">查看示例</a>
                              </span>
                            </dd>
                          </dl>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="file-upload-list ">
                <label>
                  委托书
                  <a class="btn createpower " :href="templateUrl[3].fileUrl">下载模板</a>
                </label>
                <div class="l-content ">
                  <ul class="showPhotos clearfix ">
                    <li class="list-photo fproxyli ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box btnupload " type="fproxy ">
                            <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="uploadFile.attorney" :src="uploadFile.attorney.fsHost+uploadFile.attorney.fsPath ">
                            <div v-if="!uploadFile.attorney" class="bg flicensebg" @click="upload('attorney')">
                              <span class="icon">
                                <i class="iconfont icon-jiahao ">&#xe623;</i>
                              </span>
                              <span class="text">（需盖公章）</span>
                            </div>
                          </div>
                          <dl class="shadow-box" v-if="uploadFile.attorney">
                            <i class="shadow"></i>
                            <dd class="mybtns">
                              <span class="box">
                                <a class="btn fproxylink" :href="uploadFile.attorney.fsHost+uploadFile.attorney.fsPath" target="_blank ">查看大图</a>
                                <a class="btn btnupload" @click="upload('attorney')" type="fproxy ">重新上传</a>
                              </span>
                            </dd>
                          </dl>
                          <div class="moxie-shim moxie-shim-html5 ">
                            <input ref="attorney" type="file" accept="image/jpeg,.jpg,application/pdf,.pdf ">
                          </div>
                        </div>
                      </div>
                    </li>
                    <li class="list-photo photo-shili show ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box ">
                            <i class="icon-tag-shili "></i>
                            <img class="photo " src="../../assets/images/order/ex-file-wts.jpg">
                          </div>
                          <dl class="shadow-box ">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn " :href="templateUrl[2].fileUrl" target="_blank ">查看示例</a>
                              </span>
                            </dd>
                          </dl>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="file-upload-list ">
                <label>
                  <!--  <a class="icon icon-qm " href="/index/about/13.html " target="_blank "></a> -->
                  <span>优先权证明</span>
                </label>
                <div class="l-content ">
                  <ul class="showPhotos clearfix ">
                    <li class="list-photo fpriorityli ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box btnupload " type="fpriority ">
                            <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="uploadFile.priority" :src="uploadFile.priority.fsHost+uploadFile.priority.fsPath ">
                            <div v-if="!uploadFile.priority" class="bg flicensebg" @click="upload('priority')">
                              <span class="icon ">
                                <i class="iconfont icon-jiahao ">&#xe623;</i>
                              </span>
                              <span class="text ">针对巴黎公约<br>后续申请</span>
                            </div>
                          </div>
                          <dl class="shadow-box " v-if="uploadFile.priority">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn fprioritylink " :href="uploadFile.priority.fsHost+uploadFile.priority.fsPath" target="_blank ">查看大图</a>
                                <a class="btn btnupload" @click="upload('priority')" type="fpriority">重新上传</a>
                              </span>
                            </dd>
                          </dl>
                          <div class="moxie-shim moxie-shim-html5 ">
                            <input ref="priority" type="file" accept="image/jpeg,.jpg,application/pdf,.pdf ">
                          </div>
                        </div>
                      </div>
                    </li>
                    <li class="list-photo photo-shili show ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box ">
                            <i class="icon-tag-shili "></i>
                            <img class="photo " src="../../assets/images/order/ex-file-yxqzm.jpg">
                          </div>
                          <dl class="shadow-box ">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn " :href="templateUrl[4].fileUrl" target="_blank ">查看示例</a>
                              </span>
                            </dd>
                          </dl>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="f-content file-gsh tbody-gsh scroll">
              <div class="file-upload-list ">
                <label>身份证复印件</label>
                <div class="l-content ">
                  <ul class="showPhotos clearfix ">
                    <li class="list-photo fidcardli ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box btnupload " type="fidcard ">
                            <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="person.copy" :src="person.copy.fsHost+person.copy.fsPath ">
                            <div v-if="!person.copy" class="bg flicensebg" @click="upload('copy')">
                              <span class="icon ">
                                <i class="iconfont icon-jiahao">&#xe623;</i>
                              </span>
                              <span class="text ">正反面复印件<br>（需签字）</span>
                            </div>
                          </div>
                          <dl class="shadow-box" v-if="person.copy">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn fidcardlink " :href="person.copy.fsHost+person.copy.fsPath " target="_blank ">查看大图</a>
                                <a class="btn btnupload " @click="upload('copy')" type="fidcard ">重新上传</a>
                              </span>
                            </dd>
                          </dl>
                          <div class="moxie-shim moxie-shim-html5 ">
                            <input ref="copy" type="file" accept="image/jpeg,.jpg,application/pdf,.pdf ">
                          </div>
                        </div>
                      </div>
                    </li>
                    <li class="list-photo photo-shili show ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box ">
                            <i class="icon-tag-shili "></i>
                            <img class="photo " src="../../assets/images/order/ex-file-sfz.jpg">
                          </div>
                          <dl class="shadow-box ">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn " :href="templateUrl[0].fileUrl" target="_blank ">查看示例</a>
                              </span>
                            </dd>
                          </dl>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="file-upload-list ">
                <label>个体户营业执照</label>
                <div class="l-content ">
                  <ul class="showPhotos clearfix ">
                    <li class="list-photo flicenseli ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box btnupload " type="flicense ">
                            <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="person.personBusiness" :src="person.personBusiness.fsHost+person.personBusiness.fsPath ">
                            <div v-if="!person.personBusiness" class="bg flicensebg" @click="upload('personBusiness')">
                              <span class="icon ">
                                <i class="iconfont icon-jiahao ">&#xe623;</i>
                              </span>
                              <span class="text ">复印件<br>（需签字）</span>
                            </div>
                          </div>
                          <dl class="shadow-box " v-if="person.personBusiness">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn flicenselink " :href="person.personBusiness.fsHost+person.personBusiness.fsPath " target="_blank ">查看大图</a>
                                <a class="btn btnupload " @click="upload('personBusiness')" type="flicense ">重新上传</a>
                              </span>
                            </dd>
                          </dl>
                          <div class="moxie-shim moxie-shim-html5 ">
                            <input ref="personBusiness" type="file" accept="image/jpeg,.jpg,application/pdf,.pdf ">
                          </div>
                        </div>
                      </div>
                    </li>
                    <li class="list-photo photo-shili show ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box ">
                            <i class="icon-tag-shili "></i>
                            <img class="photo " src="../../assets/images/order/yyzz.jpg">
                          </div>
                          <dl class="shadow-box ">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn " :href="templateUrl[1].fileUrl" target="_blank ">查看示例</a>
                              </span>
                            </dd>
                          </dl>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>

              <div class="file-upload-list ">
                <label>
                  委托书
                  <a class="btn createpower " :href="templateUrl[3].fileUrl">下载模板</a>
                </label>
                <div class="l-content ">
                  <ul class="showPhotos clearfix ">
                    <li class="list-photo fproxyli ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box btnupload " type="fproxy ">
                            <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="uploadFile.attorney" :src="uploadFile.attorney.fsHost+uploadFile.attorney.fsPath ">
                            <div v-if="!uploadFile.attorney" class="bg flicensebg" @click="upload('attorney')">
                              <span class="icon">
                                <i class="iconfont icon-jiahao ">&#xe623;</i>
                              </span>
                              <span class="text">（需盖公章）</span>
                            </div>
                          </div>
                          <dl class="shadow-box" v-if="uploadFile.attorney">
                            <i class="shadow"></i>
                            <dd class="mybtns">
                              <span class="box">
                                <a class="btn fproxylink" :href="uploadFile.attorney.fsHost+uploadFile.attorney.fsPath" target="_blank ">查看大图</a>
                                <a class="btn btnupload" @click="upload('attorney')" type="fproxy ">重新上传</a>
                              </span>
                            </dd>
                          </dl>
                          <div class="moxie-shim moxie-shim-html5 ">
                            <input ref="attorney" type="file" accept="image/jpeg,.jpg,application/pdf,.pdf ">
                          </div>
                        </div>
                      </div>
                    </li>
                    <li class="list-photo photo-shili show ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box ">
                            <i class="icon-tag-shili "></i>
                            <img class="photo " src="../../assets/images/order/ex-file-wts.jpg">
                          </div>
                          <dl class="shadow-box ">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn " :href="templateUrl[2].fileUrl" target="_blank ">查看示例</a>
                              </span>
                            </dd>
                          </dl>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="file-upload-list ">
                <label>
                  <!--  <a class="icon icon-qm " href="/index/about/13.html " target="_blank "></a> -->
                  <span>优先权证明</span>
                </label>
                <div class="l-content ">
                  <ul class="showPhotos clearfix ">
                    <li class="list-photo fpriorityli ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box btnupload " type="fpriority ">
                            <img onerror="src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540471738953&di=9b8e50d26184fc7a4b1e5b9c73da9b55&imgtype=0&src=http%3A%2F%2Fpic.downyi.com%2Fupload%2F2017-10%2F201710191446445588.png'" class="photo fproxypic" v-if="uploadFile.priority" :src="uploadFile.priority.fsHost+uploadFile.priority.fsPath ">
                            <div v-if="!uploadFile.priority" class="bg flicensebg" @click="upload('priority')">
                              <span class="icon ">
                                <i class="iconfont icon-jiahao ">&#xe623;</i>
                              </span>
                              <span class="text ">针对巴黎公约<br>后续申请</span>
                            </div>
                          </div>
                          <dl class="shadow-box " v-if="uploadFile.priority">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn fprioritylink " :href="uploadFile.priority.fsHost+uploadFile.priority.fsPath" target="_blank ">查看大图</a>
                                <a class="btn btnupload" @click="upload('priority')" type="fpriority">重新上传</a>
                              </span>
                            </dd>
                          </dl>
                          <div class="moxie-shim moxie-shim-html5 ">
                            <input ref="priority" type="file" accept="image/jpeg,.jpg,application/pdf,.pdf ">
                          </div>
                        </div>
                      </div>
                    </li>
                    <li class="list-photo photo-shili show ">
                      <div class="top ">
                        <div class="top-container ">
                          <div class="photo-box ">
                            <i class="icon-tag-shili "></i>
                            <img class="photo " src="../../assets/images/order/ex-file-yxqzm.jpg">
                          </div>
                          <dl class="shadow-box ">
                            <i class="shadow "></i>
                            <dd class="mybtns ">
                              <span class="box ">
                                <a class="btn " :href="templateUrl[4].fileUrl" target="_blank ">查看示例</a>
                              </span>
                            </dd>
                          </dl>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>

            </div>
          </div>
        </div>
        <div class="section-btns clearfix ">
          <a class="btn-step btn-inverse " href="/order/order ">上一步</a>
          <a class="btn-step " href="javascript:;" @click="submit">下一步</a>
        </div>
      </div>
    </div>
    <to-top />

    <div class="model" v-if="isTips">
      <div class="content" style="width: 700px">
        <i class="iconfont" @click="isTips=false">&#xe7fc;</i>
        <h1>上传文件注意事项</h1>
        <div class="text">
          <table class="table-search">
            <tbody>
              <tr>
                <td colspan="2" align="center" class="td-content" style="color:#ff001f;">材料要求盖章/签字清晰完整，建议盖章/签字在文件中间空白处不压字</td>
              </tr>
              <tr>
                <td class="td-title" width="100px" align="right">委托书：</td>
                <td class="td-content">请上传由申请人盖章或签字的中文代理委托书彩色扫描件，格式为
                  <i class="color">JPG</i>，宽高的大小为
                  <i class="color">4000px~600px</i>，
                  <i class="color">300dpi</i>分辨率，大小不能超过10M。</td>
              </tr>
              <tr>
                <td class="td-title" align="right">营业执照：</td>
                <td class="td-content">格式为
                  <i class="color">PDF 或 JPG</i>，大小不能超过
                  <i class="color">10M</i>
                </td>
              </tr>
              <tr>
                <td colspan="2" align="center" class="td-content" style="color:#ff001f;">
                  <span>商标局声明：自2018年1月1日起，国内申请人办理商标注册、异议、变更、转让、续展等商标事宜，应当使用标注统一社会信用代码身份证明文件，未使用则不予受理。</span>
                </td>
              </tr>
              <tr>
                <td class="td-title" align="right">身份证：</td>
                <td class="td-content">格式为
                  <i class="color">PDF 或 JPG</i>，大小不能超过
                  <i class="color">10M</i>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import toTop from "@/components/toTop";
import popup from "~/components/popup"; //消息
import Area from "@/assets/js/area.json";

import axios from "axios";
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
            isUpload: false,
            type: 1,
            isTips: false,
            applicantPerson: [],
            applicantCompany: [],
            applicantPersonSeleted: [],
            applicantCompanySeleted: [],
            isOpen: false,
            person: {
                applicantName: "",
                applicantCardNo: "",
                businessLicenceArea: "", //营业执照
                postalcode: "",
                businessLicenceAddress: "",
                contactName: "",
                contactPhone: "",
                contactEmail: "",
                contactTelephone: "",
                copy: "", // 身份证复印件
                personBusiness: "" // 营业执照
            },
            company: {
                applicantName: "",
                businessLicenceArea: "", //营业执照
                postalcode: "",
                businessLicenceAddress: "",
                contactName: "",
                contactPhone: "",
                contactEmail: "",
                contactTelephone: "",
                business: ""
            },
            uploadFile: {
                attorney: "", // 委托书
                priority: "" // 优先权
            },
            templateUrl: "",
            showAreaBox: 0,
            areaBOx: 0,
            areaJson: [],
            provinceIdx: -1, //省
            cityIdx: -1, //市
            countryIdx: -1
        };
    },
    mounted() {
        this.loadApplicantName();
        this.loadTemplateUrl();
        document.addEventListener("click", e => {
            if (
                e.target.className == "arrow" ||
                e.target.className == "icon-arrowdown"
            ) {
                return false;
            }
            this.isOpen = false;
        });

        if (sessionStorage.getItem("concatInfo")) {
            let obj = JSON.parse(sessionStorage.getItem("concatInfo"));
            (this.type = obj.type),
                (this.person = JSON.parse(obj.person)),
                (this.company = JSON.parse(obj.company)),
                (this.uploadFile = JSON.parse(obj.uploadFile));
        }
        this.areaJson = Area.area_json;
    },
    computed: {
        commonBusinessLicenceArea() {
            if (this.provinceIdx == -2) {
                return "";
            }
            if (this.provinceIdx == -1) {
                return "";
            }
            if (this.cityIdx == -1) {
                return this.areaJson[this.provinceIdx].name;
            }
            if (this.countryIdx == -1) {
                let city = this.areaJson[this.provinceIdx].child[this.cityIdx]
                    .name;
                if (city == "县") {
                    city = this.areaJson[this.provinceIdx].name;
                }
                return this.areaJson[this.provinceIdx].name + city;
            }
            let city = this.areaJson[this.provinceIdx].child[this.cityIdx].name;
            if (city == "县") {
                city = this.areaJson[this.provinceIdx].name;
            }
            return (
                this.areaJson[this.provinceIdx].name +
                city +
                this.areaJson[this.provinceIdx].child[this.cityIdx].child[
                    this.countryIdx
                ].name
            );
        }
    },
    watch: {
        showAreaBox() {
            if (this.showAreaBox) {
                document.addEventListener("click", this.showAreaBoxFunc, false);
            } else {
                document.removeEventListener(
                    "click",
                    this.showAreaBoxFunc,
                    false
                );
            }
            console.log(1);
            let obj = {
                businessLicenceArea: this.commonBusinessLicenceArea,
                businessLicenceAddress: this.commonBusinessLicenceArea
            };
            this.company = Object.assign({}, this.company, obj);
            this.person = Object.assign({}, this.person, obj);
        }
    },
    methods: {
        // 输入
        changeCompany(e) {
            let value = e.target.value;
            this.applicantCompanySeleted = [];
            this.applicantCompany.map(item => {
                if (item.applicantName.indexOf(value) > -1) {
                    this.applicantCompanySeleted.push(item);
                }
            });
            this.isOpen = true;
        },
        changePerson(e) {
            let value = e.target.value;
            this.applicantPersonSeleted = [];
            this.applicantPerson.map(item => {
                if (item.applicantName.indexOf(value) > -1) {
                    this.applicantPersonSeleted.push(item);
                }
            });
            this.isOpen = true;
        },
        provinceName(name) {
            let strName;
            switch (name) {
                case "黑龙江省":
                    strName = "黑龙江";
                    break;
                case "内蒙古自治区":
                    strName = "内蒙古";
                    break;
                default:
                    strName = name.substr(0, 2);
            }
            return strName;
        },
        switchBox(num) {
            if (num == 2) {
                if (this.cityIdx != -1) this.areaBOx = 2;
            } else if (num == 1) {
                if (this.provinceIdx != -1) this.areaBOx = 1;
            } else {
                this.areaBOx = 0;
            }
        },
        chooseArea(str, idx, name) {
            if (str == "provinceIdx") {
                this.provinceIdx = idx;
                this.areaBOx = 1;
                this.cityIdx = -1;
                this.countryIdx = -1;
            } else if (str == "cityIdx") {
                this.cityIdx = idx;
                this.areaBOx = 2;
                this.countryIdx = -1;
            } else {
                this.countryIdx = idx;
                this.showAreaBox = 0;
            }
        },
        showAreaBoxFunc() {
            this.showAreaBox = 0;
        },
        tabFn(e) {
            let type = e.target.dataset.type;
            this.type = type;
        },
        loadApplicantName() {
            this.$Http(
                "/order/queryApplicant",
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                let arr = res.data.data;
                arr.map(item => {
                    if (item.applicantType == "applicant_type2") {
                        this.applicantPerson.push(item);
                        this.applicantPersonSeleted.push(item);
                    } else if (item.applicantType == "applicant_type1") {
                        this.applicantCompany.push(item);
                        this.applicantCompanySeleted.push(item);
                    }
                });
            });
        },
        seletedCompany(i) {
            this.company = Object.assign(
                this.company,
                this.applicantCompany[i]
            );
            this.isOpen = false;
        },
        seletedPerson(i) {
            this.person = Object.assign(this.person, this.applicantPerson[i]);
            this.isOpen = false;
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
        // 验证封装
        verification(value, k) {
            let obj = {
                applicantCardNo: "身份证",
                postalcode: "邮政编码",
                businessLicenceArea: "营业执照所在地区",
                businessLicenceAddress: "营业执照详情地址",
                applicantName: "名称",
                contactName: "订单联系人",
                contactPhone: "联系电话",
                contactEmail: "联系邮箱",
                contactTelephone: "座机电话"
            };
            if (!value) {
                popup.created({
                    content: obj[k] + "必须填写",
                    time: 2000
                });
                return false;
            }
            return true;
        },
        // 上传图片
        upload(type) {
            this.$refs[type].click();
            this.$refs[type].onchange = e => {
                let file = e.target.files[0];
                if (!file) {
                    return false;
                }
                console.log(file);
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
                let param = new FormData(); // 创建form对象
                param.append("file", file);
                param.append("userId", this.$store.state.userInfo.userId);
                param.append("type", "upload_file_type_04");
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
        // 提交
        submit() {
            if (this.type == 1) {
                if (this.errors.has("cfcode")) {
                    popup.created({
                        content: "请输入正确邮编",
                        time: 2000
                    });
                    return false;
                }

                if (this.errors.has("cfcontactTel")) {
                    popup.created({
                        content: "请输入正确电话",
                        time: 2000
                    });
                    return false;
                }

                if (this.errors.has("cfcontactMail")) {
                    popup.created({
                        content: "请输入正确邮箱",
                        time: 2000
                    });
                    return false;
                }
            } else {
                if (this.errors.has("fidcardcode")) {
                    popup.created({
                        content: "请输入正确身份证",
                        time: 2000
                    });
                    return false;
                }
                if (this.errors.has("fcode")) {
                    popup.created({
                        content: "请输入正确邮编",
                        time: 2000
                    });
                    return false;
                }

                if (this.errors.has("fcontactTel")) {
                    popup.created({
                        content: "请输入正确电话",
                        time: 2000
                    });
                    return false;
                }

                if (this.errors.has("fcontactMail")) {
                    popup.created({
                        content: "请输入正确邮箱",
                        time: 2000
                    });
                    return false;
                }
            }

            if (this.type == 1) {
                // 企业
                for (let k in this.company) {
                    if (
                        k == "contactTelephone" ||
                        k == "business" ||
                        k == "applicantCardNo"
                    ) {
                        continue;
                    }
                    if (!this.verification(this.company[k], k)) {
                        return false;
                    }
                }
            } else {
                // 个人
                for (let k in this.person) {
                    if (
                        k == "contactTelephone" ||
                        k == "copy" ||
                        k == "personBusiness"
                    ) {
                        continue;
                    }
                    if (!this.verification(this.person[k], k)) {
                        console.log(k);
                        return false;
                    }
                }
            }
            if (this.type == 1) {
                this.person = {
                    applicantName: "",
                    applicantCardNo: "",
                    businessLicenceArea: "",
                    postalcode: "",
                    businessLicenceAddress: "",
                    contactName: "",
                    contactPhone: "",
                    contactEmail: "",
                    contactTelephone: "",
                    copy: "", // 身份证复印件
                    personBusiness: "" // 营业执照
                };
            } else {
                this.company = {
                    applicantName: "",
                    businessLicenceArea: "",
                    postalcode: "",
                    businessLicenceAddress: "",
                    contactName: "",
                    contactPhone: "",
                    contactEmail: "",
                    contactTelephone: "",
                    business: ""
                };
            }
            sessionStorage.setItem(
                "concatInfo",
                JSON.stringify({
                    type: this.type,
                    person: JSON.stringify(this.person),
                    company: JSON.stringify(this.company),
                    uploadFile: JSON.stringify(this.uploadFile)
                })
            );

            this.$router.replace("/order/orderinfo");
        }
    },
    components: {
        toTop
    }
};
</script>
<style scoped>
@import url("../../assets/css/order.css");

/*文件上传*/

.file-upload-container {
    display: none;
}

.file-upload-container.active {
    display: block;
}

.file-upload-container .btns {
    margin-top: 10px;
    text-align: center;
}

.file-upload-container .btns .button {
    border-radius: 2px;
    width: 180px;
    line-height: 32px;
}

.file-upload-container .btns .button.disabled {
    opacity: 0.5;
}

.file-content {
    display: none;
    padding-left: 30px;
    height: 415px;
    overflow: auto;
}

.file-content.active {
    display: block;
}

.file-bottom {
    margin-top: 20px;
}

.file-upload-list .i-star {
    display: inline-block;
    width: 10px;
    padding-top: 2px;
    color: #f08b2f;
    font-size: 12px;
    vertical-align: top;
}

.file-upload-list > label {
    display: inline-block;
    padding-right: 25px;
    width: 145px;
    text-align: right;
    vertical-align: middle;
}

.file-upload-list > label {
}

.file-upload-list > label .icon {
    display: inline-block;
    width: 15px;
    height: 15px;
    vertical-align: middle;
}

.file-upload-list > label span {
    display: inline-block;
    vertical-align: middle;
}

.file-upload-list .l-content {
    display: inline-block;
    vertical-align: middle;
}

.file-upload-list .createpower {
    display: block;
    margin-top: 5px;
    font-size: 12px;
    color: #f08b2f;
    text-decoration: underline;
}

.file-detail-top {
    text-align: right;
    overflow: hidden;
}

.file-detail-top .crumbs {
    float: left;
}

.file-detail-date {
    display: inline-block;
}

.file-detail-date span {
    margin-right: 20px;
}

.file-detail-btn {
    float: right;
}

.file-detail-btn .button {
    margin-left: 10px;
    padding: 1px 10px;
}

.file-detail-img img {
    width: 100%;
}

.file-detail-img {
    margin-top: 20px;
    border: 1px solid #eee;
}

.file-detail-img iframe {
    width: 100%;
    min-height: 900px;
}

.td-content .administrative-division .tab-content .item {
    padding-top: 5px;
}

.order-form-file .showPhotos {
    display: inline-block;
}

.file-upload-btns {
    margin-top: 20px;
    text-align: center;
}

.file-upload-btns .button {
    display: inline-block;
    width: 180px;
    line-height: 32px;
}

.table-search {
    line-height: 30px;
}

.file-info-tips i {
    font-size: 22px;
    color: #f08b2f;
    position: relative;
    top: 2px;
    left: 2px;
    cursor: pointer;
}
</style>
