<template>
    <div class="smartRegister-page smartRegister3-page">
        <!-- start  进度条 -->
        <div class="progressBar clearfix">
            <ul>
                <li class="step step-2 finish">
                    <em class="icon-bar-circle"></em>
                    <dl class="explain">
                        <dt class="text">填写商标信息</dt>
                    </dl>
                </li>
                <li class="line"></li>
                <li class="step step-3">
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
        <!-- end 进度条 -->
        <!-- start 代理人列表 -->
        <div class="agent">
            <h6 class="agent-header">
                <i class="iconfont icon-consult"></i>商标顾问与帮助</h6>
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
        <!-- end 代理人列表 -->
        <div class="brandInfo-wrap">
            <!-- start 填写商标基本信息 -->
            <div class="section-base">
                <table width="100%" class="table-1">
                    <tbody>
                        <tr class="row-type">
                            <td class="td-title" width="145px">
                                <i class="i-star">*</i>商标类型：</td>
                            <td class="td-content">
                                <div class="myRadioBoxs" id="selectBrandType" @click="selectedType($event, 'type')">
                                    <label class="label" :class="{'checked': type == 1}" data-type="1">
                                        <i class="iconfont icon-iconfontdanxuan" data-type="1" v-if="type != 1">&#xe741;</i>
                                        <i class="iconfont icon-danxuan" data-type="1" v-if="type == 1">&#xe6f7;</i>文字</label>
                                    <label class="label" :class="{'checked': type == 2}" data-type="2">
                                        <i class="iconfont icon-iconfontdanxuan" data-type="2" v-if="type != 2">&#xe741;</i>
                                        <i class="iconfont icon-danxuan" data-type="2" v-if="type == 2">&#xe6f7;</i>图形</label>
                                    <label class="label" :class="{'checked': type == 3}" data-type="3">
                                        <i class="iconfont icon-iconfontdanxuan" data-type="3" v-if="type != 3">&#xe741;</i>
                                        <i class="iconfont icon-danxuan" data-type="3" v-if="type == 3">&#xe6f7;</i>文字及图形</label>
                                </div>
                                <a v-if="false" class="how-choose" href="/index/about/12.html" target="_blank">如何选择商标类型？</a>
                            </td>
                        </tr>
                        <tr class="row-name" v-if="type != 2">
                            <td class="td-title">
                                <i class="i-star">*</i>商标名称：</td>
                            <td class="td-content">
                                <input type="text" v-model="name" name="brandName" value="" class="myInput" placeholder="请输入商标名称">
                                <label id="jitu" v-if="type == 3"> 及图</label>
                            </td>
                        </tr>
                        <tr class="row-tuyang show-create show-create1">
                            <td class="td-title" style="vertical-align: top">
                                <i class="i-star">*</i>商标图样：</td>
                            <td class="td-content">
                                <div v-if="type == 1" class="myRadioBoxs" id="create-tuyang" @click="selectedType($event, 'autoType')">
                                    <label class="label" :class="{'checked': autoType == 1}" data-type="1">
                                        <i class="iconfont icon-iconfontdanxuan" data-type="1" v-if="autoType != 1">&#xe741;</i>
                                        <i class="iconfont icon-danxuan" data-type="1" v-if="autoType == 1">&#xe6f7;</i>自动生成</label>
                                    <label class="label " :class="{'checked': autoType == 2}" data-type="2">
                                        <i class="iconfont icon-iconfontdanxuan" data-type="2" v-if="autoType != 2">&#xe741;</i>
                                        <i class="iconfont icon-danxuan" data-type="2" v-if="autoType == 2">&#xe6f7;</i>手动上传</label>
                                </div>
                                <!-- start 手动上传 -->
                                <div v-if="autoType == 2 || type != 1" class="shoudong-create">
                                    <ul class="showPhotos newShowPhotos clearfix">
                                        <li class="list-photo">
                                            <div class="top">
                                                <div class="top-container">
                                                    <div class="photo-box btnuploadtuyang" style="z-index: 1;">
                                                        <img v-if="uploadRes" class="photo colorshowurl" :src="uploadRes.fsHost+uploadRes.fsPath">
                                                        <div v-if="!uploadRes" class="bg colorbg" @click="upload">
                                                            <span class="icon">
                                                                <i class="iconfont icon-jiahao">&#xe623;</i>
                                                            </span>
                                                            <span class="text">不大于200k的jpg</span>
                                                        </div>
                                                    </div>
                                                    <dl class="shadow-box" v-if="uploadRes">
                                                        <i class="shadow"></i>
                                                        <dd class="mybtns">
                                                            <span class="box">
                                                                <a class="btn colorlinkurl" :href="uploadRes.fsHost+uploadRes.fsPath" target="_blank">查看大图</a>
                                                                <a class="btn btnuploadtuyang" @click="upload">重新上传</a>
                                                                <div id="html5_1cohigm7jjq81c1e15mc1j9a1kbh6_container" class="moxie-shim moxie-shim-html5"></div>
                                                            </span>
                                                        </dd>
                                                    </dl>
                                                    <input ref="uploadImg" type="file" accept="image/jpeg,image/x-png">
                                                </div>
                                            </div>
                                            <div class="bottom">商标图样</div>
                                        </li>
                                    </ul>
                                    <dl class="msg-tips clearfix tips-2">
                                        <dt class="i-left">
                                            <i class="iconfont icon-warning-r">&#xe8ed;</i>
                                        </dt>
                                        <dd class="i-right">
                                            上传黑白图样，注册后可以更换商标颜色使用；
                                            <br> 上传彩色图样，注册后只能按照该彩色图样使用，并在委托书商标名称后方增加文字“（指定颜色）”；
                                            <br> 上传的彩色图样需与黑白图样的样式一致。
                                            <br>
                                            <i class="color" @click="isTips=true">查看上传商标图样注意事项</i>
                                        </dd>
                                    </dl>
                                </div>
                                <!-- end 手动上传 -->
                                <!-- start 自动生成 -->
                                <div v-if="autoType == 1 && type == 1" class="zidongdong-create">
                                    <ul class="showPhotos newShowPhotos clearfix">
                                        <li class="list-photo show">
                                            <div class="top">
                                                <div class="top-container">
                                                    <div class="photo-box">
                                                        <img v-if="createdRes" class="photo createbrandpic" :src="createdRes.fsHost+createdRes.fsPath">
                                                        <div class="bg">
                                                            <span class="icon">
                                                                <i class="iconfont icon-jiahao">&#xe623;</i>
                                                            </span>
                                                            <span class="text">不大于200k的jpg</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bottom getBrandPic" @click="createdImg" data-has="0">
                                                <a href="javascript:;">生成黑白图样</a>
                                            </div>
                                            <input type="hidden" name="createbrandpic" value="">
                                        </li>
                                    </ul>
                                    <dl class="msg-tips clearfix tips-1">
                                        <dt class="i-left">
                                            <i class="iconfont icon-warning-r">&#xe8ed;</i>
                                        </dt>
                                        <dd class="i-right">
                                            知千秋平台默认以“宋体字样自左向右排列”递交官方；
                                        </dd>
                                    </dl>
                                </div>
                                <!-- end 自动生成 -->
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- end 填写商标基本信息 -->
        </div>
        <div id="uploader" class="plupload_modal"></div>
        <div class="brandInfo-wrap">
            <!-- start 填写商标基本信息 -->
            <div class="categoryInfo-wrap">
                <div class="c-row row-way">
                    <label>选择商标类别：</label>
                    <div class="c-row-content" @click="selectedType($event, 'brandType')">
                        <a class="btn-choice" href="javascript:;" :class="{'active': brandType == 0}" data-type="0">
                            <i class="iconfont icon-duigou">&#xe63f;</i>智能推荐</a>
                        <a class="btn-choice" :class="{'active': brandType == 1}" href="javascript:;" data-type="1">
                            <i class="iconfont icon-duigou">&#xe63f;</i>自助选择</a>
                    </div>
                </div>
                <div class="c-row row-industry1" v-show="brandType == 0">
                    <label>
                        <i class="i-star">*</i>选择所在领域：</label>
                    <div class="c-row-content">
                        <select class="myInput znfirst" @change="changeIndex">
                            <option value="">请选择</option>
                            <option v-if="doMain" v-for="(item,index) in doMain" :selected="doMainIndex === index" :key="item.id" :value="index">{{item.name}}</option>
                        </select>
                        <select class="myInput znsecond" @change="getRecom" v-if="doMain[doMainIndex]">
                            <option value="0">请选择</option>
                            <option v-for="(item) in doMain[doMainIndex].sonArr" :selected="doMainchildIndex == item.id" :key="item.id" :value="item.id">{{item.name}}</option>
                        </select>
                    </div>
                </div>
            </div>
            <!-- start 智能推荐 -->
            <div class="section-recommend" id="section-recommend" v-if="brandType == 0">
                <h3 class="section-header">
                    <span class="s1">需要保护的商品/服务项目</span>
                </h3>
                <div class="section-bodyer">

                    <div class="article" v-for="(item, index) in selectList" :key="item.id" data-cgid="7956" data-cgnum="29" data-cgname="食品">
                        <h4 class="article-header clearfix">
                            <div class="header-left">
                                <span class="col-1">第
                                    <i class="color">{{item.number}}</i>类 {{item.name}}</span>

                                <a class="col-3" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)" style="cursor: pointer;">
                                    <i class="iconfont icon-xiaoxi"></i>咨询</a>
                            </div>
                            <div class="header-right">
                                <span class="header-right-secNum">已选择
                                    <i>{{item.sonArr.length}}</i>项</span>
                                <span class="col-1 price">￥
                                    <i v-if="item.sonArr.length<=10">300</i>
                                    <i v-if="item.sonArr.length>10">{{item.sonArr.length*30}}</i>
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
                <div class="add-category">
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
                            <input type="text" name="keys" v-model="searcKey" class="myInput" placeholder="请输入商品/服务名称进行查询">
                            <button class="myBtn" @click="searchType" id="btn-search">
                                <i class="iconfont icon-danchucengzhuanhuan">&#xe678;</i>
                            </button>
                        </div>
                    </div>
                    <ul class="group scroll">
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
                        <li class="list" @click="getSonType($event, 'two',item.number, index)" v-for="(item,index) in trademarkListAll" :key="item.id" :class="{'selected': item.checked}">
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
                                    <a class="col-3" onclick="var left = (window.screen.width-10-800)/2; window.open('http://p.qiao.baidu.com/cps/chat?siteId=12640048&userId=26537549', '', 'height=600, width=800,top=200, left='+left)" style="cursor: pointer;">
                                        <i class="iconfont icon-xiaoxi"></i>咨询</a>
                                </div>
                                <div class="header-right">
                                    <span class="header-right-secNum red-color">已选择
                                        <i>{{item.sonArr.length}}</i>项</span>
                                    <span class="col-1 price">￥
                                        <i v-if="item.sonArr.length<=10">300</i>
                                        <i v-if="item.sonArr.length>10">{{item.sonArr.length*30}}</i>
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
        <!-- end 商标信息 -->
        <div class="last-pay">
            <ul class="list-box">
                <li class="row-officer">官费总额：￥
                    <i>{{officeTotal}}</i>
                </li>
                <!-- <li class="row-invoice">发票税费：￥<i>0</i>
          <i class="iconfont icon-img">&#xe61d;</i>
        </li> -->
                <!-- <i class="row-service">服务费：￥<i>0</i></li>
            <li class="row-conserve">已为您节省：￥<i>0</i></li> -->
                <li class="row-sense">应付总额：
                    <em>￥
                        <i>{{officeTotal}}</i>
                    </em>
                </li>
                <li class="row-step">
                    <a class="btn-step submitorder" @click="submit()" href="javascript:;">下一步</a>
                </li>
            </ul>
        </div>

        <div class="model" v-if="isNoTen">
            <div class="content">
                <h1>注意</h1>
                <p>以下类别选择的商品/服务项目不足10项。
                    </p>
                        <p v-for="item in noTens" :key="item.id">第{{item.number}}类 {{item.name}}，已选择{{item.sonArr.length}}个项目;</p>
                        <div class="btn">
                            <a class="active" @click="isNoTen = false" href="javascript:;">继续修改</a>
                            <a href="javascript:;" @click="submit(true)">提交</a>
                        </div>
            </div>
        </div>

        <div class="model" v-if="isNoNumber">
            <div class="content">
                <h1>注意</h1>
                <p>以下类别选择的商品/服务项目必须选择
                    <b class="red">1</b>个三级类别。
                    </p>
                        <p v-for="item in noTens" v-if="item.sonArr.length == 0" :key="item.id">第{{item.number}}类 {{item.name}}</p>
                        <div class="btn">
                            <a class="active" @click="isNoNumber = false" href="javascript:;">添加</a>
                        </div>
            </div>
        </div>

        <div class="model" v-if="isTips">
            <div class="content">
                <i class="iconfont" @click="isTips=false">&#xe7fc;</i>
                <h1>上传商标图样注意事项</h1>
                <div class="text">图样文件格式应为jpg，图形应清晰，图样文件大小应小于“
                    <i class="color">200KB</i>”，且图形像素介于“
                    <i class="color">600X600 - 1500X1500</i>”之间，如果通过扫描获得图样的，应按
                    <i class="color">24</i>位彩色，
                    <i class="color">300dpi</i>分辨率扫描符合《商标法》及其实施条例规定的图形（图形清晰，大于
                    <i class="color">5X5</i>厘米且小于
                    <i class="color">10X10</i>厘米），申请人（或代理人）应确保扫描后的图形与原图形的色彩深浅（或灰度）一致，否则，可能影响《商标注册证》上图形的清晰度）
                    <i class="color">如上传图样为黑白，取得注册后，可以更换商标颜色使用；如上传图样为彩色，取得注册后只能按照该彩色图样使用；如确认提交彩色图样，需在委托书商标名称后方增加以下文字“（指定颜色）”</i>
                </div>
            </div>
        </div>

        <to-top />
    </div>
