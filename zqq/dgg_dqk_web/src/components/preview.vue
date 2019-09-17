<template>
    <transition name="fadeIn">
        <div class="masking">
            <div class="previewContent">
                <div class="title2">
                    <p>
                        <span class="fl f16"><i class="col">{{nameStr}}</i>条件组</span>
                        <span class="el-icon-close icon f20 cur" title="关闭" @click="close"></span>
                    </p>
                </div>
                <div class="ccc" >
                    <div class="maskContent"></div>
                    <div class="title">
                        <i class="el-icon-warning col"></i>
                        已选择&nbsp;<span class="col">{{docArr[docKey].length}}</span>&nbsp;个筛选条件
                    </div>
                    <div class="all">
                        <docEle :docIdx="'fr'" :condiType="docKey" :firBox="true" :filterJson='docArr' :isPreview='true'></docEle>
                        <div class="sec">
                            <template v-if='docArr[docKey].length > 0'>
                                <template v-for='(item,idx) in docArr[docKey]'>
                                    <div class="secBox"  v-if="item.must || item.should" :key='idx' >
                                        <span class="line1"></span>
                                        <span class="line2"></span> 
                                        <docEle :docIdx="idx" :condiType="docKey" :filterJson='docArr' :isPreview='true'
                                            :condiTypeChild=" item.must ? 'must': 'should' " ></docEle>
                                    </div>
                                </template>
                            </template>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>
<script>
import docEle from '@/components/docEle'
export default {
    components: {
        docEle
    },
    props: {
        nameStr: {
            default: ""
        },
        json:{
            default:{}
        }
    },
    data() {
        return {
            numID: "", //Iboos编号
            person: "", //负责人
            department: "", //负责人部门
        };
    },
    computed:{
        docArr(){
                // SET_FILTER
            return this.json
        },
        docKey(){
            return this.json.must ? 'must' :'should'
        }, 
    },
    mounted() {
      
    },
    methods: {
       close(){
           this.$emit('changeSHow',false)
       }
    }
};
</script>
<style scoped lang="stylus">
.masking {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    z-index: 130;
    line-height: 3em;
}
.previewContent{
    position: fixed;
    left: 50%;
    top: 50%;
    margin-left: -500px;
    transform: translateY(-50%);
    width: 1000px;
    height 80%;
    overflow hidden
    padding: 20px;
    background: #fff;
    box-shadow: 0 0 5px 1px rgba(9, 9, 10, 0.1);
    border-radius: 4px;
    z-index: 132;
    .ccc{
        position relative;
        height calc(100% - 20px);
        overflow-y auto;
    }
    .title2{
        >p{
            height 46px;
            line-height 46px;
            border-bottom 1px solid #eee;
            text-align right ;
            padding 0 20px;
        }
    }
}
    .maskContent{
        position absolute;
        background-color: transparent;
        left 0;
        top 0;
        width 100%;
        height 100%;
        z-index: 122;
    }
.title {
        border-color: #cfe9ff;
        background-color: #ebf6ff;
        font-family: \\5FAE\8F6F\96C5\9ED1, Microsoft Yahei, Hiragino Sans GB, tahoma, arial, \\5B8B\4F53;
        font-size: 13px;
        font-variant: tabular-nums;
        line-height: 1.5;
        color: #5a5a5a;
        box-sizing: border-box;
        margin: 0;
        padding: 0;
        list-style: none;
        position: relative;
        padding: 8px 15px 8px 37px;
        border-radius: 3px;
        margin-bottom: 30px;
        margin-top 20px;
    }

.all {
    .sec{
        .secBox:last-child{
                border-left:none;
            .line1{
                width: 1px;
            }
        }
        
    }
    .secBox{
        padding 20px;
        margin-left 20px;
        position relative;
        padding-left: 40px;
        border-left:1px solid  #e2e2e2;
        .line1{
            position: absolute;
            top: 0;
            left: 0;
            height: 34px;
            width: 0px;
            background-color: #e2e2e2;
        }
        .line2{
            display: inline-block;
            width: 40px;
            height: 1px;
            background-color: #e2e2e2;
            position absolute;
            top: 34px;
            left: 0;
        }
    }
    .addBtn{
        margin: 30px 62px 10px; 
        position:relative;
        width:126px;
        .more{
            position:absolute;
            display inline-block
            width 32px;
            height 32px;
            border-radius:0 4px 4px 0;
            border: 1px solid #dcdfe6;
            margin-left:-2px;
            text-align:center;
            line-height:24px;
            cursor pointer   
        }
        &:hover{
            border-color: #409EFF;
            color: #409EFF;
        } 
        ul{
            position:absolute;
            width:100%;
            line-height:2em;
            outline: none;
            list-style-type: none;
            padding: 4px 0;
            margin: 0;
            text-align: left;
            background-color: #fff;
            border-radius: 3px;
            box-shadow: 0 2px 8px rgba(0,0,0,.15);
            background-clip: padding-box;
            z-index:4
            li{
                padding: 5px 12px;
                margin: 0;
                clear: both;
                font-size: 13px;
                font-weight: 400;
                color: #5a5a5a;
                white-space: nowrap;
                cursor: pointer;
                transition: all .3s;
                line-height: 22px;
                &:hover{
                        background-color: #ebf6ff;
                }
            }
        }
    }
}
</style>
