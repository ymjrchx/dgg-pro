<template>
    <div class="order">
        <ol class="status">
            <li class="atthis">1.填写主体和商标信息</li>
            <li>2.确认订单提交</li>
        </ol>

        <div class="content">
            <div class="title">
                <span>注册主体信息</span>
            </div>

            <dl>
                <dt>注册主体类型</dt>
                <dd>
                    <a class="radio" :class="{'atthis': applyType == 1}" @click="tabFn" data-type="1" href="javascript:;"><i v-show="applyType == 1">√</i> 企业(大陆)</a>
                    <a class="radio" @click="tabFn" :class="{'atthis': applyType == 0}" data-type="0" href="javascript:;"><i v-show="applyType == 0">√</i> 个体工商户(大陆)</a>
                </dd>
            </dl>
            <dl>
                <dt>主体证件</dt>
                <dd>
                    
                    <button @click="isTemp = true">选择模板</button><a class="template" target="_blank" href="/center/template">模板管理</a><div class="tips">请参考样例图：上传小于2M，jpg格式清晰扫描电子文件。</div>
                    
                    <ol>
                        <li class="upload">
                            <img width="120" height="94" src="../../assets/images/order/upload.jpg" alt="上传图片" v-if="!applyZt.businessLicenceFile">
                            <img width="120" height="94" :src="fsHost+applyZt.businessLicenceFile" alt="身份证" v-if="applyZt.businessLicenceFile">
                            <p v-if="applyType == 1">请上传营业执照<br>复印件并盖章</p>
                            <p v-if="applyType == 0">请上传营业执照<br>复印件并签字</p>
                            <button @click="uploadAll('businessLicenceFile')">上传图片</button>
                            <input ref="businessLicenceFile" type="file" accept="image/jpeg,.jpg,application/pdf,.pdf">

                        </li>

                        <li>
                            <b class="sl"></b>
                            <img src="../../assets/images/order/temp_yezz.jpg" alt="示例图片">
                            <a v-if="templateUrl" target="_blank" :href="templateUrl[1].fileUrl" class="fd">放大</a>
                        </li>

                        <li class="upload" v-if="applyType == 0">
                            <p>请上传身份证<br>正反面复印件并签字</p>
                            <img width="120" height="94" src="../../assets/images/order/upload.jpg" alt="上传图片" v-if="!applyZt.applicantCardFile">
                            <img width="120" height="94" :src="fsHost+applyZt.applicantCardFile" alt="身份证" v-if="applyZt.applicantCardFile">
                            <button @click="uploadAll('applicantCardFile')">上传图片</button>
                            <input ref="applicantCardFile" type="file" accept="image/jpeg,.jpg,application/pdf,.pdf">
                        </li>

                        <li v-if="applyType == 0">
                            <b class="sl"></b>
                            <img src="../../assets/images/order/ex-file-sfz.jpg" alt="示例图片">
                            <a v-if="templateUrl" target="_blank" :href="templateUrl[0].fileUrl" class="fd">放大</a>
                        </li>
                    </ol>
                </dd>
            </dl>

            <div class="auto">
                <h2>工商信息<span>（上传营业执照自动填写，请手动添加证件地址省、市（区/县）,否则审核失败）</span></h2>
                <ul>
                    <li>
                        <p>申请主体</p>
                        <input type="text" @blur="applicantNameFn" required="required" v-model="applyZt.applicantName" maxlength="30" placeholder="自动识别，并填写：公司名称（如未识别请手动输入）">
                    </li>
                    <li>
                        <p>证件地址</p>
                        <input type="text" @blur="businessLicenceFn" required="required" v-model="applyZt.businessLicenceAddress" maxlength="30" placeholder="自动识别，并填写：注册地址（如未识别请手动输入）">
                        <span>注*：若注册地址不含省、市（区/县）请手动补足，否则审核失败。</span>
                    </li>
                    <li>
                        <p>社会代码</p>
                        <input type="text" @blur="businessLicenceNoFn" required="required" v-model="applyZt.businessLicenceNo" maxlength="30" placeholder="自动识别，并填写：社会代码（如未识别请手动输入）">
                    </li>
                </ul>
            </div>
            
            <div class="inputsInfo">
               <dl>
                    <dt>邮编</dt>
                    <dd><input type="text" @blur="postcodeFn" v-validate="'required|postcode'" name="cfcode" v-model="applyZt.postalcode" maxlength="6" placeholder="请输入邮政编码"></dd>
                </dl> 
          
               <dl>
                    <dt>姓名</dt>
                    <dd><input  @blur="nameFn" v-model="applyZt.contactName" type="text" maxlength="6" placeholder="请输入您的姓名"></dd>
                </dl> 
                        

                <dl v-if="applyType == 0">
                    <dt>身份证号</dt>
                    <dd><input  type="text" @blur="idcardFn" v-validate="'required|idcard'" name="idcard" v-model="applyZt.applicantCardNo" maxlength="18" placeholder="请输入身份证号"></dd>
                </dl>

                <dl>
                    <dt>手机号</dt>
                    <dd><input v-model="applyZt.contactPhone" @blur="phoneFn" v-validate="'required|phone'" name="cfcontactTel" type="text" placeholder="请输入手机号码" maxlength="11"></dd>
                </dl>
                <dl>
                    <dt>EMAIL</dt>
                    <dd><input type="text" @blur="emailFn" v-model="applyZt.contactEmail" v-validate="'required|email'" name="cfcontactMail" maxlength="30" placeholder="请输入EMAIL地址"></dd>
                </dl>
            </div>
            
            
            
            

            

            <div class="title" @click="brandToggle = !brandToggle">
                <a href="javascript:void(0);"  class="toggle">{{brandToggle?'收缩':'展开'}}</a>
                <span>注册商标信息</span>
            </div>
            
            

            <template v-if="brandToggle">

            <table width="100%" cellspacing="0" cellpadding="0">
                <tbody>
                    <tr>
                        <td width="600"   valign="top">
                            <dl>
                                <dt>商标名称</dt>
                                <dd><input class="sbname" @blur="brandFn" required="required" v-model="name" type="text" maxlength="30" placeholder="请输入：汉字、拼音、英文字母、数字、图形商标即填写图形">
                                    <!-- <button @click="isTest = true">智能评测</button> -->
                                    <span class="wh" style="margin-top: 15px"><img src="../../assets/images/order/icon_wh.png" alt="">本系统仅支持一般类型商标：汉字、拼音、英文字母、数字、图形。不支持三维商标、声音商标、组合商标的申请。
                                        <a v-if="0" @click="isTian2=true" href="javascript:;">怎么填？</a>
                                    </span>
                                </dd>
                            </dl>
                        </td>
                        <td   valign="top">
                            <dl>
                                <dt>商标说明</dt>
                                <dd>
                                    <textarea name="" @blur="explainFn" required="required" v-model="explain" id="" cols="30" rows="10" placeholder="无特殊情况，请直接填：无"></textarea>
                                    <span class="wh"><img src="../../assets/images/order/icon_wh.png" alt="">商标说明即对注册商标的含义说明。
                                        <!-- <a @click="isTian1=true" href="javascript:;">怎么填？</a> -->
                                    </span>
                                </dd>
                            </dl>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <dl class="tuyang">
                                <dt>商标图样</dt>
                                <dd>
                                    <input type="radio" name="ty" id="hb" @click="autoType=1" checked="checked">
                                    <label for="hb" @click="autoType=1">黑白</label> 
                                    <!-- <input @click="autoType=2" name="ty" type="radio" id="zs"> -->
                                    <!-- <label for="zs" @click="autoType=2">着色</label> -->
                                    <span class="wh" style="color: #ff3145"><img src="../../assets/images/order/icon_wh.png" alt="">本系统仅支持，黑白图样提交，请将设计图样，调整为黑白。<br>
                                        黑白商标，注册后不限颜色使用。注册着色商标，注册后只能使用着色图样，<br>故本系统仅提交黑白图样；商标注册成功后，颜色可以自由修改</span> <br>
                                    <div class="tu">
                                        
                                        <img v-if="!createdRes && !uploadRes" width="134" height="94" src="../../assets/images/order/upload.jpg" alt="上传图片">
                                        <img v-if="createdRes" height="94" width="134" class="photo createbrandpic" :src="createdRes.fsHost+createdRes.fsPath">
                                        <img v-if="uploadRes" height="94" width="134" class="photo createbrandpic" :src="uploadRes.fsHost+uploadRes.fsPath">
                                        <p v-if="autoType == 1">{{uploadText}}</p>
                                        <p v-if="autoType == 2">请上传着色图样</p>
                                        <button class="zn" v-if="autoType == 1" @click="createdImg">智能生成</button>
                                        <button @click="upload">上传图片</button>
                                        <input ref="uploadImg" type="file" accept="image/jpeg,image/x-png">
                                    </div>
                                    <span class="wh"><img src="../../assets/images/order/icon_tx.png" alt="">1.JPG图样：宽高等同：400-1500像素，文件小于200kb
                                      <br>2.RGB图样：宽高等同：400-1500像素，文件小于200kb<br> 24位彩色、300dpi分辨率。</span>
                                </dd>
                            </dl>
                        </td>
                        <td valign="top">
                            <dl class="tuyang">
                                <dt>上传委托书</dt>
                                <dd>
                                    <div class="tu">
                                        
                                        <img width="134" height="94" src="../../assets/images/order/upload.jpg" alt="上传图片" v-if="!uploadFile">
                                        <img width="134" height="94" :src="uploadFile.fsHost+uploadFile.fsPath" alt="上传图片" v-if="uploadFile">
                                        <p>请上传委托书</p>
                                        <button class="zn" @click="goDown">下载委托书</button>
                                        <button @click="uploadAll('proxyFile')">上传图片</button>
                                        <input ref="proxyFile" type="file" accept="image/jpeg,.jpg">
                                    </div>
                                    <div class="sltu">
                                        <b class="sl"></b>
                                        <img height="207"  src="../../assets/images/order/temp_wts.jpg" alt="示例图片">
                                        <a v-if="templateUrl" target="_blank" :href="templateUrl[2].fileUrl" class="fd">放大</a>
                                    </div>
                                    <span class="wh"><img src="../../assets/images/order/icon_wh.png" alt="">委托书已自动生成（企业盖章/个体工商户签字）。
                                        <b><br>（提示：请直接下载----打印、签章、扫描彩色---上传即可。）
                                            </b><br>
                                        <span class="wh"><img src="../../assets/images/order/icon_tx.png" alt="">JPG格式，文件小于2M</span>
                                    </span>
                                </dd>
                            </dl>
                        </td>
                    </tr>
                </tbody>
            </table>

            </template>

            <div class="title" @click="serverToggle = !serverToggle">
                <a href="javascript:void(0);"  class="toggle">{{serverToggle?'收缩':'展开'}}</a>
                <span>商标分类选取</span>
            </div>
            
            <template v-if="serverToggle">
            <dl>
                <dt>选择商标类别</dt>
                <dd>
                    <a class="radio" @click="brandType = 0" :class="{'atthis': brandType == 0}" href="javascript:;">智能推荐</a>
                    <a class="radio" :class="{'atthis': brandType == 1}" @click="brandType = 1" href="javascript:;">自助选择</a>
                </dd>
            </dl>
            <dl v-show="brandType == 0">
                <dt>选择所在领域</dt>
                <dd>
                    <div class="c-row-content">
                        <select class="znfirst" @change="changeIndex">
                            <option value="">请选择</option>
                            <option v-if="doMain" v-for="(item,index) in doMain" :selected="doMainIndex === index" :key="item.id" :value="index">{{item.name}}</option>
                        </select>
                        <select class="znsecond" @change="getRecom" v-if="doMain[doMainIndex]">
                            <option value="0">请选择</option>
                            <option v-for="(item) in doMain[doMainIndex].sonArr" :selected="doMainchildIndex == item.id" :key="item.id" :value="item.id">{{item.name}}</option>
                        </select>
                    </div>
                </dd>
            </dl>

            <div class="brandInfo-wrap">
                <!-- start 智能推荐 -->
                <div class="section-recommend" id="section-recommend" v-if="brandType == 0">
                    <h3 class="section-header">
                        <span class="s1">需要注册/保护的分类</span>
                    </h3>
                    <div class="section-bodyer">

                        <div class="article" v-for="(item, index) in selectList" :key="item.id" data-cgid="7956" data-cgnum="29" data-cgname="食品">
                            <h4 class="article-header clearfix">
                                <div class="header-left">
                                    <span class="col-1">第
                                        <i class="color">{{item.number}}</i>类 {{item.name}}</span>

                                    <a class="col-3" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549')" style="cursor: pointer;">
                                        <i class="iconfont icon-xiaoxi"></i>咨询</a>
                                </div>
                                <div class="header-right">
                                    <span class="header-right-secNum">已选择
                                        <i>{{item.sonArr.length}}</i>项</span>
                                    <span class="col-1 price">￥
                                        <i v-if="item.sonArr.length<=10">{{product.officialPrice}}</i>
                                        <i v-if="item.sonArr.length>10">{{product.officialPrice+(item.sonArr.length-10)*30}}</i>
                                    </span>
                                    <a class="col-2 delete" href="javascript:;">
                                        <i @click="deleRecom(index)" class="iconfont icon-shanchu">&#xe610;</i>
                                    </a>
                                </div>
                            </h4>
                            <div class="article-bodyer clearfix">
                                <ul class="list-box clearfix">
                                    <li v-for="(itemChild, childIndex) in item.sonArr" :key="itemChild.id" class="list choosecg">
                                        <span class="text">{{itemChild.number}}类-{{itemChild.name}}</span>
                                        <a class="close" @click="deleThreeType(index, childIndex, itemChild, item)" href="javascript:;" title="关闭">×</a>
                                    </li>
                                </ul>
                                <div class="add-group" @click="addSamllType(item.number, index)">
                                    <a class="btn" href="javascript:;">
                                        <i class="iconfont icon-jiahao" v-if="!item.isAdd">&#xe623;</i>
                                        <i class="iconfont icon-jiahao" v-if="item.isAdd">&#xe692;</i>{{item.isAdd?'收缩':'添加'}}</a>
                                </div>
                                <div class="group clearfix" v-if="item.isAdd">
                                    <ul class="group-left">
                                        <li class="list" :class="{'active': samllTypeIndex['_'+item.number] == i}" v-for="(smallItem,i) in sallTypes['_'+item.number]" @click="getTreeType(item.number, smallItem.number, i, index)" :key="smallItem.id">{{smallItem.number}} {{smallItem.name}}</li>
                                    </ul>
                                    <div class="group-right" v-if="sallTypes['_'+item.number][samllTypeIndex['_'+item.number]]">
                                        <ul class="item active clearfix">
                                            <li class="list" v-for="(threeItem) in sallTypes['_'+item.number][samllTypeIndex['_'+item.number]].sonArr" @click="addTreeType(item.number, index, threeItem)" :class="{'selected': threeItem.checked}" :key="threeItem.id">
                                                <i class="iconfont icon-jiahao">&#xe623;</i>
                                                <span class="text">{{threeItem.number}} {{threeItem.name}}</span>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="add-category" v-if="doMainchildIndex">
                        <a class="btn-add" @click="toggelType" href="javascript:;">
                            <i>+</i>添加商标类别</a>
                        <ul class="list-box clearfix" v-if="isAddType">
                            <li class="list" @click="addType(index)" v-for="(item,index) in trademarkList" :key="item.id" :class="{'selected': item.checked}">第
                                <i class="color">{{item.number}}</i>类-{{item.name}}</li>
                        </ul>
                    </div>
                    <!-- end 继续添加45大类 -->
                </div>
                <!-- end 智能推荐 -->
                <!-- start 自助选择 -->
                <div class="section-selfchoice clearfix" id="section-selfchoice" v-if="brandType == 1">
                    <!-- start 左-->
                    <div class="group-left">
                        <div class="ns-search-container">
                            <div class="myInputGroup">
                                <input type="text" name="keys" v-model="searcKey" class="myInput" @keydown.enter="searchType" placeholder="请输入商品/服务名称进行查询">
                                <button class="myBtn" @click="searchType" id="btn-search">
                                    <i class="iconfont icon-danchucengzhuanhuan">&#xe678;</i>
                                </button>
                            </div>
                        </div>
                        <ul class="group scroll" ref="searchScroll">
                            <li class="list" @click="getAllchild($event, 'two',item.number, index, false)" v-for="(item,index) in trademarkSearchListAll" :key="item.id" :class="{'selected': !item.checked}">
                                <span class="title-first">第
                                    <i class="color">{{item.number}}</i>类 {{item.name}}</span>
                                <div class="title-second" v-show="item.selected" @click="getAllchild($event, 'three',secondItem.number,index,secondIndex)" v-for="(secondItem,secondIndex) in item.sonArr" :key="secondItem.id">
                                    <span class="second-lg">{{secondItem.number}} {{secondItem.name}}</span>
                                    <dl class="second-sm" v-show="secondItem.selected">
                                        <dt class="row" :class="{'selected': threeItem.checked}" @click="addAutoType(item, threeItem, $event)" v-for="(threeItem) in secondItem.sonArr" :key="threeItem.id">
                                            <i class="iconfont icon-jiahao">&#xe623;</i>
                                            <span class="text">{{threeItem.number}} {{threeItem.name}}</span>
                                        </dt>
                                        <dt v-if="!secondItem.isAll" class="get-all-small" @click="getAllchild($event, 'three', secondItem.number,index, secondIndex, true)">展开全部子项</dt>
                                    </dl>
                                </div>
                                <div class="title-second" v-if="!item.isAll" v-show="item.selected" @click="getAllchild($event,'two', item.number,index, false, true)">
                                    <span class="get-all-second">展开全部群组</span>
                                </div>
                            </li>
                            <li class="list" @click="getSonType($event, 'two',item.number, index)" v-for="(item,index) in trademarkListAll" :key="item.id" :class="{'selected': item.selected}">
                                <span class="title-first" data-has-data="0" data-cgid="1">第
                                    <i class="color">{{item.number}}</i>类 {{item.name}}</span>
                                <div class="title-second" v-show="item.selected" @click="getSonType($event, 'three',secondItem.number,index,secondIndex)" v-for="(secondItem,secondIndex) in item.sonArr" :key="secondItem.id">
                                    <span class="second-lg">{{secondItem.number}} {{secondItem.name}}</span>
                                    <dl class="second-sm" v-show="secondItem.selected">
                                        <dt class="row" :class="{'selected': threeItem.checked}" @click="addAutoType(item, threeItem, $event)" v-for="(threeItem) in secondItem.sonArr" :key="threeItem.id">
                                            <i class="iconfont icon-jiahao">&#xe623;</i>
                                            <span class="text">{{threeItem.number}} {{threeItem.name}}</span>
                                        </dt>
                                    </dl>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!-- end 左-->
                    <!-- start 右-->
                    <div class="group-right">
                        <h3 class="title">
                            <span>需要保护的商品\服务项目</span>
                            <div class="group-right-search">
                                <input type="button" class="button" value="清空选项" @click="clearAll()">
                            </div>
                        </h3>
                        <img class="bgimg" v-if="!selectAutoList.length" src="../../assets/images/order/selfchoice-bg.jpg" alt="请点击左侧选择商标类别">
                        <!-- start section -->
                        <div class="section scroll" v-if="selectAutoList.length">
                            <div class="article" v-if="item" v-for="(item, index) in selectAutoList" :key="item.number">
                                <h4 class="article-header clearfix">
                                    <div class="header-left">
                                        <span class="col-1">第{{item.number}}类 {{item.name}}</span>
                                        <a class="col-3" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549')" style="cursor: pointer;">
                                            <i class="iconfont icon-xiaoxi"></i>咨询</a>
                                    </div>
                                    <div class="header-right">
                                        <span class="header-right-secNum red-color">已选择
                                            <i>{{item.sonArr.length}}</i>项</span>
                                        <span class="col-1 price">￥
                                            <i v-if="item.sonArr.length<=10">{{product.officialPrice}}</i>
                                            <i v-if="item.sonArr.length>10">{{product.officialPrice+(item.sonArr.length-10)*30}}</i>
                                        </span>
                                        <a class="col-2 delete" href="javascript:;">
                                            <i @click="deleteType('two', index)" class="iconfont icon-shanchu">&#xe610;</i>
                                        </a>
                                    </div>
                                </h4>
                                <div class="article-bodyer clearfix" v-if="item.sonArr.length">
                                    <ul class="list-box clearfix">
                                        <li v-for="(childItem,childIndex) in item.sonArr" :key="childItem.id" class="list choosecg">
                                            <span class="text">{{childItem.number}} {{childItem.name}}</span>
                                            <a class="close" @click="deleteType('three', index, childIndex)" href="javascript:;" title="关闭">×</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>

                        </div>
                        <!-- end section -->
                    </div>
                    <!-- end 右-->
                </div>
                <!-- end 自助选择 -->
            </div>

            </template>
            
            <no-ssr> 
            <div class="last-pay" v-if="product">
                <ul class="list-box">
                    <li class="row-officer">官费总额：￥
                        <i>{{(officeTotal-allServicePrice).toFixed(2)}}</i>
                    </li>
                    <!-- <li class="row-invoice">发票税费：￥<i>0</i>
              <i class="iconfont icon-img">&#xe61d;</i>
            </li> -->
                    <li class="row-service">服务费：￥
                        <i>{{allServicePrice.toFixed(2)}}</i>
                    </li>
                    <!-- <li class="row-conserve">已为您节省：￥<i>0</i></li> -->
                    <li class="row-sense">应付总额：
                        <em>￥
                            <i>{{officeTotal.toFixed(2)}}</i>
                        </em>
                    </li>
                </ul>
            </div>
            </no-ssr>
            <div class="agree-box">
                <i v-if="!isAgree" @click="isAgree=true" class="iconfont checkbox2" data-id="">&#xe65d;</i>
                <i v-if="isAgree" @click="isAgree=false" class="iconfont checkbox2 checked" data-id="">&#xe612;</i>
                <span @click="isAgree=!isAgree">
                    已阅读并同意
                    <a href="/about/8.html" target="_blank" id="show-contract">《商标服务合同》</a>
                </span>
            </div>

            <div class="fn12 c_gray upbtn center">
                <dl class="dai mr20" @click="submit(0,0)">
                    <img class="tuiimg" src="../../assets/images/order/tj1.png">
                    <dt class="fn22">直接提交商标局</dt>
                </dl>
                <!-- <dl class="dai mr20" @click="submit(0,1)">
                    <img class="tuiimg" src="../../assets/images/order/sbzc_18.png">
                    <dt class="fn22">提交审核</dt>
                </dl> -->
                <dl class="dai mr20" @click="submit(0,2)">
                    <img class="tuiimg" src="../../assets/images/order/tj2.png">
                    <dt class="fn22">先审核后付款</dt>
                </dl>
                
                <p class="saveLoca">若暂不提交，您可先
                    <a href="javascript:void(0);" @click="submit(0, 3)">保存订单</a>
                </p>
            </div>
        </div>
 
        <div class="model" v-if="isTemp">
            <div class="cont">
                <i class="iconfont" @click="isTemp=false">&#xe7fc;</i>
                <div class="tab">
                    <a @click="tabFn" data-type="1" :class="{'atthis': applyType == 1}" href="javascript:;">大陆企业</a>
                    <a @click="tabFn" data-type="0" href="javascript:;" :class="{'atthis': applyType == 0}">大陆个体工商户</a>
                </div>
                <ul class="list" v-show="applyType == 1">
                    <li @click="seletedZt(1, index)" v-for="(item, index) in applicantCompany" :key="item.id">
                        <a href="javascript:;">
                            <span>{{item.templateName}}</span>{{item.applicantName}}</a>
                    </li>
                </ul>
                <ul class="list" v-show="applyType == 0">
                    <li @click="seletedZt(0, index)" v-for="(item, index) in applicantPerson" :key="item.id">
                        <a href="javascript:;">
                            <span>{{item.templateName}}</span>{{item.applicantName}}</a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="model" v-if="isTest">
            <div class="cont test">
                <img v-if="!isEvaluat" src="../../assets/images/order/safe_icon.png" alt="">
                <img v-if="isEvaluat" src="../../assets/images/order/at_icon.png" alt="">
                <input name="evaluat" v-if="!isEvaluat" type="text" v-model="name" maxlength="30" placeholder="请输入商标名称">
                <select name="" id="" v-if="!isEvaluat && allTypesList.length" v-model="typeIndex">
                    <option :value="index" v-for="(item, index) in allTypesList" :key="index">第{{item.number}}类{{item.name}}</option>
                </select>
                <div class="btn" v-if="!isEvaluat">
                    <a class="active" @click="isTest = false" href="javascript:;">取消</a>
                    <a href="javascript:;" @click="passingRate">立即评测</a>
                </div>
                <div v-if="isEvaluat" class="res">智能查近结果：
                    <p class="ml35">商标“
                        <a href="javascript:;" id="simi_tname">{{name}}</a>”在
                        <samp id="simi_tsort">第{{allTypesList[typeIndex].number}}类{{allTypesList[typeIndex].name}}</samp>的注册成功率为：
                        <em id="simi_sucrule">
                            <strong class="c_orange bold">{{parseInt(industryRes.score*100)}}%</strong>
                            <span v-if="industryRes.score>0.80">，风险较低</span>
                            <span v-if="industryRes.score>0.20 && industryRes.score<0.60">，风险高</span>
                            <span v-if="industryRes.score>0.60 && industryRes.score<0.80">，风险低</span>
                            <span v-if="industryRes.score>0 && industryRes.score<0.20">，风险非常高</span>，
                            <a href="http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549" target="blank">点击联系顾问</a>获得解决方案
                            <em class="font14">(评测结果仅供参考)</em>
                        </em>
                        <p v-if="typeof(industryRes.cause) == 'string'">提示：<br>
                            <samp class="c_orange bold">{{industryRes.cause}}</samp>
                        </p>
                </div>
                <div class="btn" v-if="isEvaluat">
                    <a class="active" @click="isTest = false;isEvaluat = false" href="javascript:;">取消</a>
                    <a href="javascript:;" @click="isEvaluat = false; isTest = true">重新评测</a>
                </div>

            </div>
        </div>

        <div class="model" v-if="isNoTen">
            <div class="cont">
                <h1>注意</h1>
                <p>以下类别选择的商品/服务项目不足10项。
                </p>
                <div class="scroll">
                    <p v-for="item in noTens" :key="item.id">第{{item.number}}类 {{item.name}}，已选择{{item.sonArr.length}}个项目;</p>
                </div>
                <div class="btn">
                    <a class="active" @click="isNoTen = false" href="javascript:;">继续修改</a>
                    <a href="javascript:;" @click="submit(true)">{{auditType=='oder_audit_type_04'?'保存':'提交'}}</a>
                </div>
            </div>
        </div>

        <div class="model" v-if="isNoNumber">
            <div class="cont">
                <h1>注意</h1>
                <p>以下类别选择的商品/服务项目必须选择
                    <b class="red">1</b>个三级类别。
                </p>
                <div class="scroll">
                    <p v-for="item in noTens" v-if="item.sonArr.length == 0" :key="item.id">第{{item.number}}类 {{item.name}}</p>
                </div>
                <div class="btn">
                    <a class="active" @click="isNoNumber = false" href="javascript:;">添加</a>
                </div>
            </div>
        </div>

        <loginBox :isShow="showLogin" @close='closeLogin' @backFn="loginBack" />
        <form action="#" ref="proxyFileFrom" method="POST" target="_blank">
            <input type="hidden" name="type" :value="applyType">
            <input type="hidden" name="client" :value="applyZt.applicantName">
            <input type="hidden" name="clientAddress" :value="applyZt.businessLicenceAddress">
            <input type="hidden" name="contactName" :value="applyZt.contactName">
            <input type="hidden" name="contactPhone" :value="applyZt.contactPhone">
            <input type="hidden" name="postalCode" :value="applyZt.postalcode">
            <input type="hidden" name="idCard" :value="applyZt.applicantCardNo">
        </form>
    </div>