</template>
<script>
import toTop from "@/components/toTop";
import axios from "axios";
import popup from "~/components/popup"; //消息
import Cookies from "js-cookie";

export default {
    middleware: "auth",
    head() {
        return {
            title: "智能订单-知千秋",
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
            ]
        };
    },
    components: {
        toTop
    },
    data() {
        return {
            isTips: false,
            isNoTen: false,
            isNoNumber: false,
            noZeros: [], // 一项都没有的
            noTens: [], // 不足10个小项的
            type: 1, // 商标类型
            autoType: 1, // 生成类型
            brandType: 0, // 商标类别
            name: "", // 商标名称
            doMain: [],
            doMainIndex: "",
            doMainchildIndex: "",
            isAddType: false,
            selectList: [], // 用户选择的服务项
            selectAutoList: [], // 自主选择的服务项
            trademarkList: "", // 45大类
            trademarkListAll: "", //所有分类
            trademarkSearchListAll: "", // 搜索的分类
            sallTypes: {}, // 待添加的小类
            samllTypeIndex: {}, // 小类选中
            uploadRes: "", // 上传图的
            createdRes: "", // 创建图
            searcKey: "",
            product: Cookies.getJSON("product")
        };
    },
    mounted() {
        if (!this.product) {
            popup.created({
                content: "产品过期，请重新下单",
                time: 2000
            });
            window.history.href = "/";
        }
        if (!sessionStorage.getItem("brandInfo")) {
            this.loadDomain();
            this.loadType();
        } else {
            let obj = JSON.parse(sessionStorage.getItem("brandInfo"));
            this.name = obj.name;
            this.uploadRes = JSON.parse(obj.uploadRes);
            this.createdRes = JSON.parse(obj.createdRes);
            this.type = obj.type;
            this.brandType = obj.brandType;
            this.autoType = obj.autoType;
            this.doMain = JSON.parse(obj.doMain);
            this.doMainIndex = parseInt(obj.doMainIndex);
            this.doMainchildIndex = obj.doMainchildIndex;
            this.selectAutoList = JSON.parse(obj.selectAutoList);
            this.selectList = JSON.parse(obj.selectList);
            this.trademarkList = JSON.parse(obj.trademarkList);
            this.trademarkListAll = JSON.parse(obj.trademarkListAll);
        }
    },
    methods: {
        selectedType(e, name) {
            if (e.target.nodeName === "LABEL" || e.target.nodeName === "I") {
                this[name] = e.target.dataset.type;
            }
            if (e.target.nodeName === "A") {
                this[name] = e.target.dataset.type;
            }
        },
        loadDomain() {
            this.$Http(
                "/industry/queryDomain",
                { userId: this.$store.state.userInfo.userId },
                "get",
                true
            ).then(res => {
                this.doMain = res.data.data;
            });
        },
        // 加载所有分类
        loadType(index) {
            this.$Http(
                "/industry/queryTrademarkList",
                { userId: this.$store.state.userInfo.userId },
                "get"
            ).then(res => {
                this.trademarkList = res.data.data;
                this.trademarkListAll = res.data.data;
            });
        },
        // 搜索分类
        searchType() {
            if (this.searcKey == "") {
                this.loadType();
                this.trademarkSearchListAll = [];
                return false;
            }
            this.$Http(
                "/industry/queryTrademarkSearchList",
                {
                    userId: this.$store.state.userInfo.userId,
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
                            childItem.sonArr.map(threeItem => {
                                item.sonArr.map(fourItem => {
                                    fourItem.sonArr.map(fiveItem => {
                                        if (threeItem.id === fiveItem.id) {
                                            fiveItem.checked = true;
                                            console.log(fiveItem);
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
            if (e.target.value != "") {
                this.doMainIndex = parseInt(e.target.value);
            } else {
                this.doMainIndex = "";
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
                arr.map(item => {
                    this.selectList[i].sonArr.map(childItem => {
                        if (item.id === childItem.id) {
                            item.checked = true;
                        }
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
                    userId: this.$store.state.userInfo.userId,
                    text: this.name,
                    imgWidth: 150,
                    imgHeight: 150
                },
                "get",
                true
            ).then(res => {
                this.createdRes = res.data.data;
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
                console.log(file);
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
                param.append("userId", this.$store.state.userInfo.userId);
                param.append("type", "upload_file_type_02");
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
                            this.uploadRes = res.data.data;
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
                    userId: this.$store.state.userInfo.userId,
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
                    // this.trademarkSearchListAll[index].sonArr[childIndex].sonArr.map((item) => {
                    //     arr.map((childItem) => {
                    //         if (item.id == childItem.id && item.checked) {
                    //             childItem.checked = true
                    //         }
                    //     })

                    // })
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
                    this.trademarkSearchListAll[index].sonArr[
                        childIndex
                    ].sonArr.map(item => {
                        arr.map(childItem => {
                            if (item.id == childItem.id && item.checked) {
                                childItem.checked = true;
                            }
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
            e.stopPropagation();
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

            this.$Http(
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

        //提交
        submit(type) {
            if (parseInt(this.type) == 1) {
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
            }

            if (parseInt(this.type == 2)) {
                if (!this.uploadRes) {
                    popup.created({
                        content: "请先上传商标图样",
                        time: 2000
                    });
                    return false;
                }
            }

            if (parseInt(this.type == 3)) {
                if (!this.name) {
                    popup.created({
                        content: "商标名称不能为空",
                        time: 2000
                    });
                    return false;
                }
                if (!this.uploadRes) {
                    popup.created({
                        content: "请先上传商标图样",
                        time: 2000
                    });
                    return false;
                }
            }

            if (!parseInt(this.officeTotal)) {
                popup.created({
                    content: "请先选择商标类别",
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
            if (!type && this.noZeros.length) {
                this.isNoNumber = true;
                return false;
            }

            if (!type && this.noTens.length) {
                this.isNoTen = true;
                return false;
            }

            if (this.brandType == 1) {
                this.selectList = [];
            } else {
                this.selectAutoList = [];
            }
            if (this.type != 1) {
                this.createdRes = "";
            } else {
                if (this.autoType == 1) {
                    this.uploadRes = "";
                } else {
                    this.createdRes = "";
                }
            }
            sessionStorage.setItem(
                "brandInfo",
                JSON.stringify({
                    name: this.name,
                    uploadRes: JSON.stringify(this.uploadRes),
                    createdRes: JSON.stringify(this.createdRes),
                    type: this.type,
                    brandType: this.brandType,
                    autoType: this.autoType,
                    doMain: JSON.stringify(this.doMain),
                    doMainIndex: this.doMainIndex,
                    doMainchildIndex: this.doMainchildIndex,
                    selectAutoList: JSON.stringify(this.selectAutoList),
                    selectList: JSON.stringify(this.selectList),
                    trademarkList: JSON.stringify(this.trademarkList),
                    trademarkListAll: JSON.stringify(this.trademarkListAll)
                })
            );

            this.$router.replace("/order/ownermsg");
        }
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
<style scoped>
@import url("../../assets/css/order.css");
.red {
    color: red;
}
</style>
