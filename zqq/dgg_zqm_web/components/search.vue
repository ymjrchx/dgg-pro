<template>
  <div class="search">
      <div class="box">
        <div class="title">
          <span
            v-for="(item, index) in titles"
            @click="changTitle(index)"
            :class="{'atthis': index == seletedIndexChild}"
            :key="item.name"
          >{{item.name}}</span>
        </div>
         <input type="text" :placeholder="titles[seletedIndexChild].placeholder" v-model="keywordChild" @keyup.enter='submit'>
            <button @click="submit">查一下</button>
        <div class="hot">
          热搜：<a
            :href="`/gs/0_${item}`"
            v-for="item in keyWords"
            :key="item"
          >{{item}}</a>
          <span
            @click="switchHot"
            :class="{'rotate': isLoad}"
          ><img
              src="../assets/images/index/icon_switch.png"
              alt="换一批"
            >换一批</span>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  props: {
    seletedIndex: {
      default: 0
    },
    keyWord: {
      default: ''
    }
  },
  data () {
    return {
      isLoad: false,
      seletedIndexChild: this.seletedIndex,
      keywordChild: this.keyWord,
      keyIndex: 1,
      keyWords: ["顶峰知产","顶联互动信息","京东电子商务","小米科技","淘宝网络"],
      keyWordsArr: ["顶峰知产","顶联互动信息","京东电子商务","小米科技","淘宝网络","顶呱呱","百度","头条","阿里云","张小龙","黄建明","北京市朝阳区东三环北路38号民生大厦21层","律师事务所","呱呱"],
      titles: [
          {
            name: "全部",
            placeholder: "请输入企业名称、人名、地址等,，如“腾讯马化腾”"
          },
          {
            name: "企业名",
            placeholder: "请输入企业名称，如“腾讯”"
          },
          {
            name: "主要成员",
            placeholder: "请输入人名，如“马化腾”"
          },
          // {
          //   name: "高管",
          //   placeholder: "请输入人名，如“张小龙”"
          // },
          // {
          //   name: "品牌/产品",
          //   placeholder: "请输入品牌/产品名称,，如“QQ”"
          // },
          // {
          //   name: "地址/电话",
          //   placeholder: "请输入地址/电话，如“13888888888”"
          // },
          {
            name: "经营范围",
            placeholder: "请输入经营范围，如“道具”"
          }
        ],
    }
  },
  computed:{

  },
  methods: {
    submit(){
      let str =this.keywordChild
        if (/[`~!@#$%^&*_\-+=<>?:"\/'\\[\]·~！@#￥%……&*——\-+=？：.]/im.test(str)) {
            this.$popup.created({
                content: "关键字不能包含特殊字符",
                time: 2000
            });
            return;
          }
        if(!this.$store.state.userInfo){
          this.$loginPop(this)
          return
        }
        if(str){
          this.$router.push(encodeURI(`/gs/${this.seletedIndexChild}_${str}`))
        }
    },
    switchHot() {
      this.isLoad = true;
      setTimeout(() => {
        this.isLoad = false;
        this.keyIndex++
        if (this.keyIndex > 3) {
          this.keyIndex = 1
        }
        this.keyWords = this.keyWordsArr.slice(0+(this.keyIndex-1)*5, 5*this.keyIndex)
      }, 1000);
    },
    changTitle(index) {
      this.seletedIndexChild = index;
    },
  }
}
</script>

<style lang="stylus" scoped>
@import '../assets/css/params.styl';
.search 
  width: 1200px;
  position: absolute;
  left: 50%;
  margin-left: -600px;
  top: 420px;
  z-index: 2;
.box 
  position: relative;
  border-radius: 40px 0 40px 0;
  background: #fff url('../assets/images/index/bg_search.png') no-repeat 1030px -15px;
  box-sizing: border-box;
  padding: 35px 70px;
  margin-top: -70px;
  font-size: 0;
  box-shadow: 0 5px 15px 0 $shadowcolor;
  .title 
    color: #2089e7;
    margin-bottom: 10px;
    span 
      font-size: 14px;
      margin-right: 15px;
      padding: 0 10px;
      border-radius: 3px;
      cursor: pointer;
      line-height: 30px;
      display: inline-block;
      position: relative;
      &:after 
        position: absolute;
        content: '';
        width: 0;
        height: 0;
        border: 5px solid transparent;
        border-top-color: #2898fc;
        left: 50%;
        bottom: -10px;
        margin-left: -5px;
        display: none;
      &.atthis 
        background: #2898fc;
        color: #fff;
        &.atthis:after 
          display: block;
  input 
    clear: both;
    width: 880px;
    height: 52px;
    line-height: 52px;
    float: left;
    text-indent: 10px;
    box-sizing border-box
    border: 1px solid $maincolor;

  button 
    background: $maincolor;
    color: #fff;
    float: left;
    font-size: 18px;
    width: 160px;
    height: 52px;
    line-height: 52px;
    cursor: pointer;
  .hot 
    overflow: hidden;
    clear: both;
    font-size: 12px;
    color: #999;
    padding-top: 15px;
    line-height: 30px;
    a 
      color: #999;
      margin-right: 10px;
    span 
      display: inline-block;
      cursor: pointer;
      margin-left: 15px;
      img 
        margin-right: 5px;
        margin-top: -1px;
      &.rotate 
        img 
          animation: rotate 1s infinite;

</style>