</template>

<script>
import axios from "axios";
import popup from "~/components/popup"; //消息
import loginBox from "~/components/loginBox"; //消息
import Cookies from "js-cookie";
export default {
    head() {
        return {
            title: `商标免费注册，商标0元注册-知千秋`,
            meta: [
                {
                    name: "keywords",
                    hid: "keywords",
                    content:
                        "注册商标查询，中国商标官网查询，商标搜索，商标检索,知千秋,权大师"
                },
                {
                    name: "description",
                    hid: "description",
                    content:
                        "免费精准的商标查询平台,中国更全的商标信息库,精准智能的商标相似查询结果,为商标申请人降低商标驳回风险,更快,更准的定位商标注册成功概率。知千秋"
                }
            ],
            link:[
                {
                    rel: "canonical",
                    content: this.baseUrl
                }
            ],
             script:[
                {   
                    src:"https://pwt.zoosnet.net/JS/LsJS.aspx?siteid=PWT96947637&float=1&lng=cn"
                }
            ]
        };
    },
    async asyncData({ params, redirect, error, env, route }) {
        return {
            baseUrl: env.webSite + route.fullPath,
            getUrl: env.baseUrl
        }
    },
    components: {
        loginBox
    },
    data() {
        return {
            brandToggle: true,
            serverToggle: true,
            isTips: false,
            isTemp: false,
            isTest: false,
            isAgree: true,
            isNoTen: false,
            isTian1: false,
            isTian2: false,
            showLogin: false,
            isEvaluat: false,
            fsHost: null,
            typeIndex: 0,
            auditType: '',
            audit: 0, // 订单状态
            allTypesList: [], // 行业类别
            industryRes: null,
            applicantPerson: [],
            applicantCompany: [],
            applyZt: {
                id: null,
                applicantCardAddress: null,
                applicantCardFile: null,
                applicantCardNo: null,
                applicantName: null,
                applicantType: null,
                applicantUserName: null,
                businessLicenceAddress: null,
                businessLicenceArea: null,
                businessLicenceFile: null,
                businessLicenceNo: null,
                contactEmail: null,
                contactName: null,
                contactPhone: null,
                postalcode: null,
                priorityFile: null,
                proxyFile: null
            },
            templateUrl: null,
            isNoNumber: false,
            noZeros: [], // 一项都没有的
            noTens: [], // 不足10个小项的
            type: 1, // 商标类型
            applyType: 1,
            autoType: 1, // 生成类型
            brandType: 0, // 商标类别
            name: null, // 商标名称
            explain: null, // 说明
            doMain: [],
            doMainIndex: null,
            doMainchildIndex: null,
            isAddType: false,
            selectList: [], // 用户选择的服务项
            selectAutoList: [], // 自主选择的服务项
            trademarkList: null, // 45大类
            trademarkListAll: null, //所有分类
            trademarkSearchListAll: null, // 搜索的分类
            sallTypes: {}, // 待添加的小类
            samllTypeIndex: {}, // 小类选中
            uploadRes: null, // 上传图的
            uploadFile: null, // 委托书
            createdRes: null, // 创建图
            searcKey: "",
            product: Cookies.getJSON("product"),
            uploadText: '请上传或生成黑白图样'
        };
    },
    mounted() {
        
        var _hmt = _hmt || [];
        (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?5b01fa44d5882b4e6d2c0b3e2c3fa68e";
        var s = document.getElementsByTagName("script")[0]; 
        s.parentNode.insertBefore(hm, s);
        })();


        this.loadIndustry();
        if (this.$route.query.id) {
            if (!this.$store.state.userInfo.userId) {
                this.showLogin = true;
                return false;
            }
            // this.loadOrderInfo()
        } else {
            if (!this.product) {
                popup.created({
                    content: "产品过期，请重新下单",
                    time: 2000
                });
                window.location.href =
                    "/show/navigation_trademark_register_04&S7741102214293331969.html";
            }
            document.title = `${this.product.name}，商标免费注册，商标0元注册-知千秋`
        }
        
        this.loadApplicantName();
        this.loadTemplateUrl();
        if (this.$route.query.id) {
            Promise.all([this.loadDomain(), this.loadType()]).then(res => {
                this.loadOrderInfo();
            });
        } else {
            Promise.all([this.loadDomain(), this.loadType()]).then(res => {
                if (sessionStorage.getItem("orderData")) {
                    let orderData = JSON.parse(sessionStorage.getItem("orderData")) 
                    this.fsHost = orderData.fsHost
                    this.typeIndex = orderData.typeIndex
                    this.audit = orderData.audit // 订单状态
                    this.industryRes = orderData.industryRes
                    this.applyZt = JSON.parse(orderData.applyZt)
                    this.templateUrl = JSON.parse(orderData.templateUrl)
                    this.type = orderData.type // 商标类型
                    this.applyType = orderData.applyType
                    this.autoType = orderData.autoType // 生成类型
                    this.brandType = orderData.brandType // 商标类别
                    this.name = orderData.name // 商标名称
                    this.explain = orderData.explain // 说明
                    this.doMainIndex = orderData.doMainIndex
                    this.doMainchildIndex = orderData.doMainchildIndex

                    this.selectList = JSON.parse(orderData.selectList) // 用户选择的服务项
                    this.selectList.map((item) => {
                        item.isAdd = false
                    })
                    this.selectAutoList = JSON.parse(orderData.selectAutoList) // 自主选择的服务项
                    this.uploadRes = JSON.parse(orderData.uploadRes) // 上传图的
                    this.uploadFile = JSON.parse(orderData.uploadFile) // 委托书
                    this.createdRes = JSON.parse(orderData.createdRes) // 创建图
                }
                else if(this.$route.query.t == 1){
                    let otherClassify =  Cookies.get("otherMsg") ? JSON.parse(Cookies.get("otherMsg")) : null
                    if(otherClassify){
                       
                        this.brandType = 1
                        this.trademarkListAll.map((item, index) => {
                            if (parseInt(item.number) == otherClassify.type.replace(/[^0-9]/g,'')) {
                                this.$set(this.trademarkListAll[index], 'selected', true)
                                this.getSonType(null, 'two',item.number, index)
                                setTimeout(() => {
                                    this.$refs.searchScroll.scrollTop = (index-1)*32
                                })
                            }
                        })
                        this.name = otherClassify.name;
                        this.createdImg()
                    }
                }
            });
        }
    },
    methods: {
        // 验证信息
        postcodeFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.applyZt.postalcode) {
                popup.created({
                    content: "请输入邮编",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
            if (this.errors.has("cfcode")) {
                popup.created({
                    content: "请输入正确邮编",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },
        // 验证信息
        idcardFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.applyZt.applicantCardNo) {
                popup.created({
                    content: "请输入身份证号",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
            if (this.errors.has("cfcode")) {
                popup.created({
                    content: "请输入正确身份证号",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },
        // 验证信息
        nameFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.applyZt.contactName) {
                popup.created({
                    content: "请输入姓名",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },
        // 验证信息
        phoneFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.applyZt.contactPhone) {
                popup.created({
                    content: "请输入手机号",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
            if (this.errors.has("cfcontactTel")) {
                popup.created({
                    content: "请输入正确手机号",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },
        // 验证信息
        emailFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.applyZt.contactEmail) {
                popup.created({
                    content: "请输入EMAIL地址",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
            if (this.errors.has("cfcontactMail")) {
                popup.created({
                    content: "请输入正确EMAIL地址",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },
        applicantNameFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.applyZt.applicantName) {
                popup.created({
                    content: "请输入申请主体",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },
        businessLicenceFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.applyZt.businessLicenceAddress) {
                popup.created({
                    content: "请输入证件地址",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },
        businessLicenceNoFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.applyZt.businessLicenceNo) {
                popup.created({
                    content: "请输入社会代码",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },
        // 验证信息
        brandFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.name) {
                popup.created({
                    content: "请输入商标名称",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },
        explainFn(e) {
            e.target.className = e.target.className.replace("error", "");
            if (!this.explain) {
                popup.created({
                    content: "请输入商标说明",
                    time: 2000,
                    isFixed: false
                });
                e.target.className = e.target.className + " error";
                return false;
            }
        },

        // 加载订单信息
        loadOrderInfo() {
            this.$Http(
                "/order/findOrderById",
                {
                    orderId: this.$route.query.id,
                    userId: this.$store.state.userInfo.userId
                },
                "get",
                true
            ).then(res => {
                let orderInfo = res.data.data;
                this.product = {};
                this.product.serviceId = orderInfo.order.serviceId;
                this.product.name = orderInfo.order.serviceName;
                this.product.servicePrice = orderInfo.order.servicePrice;
                this.product.officialPrice = orderInfo.order.serviceOfficialPrice;
                (this.product.serviceAttrId = orderInfo.order.serviceAttrId),
                    // this.officeTotal = orderInfo.order.allPrice
                    (this.product.num = orderInfo.order.allNum);
                this.auditType = orderInfo.order.auditType;
                this.applyZt.contactName = orderInfo.order.contactName;
                this.applyZt.contactPhone = orderInfo.order.contactPhone;
                this.applyZt.contactEmail = orderInfo.order.contactEmail;
                this.applyZt.contactTelephone =
                    orderInfo.order.contactTelephone;

                this.applyZt.id = orderInfo.trademarkAndApplicant.templateId;
                this.remark = orderInfo.order.remark;
                this.fsHost = orderInfo.fsHost;

                let trademarkTypes = {
                    trademark_type4: 1,
                    trademark_type5: 2
                };
                let exampleType = [null, "example_type1", "example_type2"];
                let classCreateWay = {
                    class_create_way1: 0,
                    class_create_way2: 1
                };
                let applicantType = { applicant_type2: 0, applicant_type1: 1 };
                this.autoType =
                    trademarkTypes[orderInfo.trademarkAndApplicant.type];
                this.name = orderInfo.trademarkAndApplicant.name;
                this.explain = orderInfo.trademarkAndApplicant.explain;
                // exampleType = exampleType[2],
                if (this.autoType == 1) {
                    this.createdRes = {
                        fsHost: orderInfo.fsHost,
                        fsPath: orderInfo.trademarkAndApplicant.exampleAddress
                    };
                } else {
                    this.uploadRes = {
                        fsHost: orderInfo.fsHost,
                        fsPath: orderInfo.trademarkAndApplicant.exampleAddress
                    };
                }

                this.brandType =
                    classCreateWay[
                        orderInfo.trademarkAndApplicant.classCreateWay
                    ];
                this.doMain.map((item, index) => {
                    if (
                        item.id ==
                        orderInfo.trademarkAndApplicant.suggestedFirstLevel
                    ) {
                        this.doMainIndex = index;
                    }
                });

                this.doMainchildIndex =
                    orderInfo.trademarkAndApplicant.suggestedSecondLevel;

                this.applyType =
                    applicantType[
                        orderInfo.trademarkAndApplicant.applicantType
                    ];
                this.applyZt.applicantName =
                    orderInfo.trademarkAndApplicant.applicantName;

                this.applyZt.applicantCardNo =
                    orderInfo.trademarkAndApplicant.applicantCardNo;

                this.applyZt.businessLicenceArea =
                    orderInfo.trademarkAndApplicant.businessLicenceArea;

                this.applyZt.postalcode =
                    orderInfo.trademarkAndApplicant.postalcode;
                this.applyZt.businessLicenceAddress =
                    orderInfo.trademarkAndApplicant.businessLicenceAddress;
                this.applyZt.applicantCardFile =
                    orderInfo.trademarkAndApplicant.applicantCardFile;
                this.applyZt.businessLicenceFile =
                    orderInfo.trademarkAndApplicant.businessLicenceFile;
                this.applyZt.businessLicenceNo =
                    orderInfo.trademarkAndApplicant.businessLicenceNo;
                this.uploadFile = {
                    fsHost: orderInfo.fsHost,
                    fsPath: orderInfo.trademarkAndApplicant.proxyFile
                };

                let obj = [];
                orderInfo.trademarkClassList.map(item => {
                    if (obj[parseInt(item.classLevel1Code)]) {
                        obj[parseInt(item.classLevel1Code)].sonArr.push({
                            id: item.classLevel3Id,
                            isColour: 0,
                            isRecommend: 0,
                            isShow: null,
                            name: item.classLevel3Name,
                            number: item.classLevel3Code,
                            remark: null,
                            secondId: null,
                            secondNumber: item.classLevel2Code
                        });
                    } else {
                        obj[parseInt(item.classLevel1Code)] = {
                            id: item.classLevel1Id,
                            isColour: 0,
                            isRecommend: 0,
                            isShow: 0,
                            name: item.classLevel1Name,
                            number: item.classLevel1Code,
                            sonArr: [
                                {
                                    id: item.classLevel3Id,
                                    isColour: 0,
                                    isRecommend: 0,
                                    isShow: null,
                                    name: item.classLevel3Name,
                                    number: item.classLevel3Code,
                                    remark: null,
                                    secondId: null,
                                    secondNumber: item.classLevel2Code
                                }
                            ]
                        };
                    }
                });

                if (this.brandType == 0) {
                    let objArr = [];
                    obj.map(item => {
                        objArr.push(item);
                    });
                    this.selectList = objArr;
                } else {
                    this.doMainIndex = null;
                    this.selectAutoList = obj;
                }
            });
        },
        // 商标通过率
        passingRate() {
            if (!this.name) {
                popup.created({
                    content: "请输入商标名称",
                    time: 2000
                });
                return false
            }
            let loading = popup.created({
                type: "loading",
                icon: "&#xe61c",
                content: "测算中..."
            });
            
            sessionStorage.setItem("isFrom", true);
            this.$Http(
                "/predicate/registerPassingRate",
                {
                    brand: this.name,
                    type: this.allTypesList[this.typeIndex].number
                },
                "post",
                false
            )
                .then(res => {
                    loading.close();
                    this.isEvaluat = true;
                    sessionStorage.setItem("isFrom", false);
                    this.industryRes = res.data.data;
                })
                .catch(() => {
                    loading.close();
                    sessionStorage.setItem("isFrom", false);
                });
        },
        // 分类查询
        loadIndustry() {
            this.$Http("/predicate/allTypes", {}, "post", false).then(res => {
                this.allTypesList = res.data.data;
            });
        },
        // 登录成功后回调
        loginBack() {
            this.loadApplicantName();
            // this.loadTemplateUrl();
            // if (this.$route.query.id) {
            //     Promise.all([this.loadDomain(), this.loadType()]).then(res => {
            //         // this.loadOrderInfo()
            //     });
            // } else {
            //     this.loadType();
            //     this.loadDomain();
            // }
        },
        // 关闭登录
        closeLogin() {
            this.showLogin = false;
        },
        // 动态的tab切换
        tabFn(e) {
            let type = e.target.dataset.type;
            this.applyType = type;
        },
        // 选择模板
        seletedZt(type, index) {
            if (type) {
                this.applyZt = this.applicantCompany[index];
            } else {
                this.applyZt = this.applicantPerson[index];
            }
            // 取消所有错误提示
            let inputs = document.getElementsByTagName("input");
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].className = inputs[i].className.replace("error", "");
            }

            this.isTemp = false;
        },
        // 加载模板
        loadApplicantName() {
            if (this.$store.state.userInfo.userId) { 
                this.$Http(
                    "/applicantTemplate/find",
                    { createrId: this.$store.state.userInfo.userId },
                    "get",
                    true
                ).then(res => {
                    let arr = res.data.data;
                    this.fsHost = res.data.data.fsHost;
                    this.applicantPerson = arr.applicant_type2;
                    this.applicantCompany = arr.applicant_type1;
                });
            }
        },
        // 选择分类
        selectedType(e, name) {
            if (e.target.nodeName === "LABEL" || e.target.nodeName === "I") {
                this[name] = e.target.dataset.type;
            }
            if (e.target.nodeName === "A") {
                this[name] = e.target.dataset.type;
            }
        },
        // 加载推荐
        loadDomain() {
            return this.$Http(
                "/industry/queryDomain",
                {  },
                "get",
                true
            ).then(res => {
                this.doMain = res.data.data;
            });
        },
        // 加载所有分类
        loadType(index) {
            return this.$Http(
                "/industry/queryTrademarkList",
                {  },
                "get"
            ).then(res => {
                this.trademarkList = res.data.data;
                this.trademarkListAll = res.data.data;
            });
        },
        // 搜索分类
        searchType() {
            this.$refs.searchScroll.scrollTop = 0
            if (this.searcKey == '') {
                this.loadType();
                this.trademarkSearchListAll = [];
                return false;
            }
            this.$Http(
                "/industry/queryTrademarkSearchList",
                {
                    searcKey: this.searcKey
                },
                "get"
            ).then(res => {
                //this.trademarkList = res.data.data
                let arr = res.data.data;
                if (arr.length == 0) {
                    popup.created({
                        content: "未找到相关类别",
                        time: 2000
                    });
                    return false;
                }
                arr.map(item => {
                    item.selected = true;
                    this.trademarkListAll.map((childItem, childIndex) => {
                        if (item.id === childItem.id) {
                            this.trademarkListAll.splice(childIndex, 1);
                        }
                    });
                    this.selectAutoList.map((childItem, childIndex) => {
                        if (childItem && item.id === childItem.id) {
                            childItem.sonArr.map((threeItem, threeIndex) => {
                                item.sonArr.map(fourItem => {
                                    fourItem.sonArr.map(fiveItem => {
                                        if (threeItem.id === fiveItem.id) {
                                            fiveItem.checked = true;
                                            this.$set(this.selectAutoList[childIndex].sonArr, threeIndex, fiveItem)
                                        }
                                    });
                                });
                            });
                        }
                    });
                    item.sonArr.map(childItem => {
                        childItem.selected = true;
                    });
                });
                this.trademarkSearchListAll = arr;
            });
        },
        changeIndex(e) {
            if (e.target.value != null) {
                this.doMainIndex = parseInt(e.target.value);
            } else {
                this.doMainIndex = null;
            }
        },

        // 添加小类
        addSamllType(number, index) {
            if (this.sallTypes["_" + number]) {
                this.$set(
                    this.selectList[index],
                    "isAdd",
                    !this.selectList[index].isAdd
                );
                return false;
            }
            this.$Http(
                "/industry/queryTrademarkParticularList",
                {
                    userId: this.$store.state.userInfo.userId,
                    number: number,
                    numberType: "two"
                },
                "get",
                true
            ).then(res => {
                this.$set(this.sallTypes, "_" + number, res.data.data);
                this.$set(
                    this.selectList[index],
                    "isAdd",
                    !this.selectList[index].isAdd
                );
            });
        },
        // 获取三级分类
        getTreeType(two, type, index, i) {
            this.$set(this.samllTypeIndex, "_" + two, index);
            if (this.sallTypes["_" + two][index].sonArr) {
                return false;
            }
            this.$Http(
                "/industry/queryTrademarkParticularList",
                {
                    userId: this.$store.state.userInfo.userId,
                    number: type,
                    numberType: "three"
                },
                "get",
                true
            ).then(res => {
                let arr = res.data.data;
                // arr.map(item => {
                //     this.selectList[i].sonArr.map(childItem => {
                //         if (item.id === childItem.id) {
                //             item.checked = true;
                //         }
                //     });
                // });

                arr.map(item => {
                    this.selectList.map((childItem, index) => {
                        childItem.sonArr.map((threeItem, childrenIndex) => {
                            if (childItem && item.id == threeItem.id) {
                                item.checked = true;
                                console.log(this.selectList[index]);
                                this.$set(
                                    this.selectList[index].sonArr,
                                    childrenIndex,
                                    item
                                );
                            }
                        });
                    });
                });
                this.$set(this.sallTypes["_" + two][index], "sonArr", arr);
            });
        },
        // 获取推荐
        getRecom(e) {
            this.doMainchildIndex = e.target.value;
            this.$Http(
                "/industry/queryCommodity",
                { id: e.target.value },
                "get",
                true
            ).then(res => {
                this.selectList = res.data.data;
            });
        },
        toggelType() {
            this.isAddType = !this.isAddType;
        },
        // 删除推荐
        deleRecom(index) {
            this.selectList.splice(index, 1);
        },
        // 删除小类
        deleThreeType(index, childIndex, itemChild, item) {
            if (this.sallTypes["_" + item.number]) {
                let arr = this.sallTypes["_" + item.number][
                    this.samllTypeIndex["_" + item.number]
                ].sonArr;
                arr.map(childItem => {
                    if (itemChild.id == childItem.id) {
                        childItem.checked = false;
                    }
                });
            }
            this.selectList[index].sonArr.splice(childIndex, 1);
        },
        // 添加大类
        addType(index) {
            if (this.trademarkList[index].checked) {
                return false;
            }
            let obj = Object.assign({}, this.trademarkList[index]);
            obj.sonArr = [];
            this.selectList.push(obj);
        },
        // 添加小类
        addTreeType(two, index, item) {
            let arr = this.selectList[index].sonArr;
            if (item.checked) {
                return false;
            }
            item.checked = true;
            arr.push(item);
            this.$set(this.selectList[index], "sonArr", arr);
        },
        // 创建图片
        createdImg() {
            if (!this.name) {
                popup.created({
                    content: "请输入商标名称",
                    time: 2000
                });
                return false;
            }
            this.$Http(
                "/file/createTrademark",
                {
                    text: this.name,
                    imgWidth: 500,
                    imgHeight: 500
                },
                "get",
                true
            ).then(res => {
                this.uploadText = '自动生成：成功！'
                this.createdRes = res.data.data;
                this.uploadRes = null;
            });
        },
        // 上传图片
        uploadAll(type) {
            this.$refs[type].click();
            this.$refs[type].onchange = e => {
                let file = e.target.files[0];
                if (!file) {
                    return false;
                }
                if (type == "proxyFile") {
                    if (file.size > 2097152) {
                        popup.created({
                            content: "上传文件不能大于2M",
                            time: 2000
                        });
                        return false;
                    }
                } else {
                    if (file.size > 2097152) {
                        popup.created({
                            content: "上传文件不能大于2M",
                            time: 2000
                        });
                        return false;
                    }
                }

                if (file.type != "image/jpeg") {
                    popup.created({
                        content: "只能上传.jpg",
                        time: 2000
                    });
                    return false;
                }
                let param = new FormData(); // 创建form对象
                param.append("file", file);
                param.append("type", "upload_file_type_04");
                let loading = popup.created({
                    type: "loading",
                    icon: "&#xe61c",
                    content: "上传中..."
                });
                axios
                    .post(`${this.$baseUrl}/file/singleUpload`, param, {
                        headers: {
                            "Content-Type": "multipart/form-data",
                            token: Cookies.get("token")
                        }
                    })
                    .then(res => {
                        loading.close();
                        if (res.data.code == 0) {
                            if (type == "proxyFile") {
                                popup.created({
                                    content: "上传成功",
                                    time: 2000
                                });
                                this.uploadFile = res.data.data;
                            } else {
                                this.fsHost = res.data.data.fsHost;
                                this.$set(
                                    this.applyZt,
                                    type,
                                    res.data.data.fsPath
                                );
                                // 判断 营业执照还是身份证
                                let loading2 = popup.created({
                                    type: "loading",
                                    icon: "&#xe61c",
                                    content: "识别中..."
                                });
                                if (type == "businessLicenceFile") {
                                    this.$Http(
                                        "/imgidentify/license",
                                        {
                                            filePath:
                                                res.data.data.fsHost +
                                                res.data.data.fsPath
                                        },
                                        "get",
                                        false
                                    )
                                        .then(res2 => {
                                            loading2.close();
                                            this.applyZt.applicantName =
                                                res2.data.data.org;
                                            this.applyZt.businessLicenceAddress =
                                                res2.data.data.address;
                                            this.applyZt.businessLicenceNo =
                                                res2.data.data.no;
                                        })
                                        .catch(() => {
                                            loading2.close();
                                        });
                                } else if (type == "applicantCardFile") {
                                    this.$Http(
                                        "/imgidentify/idCard",
                                        {
                                            filePath:
                                                res.data.data.fsHost +
                                                res.data.data.fsPath,
                                            idCardSide: "front"
                                        },
                                        "get",
                                        false
                                    )
                                        .then(res2 => {
                                            loading2.close();
                                            this.applyZt.applicantCardNo =
                                                res2.data.data.no;
                                        })
                                        .catch(() => {
                                            loading2.close();
                                        });
                                }
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
        loadTemplateUrl() {
            this.$Http(
                "/template/findTemplateUrl",
                {  },
                "get",
                true
            ).then(res => {
                this.templateUrl = res.data.data;
            });
        },
        // 上传图片
        upload() {
            this.$refs.uploadImg.click();
            this.$refs.uploadImg.onchange = e => {
                let file = e.target.files[0];
                if (!file) {
                    return false;
                }
                if (file.size > 204800) {
                    popup.created({
                        content: "上传文件不能大于200kb",
                        time: 2000
                    });
                    return false;
                }
                if (file.type != "image/jpeg") {
                    popup.created({
                        content: "只能上传.jpg文件",
                        time: 2000
                    });
                    return false;
                }

                let param = new FormData(); // 创建form对象
                param.append("file", file);
                param.append("type", "upload_file_type_02");
                let loading = popup.created({
                    type: "loading",
                    icon: "&#xe61c",
                    content: "上传中..."
                });
                axios
                    .post(`${this.$baseUrl}/file/singleUpload`, param, {
                        headers: {
                            "Content-Type": "multipart/form-data",
                            token: Cookies.get("token")
                        }
                    })
                    .then(res => {
                        loading.close();
                        if (res.data.code == 0) {
                            popup.created({
                                content: "上传成功",
                                time: 2000
                            });
                            this.uploadText = '请上传或生成黑白图样'
                            this.uploadRes = res.data.data;
                            this.createdRes = null;
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
        // 自主选择
        // 获取剩下的收缩项
        getAllchild(e, type, number, index, childIndex, force) {
            e.stopPropagation();
            if (childIndex === false) {
                if (this.trademarkSearchListAll[index].sonArr && !force) {
                    this.trademarkSearchListAll[index].selected = !this
                        .trademarkSearchListAll[index].selected;
                    return false;
                }
            } else {
                if (
                    this.trademarkSearchListAll[index].sonArr[childIndex]
                        .sonArr &&
                    !force
                ) {
                    this.trademarkSearchListAll[index].sonArr[
                        childIndex
                    ].selected = !this.trademarkSearchListAll[index].sonArr[
                        childIndex
                    ].selected;
                    return false;
                }
            }
            this.$Http(
                "/industry/queryTrademarkParticularList",
                {
                    number: number,
                    numberType: type
                },
                "get",
                true
            ).then(res => {
                if (type == "two") {
                    this.$set(
                        this.trademarkSearchListAll[index],
                        "selected",
                        true
                    );
                    // 保存之前的
                    this.$set(
                        this.trademarkSearchListAll[index],
                        "oldsonArr",
                        this.trademarkSearchListAll[index].sonArr
                    );
                    let arr = res.data.data;
                    this.$set(
                        this.trademarkSearchListAll[index],
                        "sonArr",
                        arr
                    );
                    this.$set(
                        this.trademarkSearchListAll[index],
                        "isAll",
                        true
                    );
                }
                if (type == "three") {
                    this.$set(
                        this.trademarkSearchListAll[index].sonArr[childIndex],
                        "selected",
                        true
                    );
                    // 保存之前的
                    this.$set(
                        this.trademarkSearchListAll[index].sonArr[childIndex],
                        "oldsonArr",
                        this.trademarkSearchListAll[index].sonArr[childIndex]
                            .sonArr
                    );
                    let arr = res.data.data;
                    arr.map(item => {
                    this.selectAutoList.map((childItem, index) => {
                        childItem.sonArr.map((threeItem, childrenIndex) => {
                            if (childItem && item.id == threeItem.id) {
                                item.checked = true;
                                this.$set(
                                    this.selectAutoList[index].sonArr,
                                    childrenIndex,
                                    item
                                );
                            }
                        });
                    });
                });
                    this.$set(
                        this.trademarkSearchListAll[index].sonArr[childIndex],
                        "sonArr",
                        arr
                    );
                    this.$set(
                        this.trademarkSearchListAll[index].sonArr[childIndex],
                        "isAll",
                        true
                    );
                }
            });
        },
        // 获取下级分类
        getSonType(e, type, number, index, childIndex) {
            e && e.stopPropagation();
            if (isNaN(childIndex)) {
                if (this.trademarkListAll[index].sonArr) {
                    this.trademarkListAll[index].selected = !this
                        .trademarkListAll[index].selected;
                    return false;
                }
            } else {
                if (this.trademarkListAll[index].sonArr[childIndex].sonArr) {
                    this.trademarkListAll[index].sonArr[
                        childIndex
                    ].selected = !this.trademarkListAll[index].sonArr[
                        childIndex
                    ].selected;
                    return false;
                }
            }

            return this.$Http(
                "/industry/queryTrademarkParticularList",
                {
                    userId: this.$store.state.userInfo.userId,
                    number: number,
                    numberType: type
                },
                "get",
                true
            ).then(res => {
                if (type == "two") {
                    this.$set(this.trademarkListAll[index], "selected", true);
                    this.$set(
                        this.trademarkListAll[index],
                        "sonArr",
                        res.data.data
                    );
                }
                if (type == "three") {
                    this.$set(
                        this.trademarkListAll[index].sonArr[childIndex],
                        "selected",
                        true
                    );
                    let arr = res.data.data;
                    arr.map(item => {
                        this.selectAutoList.map((childItem, index) => {
                            if (childItem) {
                               childItem.sonArr.map((threeItem, childrenIndex) => {
                                    if (childItem && item.id == threeItem.id) {
                                        item.checked = true;
                                        console.log(threeItem.id);
                                        this.$set(
                                            this.selectAutoList[index].sonArr,
                                            childrenIndex,
                                            item
                                        );
                                    }
                                }); 
                            }
                        });
                    });
                    this.$set(
                        this.trademarkListAll[index].sonArr[childIndex],
                        "sonArr",
                        res.data.data
                    );
                }
            });
        },
        // 选择添加小项
        addAutoType(item, childItem, e) {
            e.stopPropagation();
            if (childItem.checked) {
                return false;
            }
            let arr = [];
            if (this.selectAutoList[parseInt(item.number)]) {
                arr = this.selectAutoList[parseInt(item.number)].sonArr;
            }
            childItem.checked = true;
            arr.push(childItem);
            this.$set(this.selectAutoList, parseInt(item.number), {
                id: item.id,
                isColour: item.isColour,
                isRecommend: item.isRecommend,
                isShow: item.isShow,
                name: item.name,
                number: item.number,
                remark: item.remark,
                sonArr: arr
            });
        },
        goDown() {
            let inputs = {
                postalcode: "请输入邮政编码",
                applicantCardNo: "请输入身份证号码",
                contactName: "请输入联系人",
                contactPhone: "请输入联系电话",
                applicantName: "请输入申请主体",
                businessLicenceAddress: "请输入证件地址",
            }
            
            for (let i in inputs) {
                if (!((i == 'applicantCardNo' || i == 'applicantCardFile') && this.applyType == 1)) {
                    if (!this.validate(i, inputs[i])) {
                        return false
                    }
                }
            }
            if (this.applyZt.businessLicenceAddress.indexOf('市') < 0 || (this.applyZt.businessLicenceAddress.indexOf('省') < 0 && !this.applyZt.businessLicenceAddress.match(/天津市|上海市|北京市|重庆市/))) {
                popup.created({
                    content: '地址必须包含省、市（区/县）（请完善地址后下载委托书）',
                    time: 2000
                });
                return false
            }
            let froms = this.$refs.proxyFileFrom
            froms.action = this.getUrl+'export/proxy'
            froms.submit()
        },
        // 删除小类
        deleteType(type, index, childIndex) {
            if (type == "two") {
                if (this.selectAutoList[index]) {
                    this.selectAutoList[index].sonArr.map(childItem => {
                        childItem.checked = false;
                    });
                }
                this.selectAutoList.splice(index, 1);
            }
            if (type == "three") {
                let arr = this.selectAutoList[index].sonArr;
                arr[childIndex].checked = false;
                arr.splice(childIndex, 1);
                this.$set(this.selectAutoList[index], "sonArr", arr);
            }
        },
        // 清空所有
        clearAll() {
            this.selectAutoList.map(item => {
                if (item) {
                    item.sonArr.map(childItem => {
                        childItem.checked = false;
                    });
                }
            });
            this.selectAutoList = [];
        },

        // 验证
        validate (name, tips) {
            if (!this.applyZt[name]) {
                popup.created({
                    content: tips,
                    time: 2000
                });
                return false;
            }
            return true
        },
        //提交
        submit(type, audit) {
            let inputs = {
                businessLicenceFile: "请先上传营业执照",
                applicantCardFile: "请上传身份证",
                postalcode: "请输入邮政编码",
                applicantCardNo: "请输入身份证号码",
                contactName: "请输入联系人",
                contactPhone: "请输入联系电话",
                contactEmail: "请输入邮箱",
                applicantName: "请输入申请主体",
                businessLicenceAddress: "请输入证件地址",
                businessLicenceNo: "请输入社会代码",
            }
            
            for (let i in inputs) {
                if (!((i == 'applicantCardNo' || i == 'applicantCardFile') && this.applyType == 1)) {
                    if (!this.validate(i, inputs[i])) {
                        return false
                    }
                }
            }
            
            if (this.errors.has("cfcode")) {
                popup.created({
                    content: "请输入正确邮编",
                    time: 2000
                });
                return false;
            }

            if (this.errors.has("idcard")) {
                popup.created({
                    content: "请输入正确身份证号码",
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
            if (this.errors.has("fidcardcode")) {
                popup.created({
                    content: "请输入正确身份证",
                    time: 2000
                });
                return false;
            }

            if (!this.name) {
                popup.created({
                    content: "商标名称不能为空",
                    time: 2000
                });
                return false;
            }
            if (!this.createdRes && !this.uploadRes) {
                popup.created({
                    content: "请先上传或生成商标图样",
                    time: 2000
                });
                return false;
            }


            if (!this.explain) {
                popup.created({
                    content: "必须输入商标说明",
                    time: 2000
                });
                return false;
            }

            if (!this.uploadFile) {
                popup.created({
                    content: "请必须上传委托书",
                    time: 2000
                });
                return false;
            }

            if (!parseInt(this.officeTotal)) {
                popup.created({
                    content: "请先选择商标类别",
                    time: 2000
                });
                return false;
            }

            if (!this.isAgree) {
                popup.created({
                    content: "请阅读并勾选商标服务合同",
                    time: 2000
                });
                return false;
            }

            // 不足10个小项
            this.noTens = [];
            this.noZeros = [];
            if (this.brandType) {
                this.selectAutoList.map(item => {
                    if (item) {
                        if (item.sonArr.length < 10) {
                            this.noTens.push(item);
                            if (item.sonArr.length == 0) {
                                this.noZeros.push(item);
                            }
                        }
                    }
                });
            } else {
                this.selectList.map(item => {
                    if (item.sonArr.length < 10) {
                        this.noTens.push(item);
                        if (item.sonArr.length == 0) {
                            this.noZeros.push(item);
                        }
                    }
                });
            }

            if (this.brandType == 1) {
                this.selectList = [];
            } else {
                this.selectAutoList = [];
            }
            if (this.type != 1) {
                this.createdRes = null;
            } else {
                
            }
            let auditType = null;
            if (audit || audit === 0) {
                this.audit = audit;
            }
            if (this.audit == 0) {
                auditType = "oder_audit_type_01";
            }
            if (this.audit == 1) {
                auditType = "oder_audit_type_02";
            }
            if (this.audit == 2) {
                auditType = "oder_audit_type_03";
            }
            if (this.audit == 3) {
                auditType = "oder_audit_type_04";
            }

            if (!type && this.noZeros.length) {
                this.isNoNumber = true;
                return false;
            }

            if (!type && this.noTens.length) {
                this.isNoTen = true;
                this.auditType = auditType
                return false;
            }

            // 数据缓存本地



            let arr = [null, "trademark_type4", "trademark_type5"];

            let exampleType = [null, "example_type1", "example_type2"];
            let classCreateWay = ["class_create_way1", "class_create_way2"];
            let applicantType = ["applicant_type2", "applicant_type1"];
            let codes = [];
            this.selectList.map(item => {
                item.sonArr.map(childItem => {
                    codes.push({
                        classLevel1Id: item.id,
                        classLevel1Code: item.number,
                        classLevel1Name: item.name,
                        classLevel3Id: childItem.id,
                        classLevel2Code: childItem.secondNumber,
                        classLevel3Code: childItem.number,
                        classLevel3Name: childItem.name
                    });
                });
            });
            this.selectAutoList.map(item => {
                if (item) {
                    item.sonArr.map(childItem => {
                        codes.push({
                            classLevel1Id: item.id,
                            classLevel1Code: item.number,
                            classLevel1Name: item.name,
                            classLevel2Code: childItem.secondNumber,
                            classLevel3Id: childItem.id,
                            classLevel3Code: childItem.number,
                            classLevel3Name: childItem.name
                        });
                    });
                }
            });

            
            let obj = {
                orderJson: JSON.stringify({
                    invoiceFee: 0,
                    id: this.$route.query.id,
                    userId: this.$store.state.userInfo.userId?this.$store.state.userInfo.userId: null,
                    serviceId: this.product.serviceId,
                    serviceAttrId: this.product.serviceAttrId
                        ? this.product.serviceAttrId
                        : null,
                    allPrice: this.officeTotal,
                    allNum: this.product.num,
                    auditType: auditType,
                    contactName: this.applyZt.contactName,
                    contactPhone: this.applyZt.contactPhone,
                    contactEmail: this.applyZt.contactEmail,
                    contactTelephone: this.applyZt.contactTelephone,
                    remark: null
                }),
                trademarkAndApplicantJson: JSON.stringify({
                    type: arr[this.autoType],
                    templateId: this.applyZt.id,
                    name: this.name,
                    explain: this.explain,
                    exampleType: exampleType[2],
                    exampleAddress: this.createdRes
                        ? this.createdRes.fsPath
                        : this.uploadRes.fsPath,
                    classCreateWay: classCreateWay[this.brandType],
                    suggestedFirstLevel: this.doMainIndex
                        ? this.doMain[parseInt(this.doMainIndex)].id
                        : null,
                    suggestedSecondLevel: this.doMainchildIndex,
                    applicantType: applicantType[parseInt(this.applyType)],
                    applicantName: this.applyZt.applicantName,
                    applicantCardNo:
                        parseInt(this.applyType) == 0
                            ? this.applyZt.applicantCardNo
                            : null,
                    businessLicenceArea: this.applyZt.businessLicenceArea,
                    postalcode: this.applyZt.postalcode,
                    businessLicenceAddress: this.applyZt.businessLicenceAddress,
                    applicantCardFile:
                        parseInt(this.applyType) == 0
                            ? this.applyZt.applicantCardFile
                            : null,
                    businessLicenceFile: this.applyZt.businessLicenceFile,
                    businessLicenceNo: this.applyZt.businessLicenceNo,
                    proxyFile: this.uploadFile.fsPath,
                    priorityFile: null
                }),
                trademarkClassListJson: JSON.stringify(codes)
            };

            this.isNoTen = false;

            if (!this.$store.state.userInfo.userId) {
                sessionStorage.setItem('orderData', JSON.stringify({
                    fsHost: this.fsHost,
                    typeIndex: this.typeIndex,
                    audit: this.audit, // 订单状态
                    industryRes: this.industryRes,
                    applyZt: JSON.stringify(this.applyZt),
                    templateUrl: JSON.stringify(this.templateUrl),
                    type: this.type, // 商标类型
                    applyType: this.applyType,
                    autoType: this.autoType, // 生成类型
                    brandType: this.brandType, // 商标类别
                    name: this.name, // 商标名称
                    explain: this.explain, // 说明
                    doMainIndex: this.doMainIndex,
                    doMainchildIndex: this.doMainchildIndex,
                    selectList: JSON.stringify(this.selectList), // 用户选择的服务项
                    selectAutoList: JSON.stringify(this.selectAutoList), // 自主选择的服务项
                    uploadRes: JSON.stringify(this.uploadRes), // 上传图的
                    uploadFile: JSON.stringify(this.uploadFile), // 委托书
                    createdRes: JSON.stringify(this.createdRes) // 创建图
                }))
                this.showLogin = true
                return false
            }
            
            sessionStorage.setItem("isFrom", true);
            
            if (this.$route.query.id) {
                this.$Http("/order/updateAutoOrder", obj, "post", true).then(
                    res => {
                        sessionStorage.setItem("isFrom", false);
                        // Cookies.remove("product");

                        if (this.audit == 0) {
                            // Cookies.remove("product");
                            this.$router.replace("/order/pay/" + res.data.data);
                        } else {
                            this.$router.replace(
                                "/order/examine/" + res.data.data
                            );
                        }
                    }
                );
            } else {
                this.$Http("/order/saveAutoOrder2", obj, "post", true).then(
                    res => {
                        sessionStorage.setItem("isFrom", false);
                        sessionStorage.removeItem('orderData')
                        if (this.audit == 0) {
                            // Cookies.remove("product");
                            this.$router.replace("/order/pay/" + res.data.data);
                        } else {
                            this.$router.replace(
                                "/order/examine/" + res.data.data
                            );
                        }
                    }
                );
            }
        }
    },
    computed: {
        officeTotal() {
            let total = this.allServicePrice;
            if (parseInt(this.brandType)) {
                this.selectAutoList.map(item => {
                    if (item) {
                        if (item.sonArr.length <= 10) {
                            total += this.product.officialPrice
                        } else {
                            total += this.product.officialPrice + (item.sonArr.length-10) * 30;
                        }
                    }
                });
                return total
            }
            this.selectList.map(item => {
                if (item.sonArr.length <= 10) {
                    total += this.product.officialPrice
                } else {
                    total += this.product.officialPrice + (item.sonArr.length-10) * 30;
                }
            });
            return total
        },
        allServicePrice () {
            let L = 0
            if (parseInt(this.brandType)) {
                this.selectAutoList.map(item => {
                    if (item) {
                        ++L
                    }
                });
                return (L?L:1)*this.product.servicePrice
                 
            }
            this.selectList.map(item => {
                if (item) {
                    ++L
                }
            });
            return (L?L:1)*this.product.servicePrice
        }
    },
    watch: {
        selectList() {
            this.trademarkList.map(item => {
                item.checked = false;
                this.selectList.map(childItem => {
                    if (item.id === childItem.id) {
                        item.checked = true;
                    }
                });
            });
        }
    }
};
</script>

<style scoped lang="stylus">
.order {
    width: 1200px;
    margin: 0 auto;

    .status {
        margin: 30px 0;
        overflow: hidden;
        position: relative;

        &:after {
            content: '';
            position: absolute;
            width: 25px;
            height: 40px;
            left: 50%;
            top: 0;
            //background: transparent url('../../assets/images/order/bg_arr.png') left top no-repeat;
        }

        li {
            float: left;
            width: 50%;
            text-align: center;
            font: normal 16px / 40px 'microsoft yahei';
            color: #fff;
            background: #a2b8ce;

            &.atthis {
                background: #fd7d22;
            }
        }
    }

    .content {
        background: #fff;
        // border: 1px solid #e7e7e7;
        padding: 0 0 30px;
        margin-bottom: 30px;
        position: relative;
        .inputsInfo{
            float right;
            // padding-top 20px;
            margin-bottom 20px;
        }
        .auto {
            // position: absolute;
            width: 600px;
            float left;
            margin-bottom 30px;
            // height: 387px;
            // width: 700px;
            margin-top: 15px;
            background: #f7f7f7;
            box-sizing: border-box;
            border 1px solid #eee;
            padding: 10px 20px 0;

            h2 {
                font: normal 16px / 40px 'microsoft yahei';
                color: #333;
                border-bottom: 1px solid #c7dcf1;
                background #f7f7f7;
                padding-left 15px;
                span{
                    font: normal 12px / 18px 'microsoft yahei';
                    padding: 5px 0;
                    text-align center;
                    color #ff3145 
                    padding-left: 15px;
                }
            }

            >p {
                font: normal 14px / 18px 'microsoft yahei';
                padding: 10px 0;
                text-align center;
                color #fd7d22
            }

            ul {
                li {
                    margin: 15px 0;
                    overflow hidden;
                    p {
                        font: normal 14px / 40px 'microsoft yahei';
                        color: #666;
                        float left;
                        width 100px;
                        text-align right;
                    }

                    input {
                        float left
                        width: 370px;
                        height: 36px;
                        line-height: 36px;
                        box-sizing: border-box;
                        padding: 0 10px;
                        color: #666;
                        font-size: 14px;
                        border: 1px solid #c7dcf1;
                        background #f6f6f6;
                        margin-left 20px 
                        margin-right 10px
                    }

                    >span {
                        color: #ff3145;
                        padding-top 10px;
                        display inline-block;
                        font: normal 12px / 18px 'microsoft yahei';
                        margin-left: 20px;
                    }
                }
            }

            .note {
                position: absolute;
                left: 25px;
                right: 25px;
                bottom: 20px;
                font: normal 12px / 20px 'microsoft yahei';
                color: #666;

                a {
                    color: #fd7d22;
                }
            }
        }

        .tips {
            font-size: 12px;
            color: #666;
            background: transparent url('../../assets/images/order/icon_tx.png') left center no-repeat;
            padding-left: 20px;
            line-height: 16px;
            margin: 10px 0;
        }

        .title {
            line-height: 50px;
            margin-top: 30px;
            height: 50px;
            background #eee;
            cursor pointer;
            clear both;
            // border-bottom: 1px solid #eee;
            a {
                float:right;
                color: #999;
                font-size 14px;
                font-weight bold;
                margin-right 15px;
            }
            span {
                float: left;
                line-height: 48px;
                color: #fd7d22;
                font-size: 18px;
                padding-left 15px;
                border-left: 4px solid #fd7d22;
            }
        }

        >dl,>.inputsInfo dl,table dl {
            overflow: hidden;
            clear: both;
            line-height: 30px;
            margin-top: 20px;
            font-size: 16px;
            color: #333;

            &.tuyang {
                margin-top 0;
                dd {
                    line-height: 38px;

                    input[type='radio'] {
                        vertical-align: middle;
                        cursor: pointer;
                    }

                    label {
                        margin-right: 15px;
                        margin-left: 5px;
                        cursor: pointer;
                    }

                    .wh {
                        top: 10px;
                        padding-bottom 10px;
                    }

                    .tu {
                        display: inline-block;
                        margin: 10px 0;
                        padding-top 10px;
                        text-align: center;
                        width: 180px;
                        height: 194px;
                        border: 1px solid #d1d1d1;
                        background: #f6f6f6;

                        >p {
                            font-size: 14px;
                            color: #666;
                        }

                        img {
                            margin: 5px 0 5px 0;
                        }

                        button {
                            margin: 0 5px;
                            font-size: 12px;
                            &.zn {
                                background: #4d6fcf;
                            }
                        }
                    }

                    .sltu {
                        display: inline-block;
                        position: relative;
                        margin-left: 10px;
                        vertical-align: top;
                        margin-top: 10px;
                    }
                }
            }

            dt {
                float: left;
                width: 122px;
                font-size 14px;
                line-height: 38px;
                text-align: right;
            }

            dd {
                overflow: hidden;
                padding-left: 20px;
                select {
                    height: 36px;
                    border: 1px solid #ccc;
                    border-radius: 3px;
                    font-size: 14px;
                    width: 200px;
                    padding: 6px;
                    margin-right: 10px;
                }

                textarea {
                    background: #fff;
                    line-height: 20px;
                    border: 1px solid #d3d3d3;
                    padding: 10px;
                    width: 390px;
                    height: 112px;
                    box-sizing: border-box;
                    resize: none;
                }

                input[type='text'], input[type='tel'] {
                    background: #fff;
                    height: 36px;
                    line-height: 36px;
                    border: 1px solid #d3d3d3;
                    padding: 0 10px;
                    width: 455px;
                    vertical-align: middle;
                    box-sizing: border-box;

                    &.w140 {
                        width: 140px;
                        margin-right: 10px;
                    }

                    &.w320 {
                        width: 320px;
                    }

                    &.sbname {
                        width: 390px;
                        margin-right: 10px;
                    }
                }

                .wh {
                    vertical-align: top;
                    font: normal 12px / 20px 'microsoft yahei';
                    color: #666;
                    display: inline-block;
                    padding-left: 20px;
                    position: relative;
                    text-align: left;
                    .wh {
                        left: -20px;
                    }

                    img {
                        position: absolute;
                        left: 0;
                        top: 2px;
                    }

                    a {
                        color: #fd7d22;
                    }

                    b {
                        font-weight: normal;
                        color: #ff3145;
                    }
                }

                .radio {
                    display: inline-block;
                    padding: 0 15px;
                    line-height: 32px;
                    border: 1px solid #d1d1d1;
                    color: #666;
                    margin-right: 10px;
                    border-radius: 3px;

                    &.atthis {
                        border: 1px solid #fd7d22;
                        color: #fd7d22;
                    }
                }

                button {
                    background: #fd7d22;
                    padding: 0 10px;
                    vertical-align: middle;
                    line-height: 33px;
                    color: #fff;
                    border-radius: 3px;
                    cursor: pointer;
                }

                input[type='file'] {
                    display: none;
                }

                ol {
                    overflow: hidden;
                    // margin-top: 20px;
                    // float left;
                    // width 700px;
                    
                    li {
                        float: left;
                        margin-right: 15px;
                        border: 1px solid #d1d1d1;
                        width: 144px;
                        height: 194px;
                        text-align: center;
                        font-size: 14px;
                        color: #666;
                        background #f6f6f6;
                        line-height: 18px;
                        position: relative;

                        &.upload {
                            p {
                                line-height: 18px;
                                font-size 12px;
                                padding-top: 5px;
                                padding-bottom 5px;
                            }

                            img {
                                margin: 10px 0 5px;
                            }

                            button {
                                font-size: 12px;
                            }
                        }
                    }
                }

                .sl {
                    position: absolute;
                    display: block;
                    left: 0;
                    top: 0;
                    width: 40px;
                    height: 40px;
                    background: transparent url('../../assets/images/order/icon_yl.png') left top no-repeat;
                }

                .fd {
                    position: absolute;
                    line-height: 33px;
                    height: 33px;
                    left: 50%;
                    margin-left: -44px;
                    display: block;
                    bottom: 7px;
                    width: 88px;
                    font-size: 14px;
                    border-radius 3px;
                    background: rgba(253, 125, 34, 0.9) url('../../assets/images/order/icon_search.png') no-repeat 16px center;
                    text-indent: 40px;
                    color: #fff;
                    text-align: left;
                }
            }
        }
    }
}

/* ---------- 智能推荐 ---------- */
span.header-right-secNum {
    margin-top: 2px;
    margin-right: 15px;
    font-size: 14px;
    color: #666;
}

span.header-right-secNum i {
    margin-left: 1px;
    margin-right: 1px;
}

span.header-right-secNum.red-color i {
    color: #fc461e;
}

.brandInfo-wrap .section-recommend {
    padding-top: 30px;
}

.brandInfo-wrap .section-recommend .section-header {
    position: relative;
    background-color: #f3f3f3;
    height: 58px;
}

.brandInfo-wrap .section-recommend .section-header .s1 {
    display: block;
    float: left;
    color: #999;
    margin: 18px 0px 0 20px;
    padding-left: 10px;
    height: 20px;
    line-height: 19px;
    font-size: 18px;
    border-left: solid 2px #f08b2f;
}

.brandInfo-wrap .section-recommend .section-header .btn-export {
    display: block;
    float: right;
    width: 106px;
    line-height: 32px;
    color: #f08b2f;
    border: solid 1px #f08b2f;
    text-align: center;
    border-radius: 3px;
    margin: 12px 20px 0 0;
    background-color: #fff;
    font-size: 14px;
}

.brandInfo-wrap .section-recommend .section-bodyer {
    padding: 0 32px;
}

.brandInfo-wrap .section-recommend .article {
    border-bottom: solid 1px #eee;
    padding-bottom: 15px;
}

.brandInfo-wrap .section-recommend .article .article-header {
    font-weight: normal;
    padding: 12px 0 6px;
}

.brandInfo-wrap .section-recommend .article .article-header span {
    display: block;
    float: left;
}

.brandInfo-wrap .section-recommend .article .article-header .header-left {
    float: left;
}

.brandInfo-wrap .section-recommend .article .article-header .header-left .col-1 {
    color: #666;
    font-size: 18px;
}

.brandInfo-wrap .section-recommend .article .article-header .header-left .col-2 {
    color: #888;
    margin: 3px 0 0 25px;
}

.brandInfo-wrap .section-recommend .article .article-header .header-left .col-3 {
    color: #f08b2f;
    margin: 3px 0 0 25px;
    display: block;
    float: left;
}

.brandInfo-wrap .section-recommend .article .article-header .header-left .col-3 .iconfont {
    margin: 3px 5px 0 0;
    float: left;
    font-size: 15px;
}

.brandInfo-wrap .section-recommend .article .article-header .header-right {
    float: right;
}

.brandInfo-wrap .section-recommend .article .article-header .header-right .col-1 {
    display: block;
    float: left;
    margin-right: 15px;
    font-size: 18px;
    color: #f08b2f;
}

.brandInfo-wrap .section-recommend .article .article-header .header-right .col-2 {
    display: block;
    float: left;
}

.brandInfo-wrap .section-recommend .article .article-header .header-right .col-2 .iconfont {
    font-size: 19px;
    color: #999;
}

.brandInfo-wrap .section-recommend .article .article-bodyer .list-box .list {
    display: block;
    float: left;
    height: 26px;
    line-height: 26px;
    padding: 0 10px;
    margin: 2px 0px 2px 20px;
    border: solid 1px #fff;
    cursor: default;
    position: relative;
    border-radius: 2px;
}

.brandInfo-wrap .section-recommend .article .article-bodyer .list-box .list .text {
    display: block;
    float: left;
}

.brandInfo-wrap .section-recommend .article .article-bodyer .list-box .list .close {
    display: none;
    width: 12px;
    height: 12px;
    line-height: 10px;
    cursor: pointer;
    background-color: #f08b2f;
    color: #fff;
    position: absolute;
    top: -6px;
    right: -6px;
    border-radius: 50%;
    text-align: center;
    font-size: 15px;
    font-weight: bold;
    overflow: hidden;
}

.brandInfo-wrap .section-recommend .article .article-bodyer .list-box .list:hover {
    border: solid 1px #f08b2f;
    color: #f08b2f;
}

.brandInfo-wrap .section-recommend .article .article-bodyer .list-box .list:hover .close {
    display: block;
}

.brandInfo-wrap .section-recommend .article .article-bodyer .list-box .list-add {
    color: #f08b2f;
    cursor: pointer;
}

.brandInfo-wrap .section-recommend .article .article-bodyer .list-box .list-add .iconfont {
    float: left;
    margin: 0 5px 0 -1px;
}

/* 群组 */
.brandInfo-wrap .section-recommend .article .group {
    height: 378px;
    background-color: #f9f9f9;
    font-size: 12px;
    margin: 15px 0;
    color: #666;
}

.brandInfo-wrap .section-recommend .article .group .group-left {
    float: left;
    width: 300px;
    height: 100%;
    padding: 12px 0;
    position: relative;
    z-index: 2;
    overflow-y: auto;
    border-right: solid 1px #eee;
    box-sizing: border-box;
}

.brandInfo-wrap .section-recommend .article .group .group-left .list {
    padding: 8px 25px;
    cursor: pointer;
}

.brandInfo-wrap .section-recommend .article .group .group-left .list:hover {
    color: #f08b2f;
}

.brandInfo-wrap .section-recommend .article .group .group-left .list.atthis {
    color: #f08b2f;
}

.brandInfo-wrap .section-recommend .article .group .group-right {
    overflow: auto;
    height: 100%;
}

.brandInfo-wrap .section-recommend .article .group .group-right .item {
    display: none;
    padding: 15px 0;
}

.brandInfo-wrap .section-recommend .article .group .group-right .item.active {
    display: block;
}

.brandInfo-wrap .section-recommend .article .group .group-right .list {
    float: left;
    width: 210px;
    padding-left: 18px;
    margin: 6px 15px;
    position: relative;
    cursor: pointer;
    box-sizing: border-box;
}

.brandInfo-wrap .section-recommend .article .group .group-right .list .iconfont {
    display: none;
    position: absolute;
    left: 0px;
    top: 1px;
    font-size: 14px;
}

.brandInfo-wrap .section-recommend .article .group .group-right .list .text {
    display: block;
    float: left;
    width: 100%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.brandInfo-wrap .section-recommend .article .group .group-right .list:hover {
    color: #f08b2f;
}

.brandInfo-wrap .section-recommend .article .group .group-right .list:hover .iconfont {
    display: block;
}

.brandInfo-wrap .section-recommend .article .group .group-right .list.selected .text {
    color: #999;
    text-decoration: line-through;
    cursor: default;
}

.brandInfo-wrap .section-recommend .article .group .group-right .list.selected:hover {
    color: #999;
}

.brandInfo-wrap .section-recommend .article .group .group-right .list.selected:hover .iconfont {
    display: none;
}

.brandInfo-wrap .section-recommend .article .article-bodyer.open .group {
    display: block;
}

/* 添加群组 */
.brandInfo-wrap .section-recommend .article .add-group {
    padding: 10px 0 5px 25px;
}

.brandInfo-wrap .section-recommend .article .add-group .btn {
    display: inline-block;
    color: #f08b2f;
    border: solid 1px #f08b2f;
    border-radius: 3px;
    padding: 5px 15px;
}

.brandInfo-wrap .section-recommend .article .add-group .btn .iconfont {
    font-size: 14px;
    margin-right: 5px;
}

.myInput {
    height: 34px;
    border: solid 1px #ccc;
    border-radius: 2px;
    font-size: 14px;
    width: 370px;
    padding: 6px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.myInput:focus {
    border-color: #F08B2F;
}

/* 添加商标45类别 */
.brandInfo-wrap .section-recommend .add-category {
    padding: 25px 32px;
}

.brandInfo-wrap .section-recommend .add-category .btn-add {
    display: inline-block;
    padding: 0 20px;
    height: 36px;
    line-height: 36px;
    background-color: #f7f8fb;
    color: #f08b2f;
    border-radius: 3px;
    font-size: 16px;
}

.brandInfo-wrap .section-recommend .add-category .btn-add i {
    display: block;
    float: left;
    font-size: 27px;
    margin: -1px 5px 0 0;
}

.brandInfo-wrap .section-recommend .add-category .list-box {
    background-color: #f7f8fb;
    padding: 16px 0;
    border-radius: 0 3px 3px 3px;
}

.brandInfo-wrap .section-recommend .add-category .list {
    display: block;
    float: left;
    height: 22px;
    line-height: 22px;
    margin: 7px 0px 7px 35px;
    color: #666;
    cursor: pointer;
}

.brandInfo-wrap .section-recommend .add-category .list .color {
    color: inherit;
}

.brandInfo-wrap .section-recommend .add-category .list:hover {
    color: #f08b2f;
}

.brandInfo-wrap .section-recommend .add-category .list.selected:hover {
    color: #999;
}

.brandInfo-wrap .section-recommend .add-category .list.selected {
    color: #999;
    text-decoration: line-through;
    cursor: default;
}

.brandInfo-wrap .section-recommend .add-category.open .list-box {
    display: block;
}

.brandInfo-wrap .section-recommend .exportAll {
    padding: 0px 32px;
}

.brandInfo-wrap .section-recommend .exportAll .btn-exportAll {
    display: block;
    width: 106px;
    line-height: 32px;
    color: #f08b2f;
    border: solid 1px #f08b2f;
    text-align: center;
    border-radius: 3px;
    background-color: #fff;
    font-size: 14px;
}

/* ---------- 自助选择 ---------- */
.brandInfo-wrap .section-selfchoice {
    border: solid 1px #eee;
    height: 500px;
}

/* 左群组 */
.brandInfo-wrap .section-selfchoice .group-left {
    float: left;
    width: 330px;
    height: 100%;
    border-right: solid 1px #eee;
    position: relative;
    z-index: 2;
    background-color: #f9f9f9;
}

.brandInfo-wrap .section-selfchoice .group-left .group-header {
    margin-top: 15px;
    margin-left: 20px;
    color: #666;
    font-size: 18px;
    height: 18px;
    line-height: 17px;
    padding-left: 10px;
    border-left: solid 2px #f08b2f;
}

.brandInfo-wrap .section-selfchoice .group-left .group {
    margin-top: 15px;
    height: 433px;
    padding: 0px 25px 0 32px;
    overflow: auto;
}

.brandInfo-wrap .section-selfchoice .group-left .list {
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-first {
    display: block;
    padding: 6px 0;
    color: #333;
    cursor: pointer;
}

.brandInfo-wrap .section-selfchoice .group-left .list.selected .title-first{
    color: #f08b2f
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-first .color {
    color: inherit;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-first:hover {
    color: #f08b2f;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second {
    padding: 3px 0;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-lg {
    display: block;
    height: 16px;
    padding: 0 0 0 14px;
    color: #666;
    font-size: 12px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    cursor: pointer;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .get-all-second {
    display: block;
    height: 16px;
    padding: 0 0 0 14px;
    color: #666;
    font-size: 12px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    cursor: pointer;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-lg:hover {
    color: #f08b2f;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm {
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm .row {
    line-height: 26px;
    height: 26px;
    padding: 5px 0;
    padding: 0 0 0 45px;
    font-size: 12px;
    color: #666;
    overflow: hidden;
    cursor: pointer;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm .row i {
    position: relative;
    top: -2px;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm .get-all-small {
    height: 16px;
    padding: 0 0 0 45px;
    font-size: 12px;
    color: #888;
    overflow: hidden;
    cursor: pointer;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm .row .iconfont {
    display: block;
    float: left;
    font-size: 14px;
    color: #f08b2f;
    margin-top: 1px;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm .row .text {
    box-sizing: border-box;
    float: left;
    width: 100%;
    height: 100%;
    margin-left: -16px;
    padding-left: 20px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm .row:hover {
    color: #f08b2f;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm .row.selected .iconfont {
    color: #888;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm .row.selected .text {
    text-decoration: line-through;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second .second-sm .row.selected:hover {
    color: #888;
    cursor: default;
}

.brandInfo-wrap .section-selfchoice .group-left .list.open .title-first {
    color: #f08b2f;
}

.brandInfo-wrap .section-selfchoice .group-left .list.open .title-second {
    display: block;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second.open .second-sm {
    display: block;
}

.brandInfo-wrap .section-selfchoice .group-left .list .title-second.open .second-lg {
    color: #f08b2f;
}

/* 右小项 */
.brandInfo-wrap .section-selfchoice {
    margin-top: 30px;
}

.brandInfo-wrap .section-selfchoice .group-right {
    overflow: hidden;
    position: relative;
    height: 500px;
}

.brandInfo-wrap .section-selfchoice .group-right .bgimg {
    display: block;
    position: absolute;
    top: 150px;
    left: 50%;
    margin-left: -180px;
}

.brandInfo-wrap .section-selfchoice .group-right .title {
    position: relative;
    height: 50px;
    padding: 16px 0 0 20px;
    background-color: #fafbfc;
    margin: 0;
}

.brandInfo-wrap .section-selfchoice .group-right .title span {
    display: block;
    color: #666;
    font-size: 14px;
    height: 18px;
    line-height: 17px;
    margin-top: 10px;
    padding-left: 10px;
    border: 0;
    border-left: solid 2px #f08b2f;
    font-weight: normal;
}

.brandInfo-wrap .section-selfchoice .group-right .section {
    height: 418px;
    padding: 5px 0;
    overflow: auto;
}

.brandInfo-wrap .section-selfchoice .group-right .article {
    padding: 10px 20px 5px 34px;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header {
    font-weight: normal;
    padding: 0px 0 8px;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header span {
    display: block;
    float: left;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header .header-left {
    float: left;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header .header-left .col-1 {
    color: #666;
    font-size: 16px;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header .header-left .col-2 {
    color: #888;
    margin: 3px 0 0 25px;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header .header-left .col-3 {
    color: #f08b2f;
    margin: 3px 0 0 25px;
    display: block;
    float: left;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header .header-left .col-3 .iconfont {
    margin: 3px 5px 0 0;
    float: left;
    font-size: 15px;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header .header-right {
    float: right;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header .header-right .col-1 {
    display: block;
    float: left;
    margin-right: 15px;
    font-size: 18px;
    color: #f08b2f;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header .header-right .col-2 {
    display: block;
    float: left;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-header .header-right .col-2 .iconfont {
    font-size: 19px;
    color: #999;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-bodyer .list-box .list {
    display: block;
    float: left;
    height: 26px;
    line-height: 26px;
    padding: 0 10px;
    margin: 0px 0px 0px 20px;
    border: solid 1px transparent;
    cursor: default;
    position: relative;
    border-radius: 2px;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-bodyer .list-box .list .text {
    display: block;
    float: left;
    font-size: 13px;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-bodyer .list-box .list .close {
    display: none;
    width: 12px;
    height: 12px;
    line-height: 10px;
    cursor: pointer;
    background-color: #f08b2f;
    color: #fff;
    position: absolute;
    top: -6px;
    right: -6px;
    border-radius: 50%;
    text-align: center;
    font-size: 13px;
    font-weight: bold;
    overflow: hidden;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-bodyer .list-box .list:hover {
    border: solid 1px #f08b2f;
    color: #f08b2f;
}

.brandInfo-wrap .section-selfchoice .group-right .article .article-bodyer .list-box .list:hover .close {
    display: block;
}

.row-way .btn-choice {
    display: inline-block;
    width: 106px;
    height: 32px;
    line-height: 29px;
    border: solid 1px #ddd;
    border-radius: 3px;
    text-align: center;
    margin-right: 12px;
}

.row-way .btn-choice.active {
    color: #f08b2f;
    border: solid 1px #f08b2f;
}

.row-way .btn-choice .iconfont {
    display: none;
    font-size: 10px;
    margin-right: 5px;
}

.row-way .btn-choice.active .iconfont {
    display: inline-block;
}

.c-row label {
    display: inline-block;
    padding-right: 15px;
    width: 145px;
    font-size: 14px;
    text-align: right;
}

.c-row-content {
    display: inline-block;
}

.smartRegister-page .last-pay li.row-invoice .icon-img {
    margin-right: 0px;
    
}

.ns-search-container {
    padding: 15px;
    padding-bottom: 0px;
}

.ns-search-container .myInputGroup {
    width: 100%;
}

.ns-search-container .myInputGroup .myInput {
    width: 100%;
}

.categoryInfo-wrap {
    padding-left: 50px;
}

.categoryInfo-wrap .row-way {
    margin-bottom: 15px;
}

.group-right-search {
    position: absolute;
    right: 15px;
    top: 10px;
    height: 40px;
}

.group-right-search .button {
    display: inline-block;
    padding: 5px 8px;
    color: #fff;
    background-color: #f08b2f;
    border-radius: 2px;
    border: 1px solid #f08b2f;
    cursor: pointer;
}

.group-right-search .group-right-search-input {
    display: inline-block;
    margin-left: 15px;
}

.group-right-search .group-right-search-input input[type=text] {
    padding: 5px;
    height: 30px;
    border-radius: 2px;
    margin-right: 5px;
    border: 1px solid #aaa;
}

.group-right-search .group-right-search-input .button {
    font-size: 14px;
}

.btn-prev {
    display: inline-block;
    margin-right: 30px;
    line-height: 36px;
    padding: 0 42px;
    border: solid 1px #ff7200;
    color: #ff7200;
    border-radius: 2px;
}

.btn-next {
    display: inline-block;
    line-height: 36px;
    padding: 0 42px;
    background-color: #ff7200;
    color: #fff;
    border-radius: 2px;
}

.btn-next.disabled {
    opacity: 0.2;
    cursor: default;
}

.myInputGroup {
    position: relative;
}

.myInputGroup .myBtn {
    display: block;
    position: absolute;
    top: 0px;
    right: 0px;
    height: 100%;
    width: 40px;
    background-color: transparent;
    border: 0;
    border-left: 1px solid #ccc;
    cursor: pointer;
    border-radius: 0;
    color: #888;
}

.upbtn {
    text-align: center;
}

.upbtn dl {
    display: inline-block;
    padding: 10px;
    width: 230px;
    position: relative;
    line-height: 50px;
    border-radius: 5px;
    margin: 0 10px;
    cursor: pointer;
}

.upbtn dl dt {
    font-size: 22px;
}

.upbtn dl dd {
    font-size: 12px;
    color: #666;
}

.upbtn dl:hover dd {
    color: #fff;
}

.upbtn dl:hover {
    background-color: #ff6700 !important;
    color: #fff !important;
    border: 1px solid #ff6700 !important;
}

.upbtn dl:hover dt {
    color: #fff !important;
}



.upbtn dl.mian {
    border: 1px solid #e1e1e1;
    background-color: #fbfbfb;
}

.upbtn dl.mian dt {
    color: #444;
}

.upbtn dl.mian dt:before {
    background: url('../../assets/images/order/sbzc_24.png') no-repeat;
}

.upbtn dl.mian:hover dt:before {
    background: url('../../assets/images/order/sbzc1_24.png') no-repeat;
}

.upbtn dl.dai {
    color #fff;
    border: 1px solid #ffd8bd;
    background-color: #fd7d22;
}

.upbtn dl.dai dt {
    color: #fff;
}

.upbtn dl.dai dt:before {
    // background: url('../../assets/images/order/sbzc_26.png') no-repeat;
}

.upbtn dl.dai:hover dt:before {
    // background: url('../../assets/images/order/sbzc1_26.png') no-repeat;
}

.upbtn dl.kuai dt {
    color: #0071e3;
}

.upbtn dl.kuai {
    border: 1px solid #c5e2ff;
    background-color: #f4faff;
}

.upbtn dl.kuai dt:before {
    background: url('../../assets/images/order/sbzc_21.png') no-repeat;
}

.upbtn dl.kuai:hover dt:before {
    background: url('../../assets/images/order/sbzc1_21.png') no-repeat;
}

.service_form2 .upbtn .tuiimg {
    position: absolute;
    top: 0;
    right: -1px;
}

.saveLoca {
    margin-top: 20px;
    font-size: 14px;
    color: #666;

    a {
        color: #fd7d22;
    }
}

.agree-box {
    text-align: center;
    padding: 30px 0;
    color: #333;
    font-size: 16px;

    .iconfont {
        font-size: 17px;
        color: #ccc;
        cursor: pointer;
        vertical-align: middle;

        &.checked {
            color: #fd7d22;
        }
    }

    a {
        color: #fd7d22;
    }

    span {
        cursor: pointer;
    }
}

.last-pay {
    text-align: right;
    font-size: 14px;
    color: #999;
    line-height: 30px;
    padding-top: 15px;

    li {
        &.row-sense {
            font-size: 16px;
            color: #fd7d22;
        }
    }
}

.model {
    position: fixed;
    background: rgba(0, 0, 0, 0.5);
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    z-index: 999;
}

.model .cont {
    width: 450px;
    margin: 10% auto;
    background: #fff;
    padding: 15px;
    border-radius: 5px;
    position: relative;
}

.model .cont h1 {
    color: #F08B2F;
    text-align: center;
    font-size: 16px;
    margin-bottom: 10px;
}

.model .cont>i {
    position: absolute;
    right: 15px;
    top: 15px;
    font-size: 20px;
    cursor: pointer;
}

.color {
    color: #F08B2F;
    cursor: pointer;
}

.model .cont>p {
    line-height: 26px;
}

.model {
    .tab {
        line-height: 40px;
        border-bottom: 1px solid #e5e5e5;
        font-size: 16px;
        height: 40px;

        a {
            float: left;
            padding: 0 15px;

            &.atthis {
                color: #f08b2f;
                font-weight: bold;
                line-height: 38px;
                border-bottom: 2px solid #f08b2f;
            }
        }
    }

    .list {
        li {
            margin-top: 10px;

            a {
                display: block;
                border: 1px solid #f08b2f;
                background: #fff;
                border-radius: 5px;
                line-height: 35px;
                font-size: 14px;
                padding: 0 10px;
                color: #666;

                span {
                    float: right;
                }
            }
        }
    }

    .test {
        text-align: center;

        input, select {
            width: 320px;
            height: 36px;
            border: 1px solid #eee;
            line-height: 36px;
            font-size: 14px;
            margin-top: 15px;
            color: #666;
            padding: 0 10px;
        }

        select {
            width: 340px;
        }

        .res {
            padding: 0 50px;
            font-size: 18px;
            color: #999;
            text-align: left;

            p {
                padding-left: 20px;
                font: normal 14px / 22px 'microsoft yahei';
                color: #666;

                a, samp {
                    color: #f08b2f;
                }
            }
        }
    }
}

.model .cont .color {
    cursor: text;
}

.model .cont .scroll{
    max-height: 300px;
    overflow: auto;
    padding-left: 20px;
}

.model .cont .btn {
    overflow: hidden;
    text-align: center;
    margin: 25px 0 10px;
}

.model .cont .btn a {
    display: inline-block;
    @css { * }display: inline;
    @css { * }zoom: 1;
    width: 120px;
    height: 34px;
    line-height: 32px;
    text-align: center;
    border: 1px solid #f08b2f;
    color: #f08b2f;
    margin: 0 10px;
}

.model .cont .btn a.active {
    background: #f08b2f;
    color: #fff;
}

.c_orange {
    color: #f08b2f !important;
}

.relative {
    position: relative;
}

.mark_main {
    position: absolute;
    left: 720px;
    top: 900px;
    background: #fff;
    border: 1px solid #dcdcdc;
    border-top: 0;
    z-index: 99;

    .left {
        float: left;
    }

    .right {
        float: right;
    }

    &.branName {
        left: 800px;
        top: 850px;
        line-height: 22px;

        .center {
            text-align: center;
        }
    }

    h3 {
        font-size: 16px;
        color: #f08b2f;
        line-height: 45px;
        padding: 0 15px;
        border-top: 3px solid #f08b2f;

        i {
            float: right;
            color: #999;
            font-size: 18px;
            cursor: pointer;

            &:hover {
                color: #666;
            }
        }
    }

    ul {
        padding: 0 15px 20px;

        li {
            line-height: 30px;
            color: #666;

            strong {
                font-size: 16px;
                padding-right: 10px;
            }
        }
    }
}

.tuiimg {
    position: absolute;
    top: 0;
    right: -1px;
}

input.error {
    border-color: #ff1a14 !important;
}
.template{
    font-size 14px;
    margin-left 15px;
    color #fd7d22
}
</style>

