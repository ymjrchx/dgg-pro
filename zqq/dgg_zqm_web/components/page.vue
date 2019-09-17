<template>
    <div class="paging" v-if="totalPage">
        <ul class="pagination">
            <li class="prev">
                <a @click="jumpPage(1)">首页</a>
            </li>
            <li class="prev">
                <a @click="Page(-1)" v-if='current_page>1'>上一页</a>
                <span v-else>上一页</span>
            </li>

            <li v-show="efont">
                <span style="font-size:12px;">••••</span>
            </li>

            <li v-for="(num,idx) in indexs" class="pagesNum" :class="{'active':current_page==num}" :key='idx'>
                <a @click="jumpPage(num)">{{num}}</a>
            </li>

            <li v-show="(efont || allPage > 7) && current_page < allPage-4">
                <span style="font-size:12px;">••••</span>
            </li>
            <li class="next">
                <a @click="Page(1)" v-if="current_page < allPage">下一页</a>
                <span v-else>下一页</span>
            </li>
            <li class="prev">
                <a @click="jumpPage(allPage)">尾页</a>
            </li>
            <li style="height:26px;color:#777;margin-left:52px;background:#fff">到第</li>
            <input type="text" @keyup.enter="jumpPage2" v-model="changePage" class="j-input">
            <input class="j-btn" type="button" value="确定" @click="jumpPage2">
        </ul>
    </div>
</template>
<script>
//  <pagination :allPage="total" @pagechange="pagechange" />    allPage总条数  pagechange函数名返回一个参数第几页
export default {
    props: {
        totalPage: {
            type: Number,
            default: 1
        },
        pageSize: {
            type: Number,
            default: 10
        },
        pageNum: {
            type: Number,
            default: 1
        }
    },
    data() {
        return {
            current_page: this.pageNum,
            changePage: "", //跳转页
            nowIndex: 0,
            allPage: 1
        };
    },
    mounted() {
        this.allPage = Math.ceil(this.totalPage / this.pageSize);
    },
    watch: {
        pageNum() {
            this.current_page = this.pageNum;
        },
        totalPage() {
            this.allPage = Math.ceil(this.totalPage / this.pageSize);
        }
        // current_page() {
        //     this.$emit("pagechange", this.current_page);
        // }
    },
    computed: {
        efont() {
            if (this.allPage <= 7) return false;
            return this.current_page > 5;
        },
        indexs() {
            var left = 1,
                right = this.allPage,
                arr = [];
            if (this.allPage >= 7) {
                if (
                    this.current_page > 5 &&
                    this.current_page < this.allPage - 4
                ) {
                    left = Number(this.current_page) - 3;
                    right = Number(this.current_page) + 3;
                } else {
                    if (this.current_page <= 5) {
                        left = 1;
                        right = 7;
                    } else {
                        right = this.allPage;

                        left = this.allPage - 6;
                    }
                }
            }
            while (left <= right) {
                arr.push(left);
                left++;
            }
            return arr;
        }
    },
    methods: {
        Page(num) {
            this.current_page += num;
            this.$emit("pagechange", this.current_page);
        },
        jumpPage(num) {
            this.current_page = num;
            this.$emit("pagechange", num);
        },
        jumpPage2() {
            let pages = Number(this.changePage);
            if (isNaN(pages) || pages < 1) this.changePage = "";
            else {
                if (pages <= this.allPage) {
                    this.current_page = pages;
                    this.$emit("pagechange", pages);
                } else {
                    this.changePage = this.current_page = this.allPage;
                    this.$emit("pagechange", this.allPage);
                }
            }
        }
    }
};
</script>
<style scoped>
.paging{
  margin: 40px auto;
}


.paging a {
  color: #777
}

.paging .pagination {
  display: inline-block;
  font-size: 0
}

.paging .pagination li {
  display: inline-block;
  margin: 0 3px;
  font-size: 14px;
  background: #eee
}

.paging .pagination li a {
  display: block;
  border: solid 1px #e5e5e5;
  height: 42px;
  line-height: 42px;
  padding: 0 10px;
  text-align: center
}

.paging .pagination li span {
  display: block;
  border: solid 1px #e5e5e5;
  height: 42px;
  line-height: 42px;
  padding: 0 16px;
  text-align: center
}

.paging .pagination li a:hover {
  background-color: #1c8bee;
  color: #fff
}

.paging .pagination .active a {
  background-color: #1c8bee;
  color: #fff
}

.paging .jump-page {
  display: inline-block;
  font-size: 0;
  margin-left: 50px
}

.paging .jump-page li {
  font-size: 14px
}

.paging .j-input {
  width: 60px;
  height: 30px;
  margin: 0 8px;
  border: solid 1px #e5e5e5;
  text-align: center
}

.paging .j-btn {
  display: inline-block;
  width: 65px;
  height: 42px;
  line-height: 42px;
  margin-left: 8px;
  border: solid 1px #1c8bee;
  background: none;
  cursor: pointer;
  vertical-align: top;
  text-align: center;
  background-color: #1c8bee;
  color: #fff
}

.paging .j-btn:hover {
  background: #1c8bee;
  color: #fff
}
.paging .pagination li.pagesNum {
    min-width: 54px;
    text-align: center;
}
.paging .j-input {
    width: 60px;
    height: 42px;
    margin: 0 8px;
    border: solid 1px #e5e5e5;
    text-align: center;
}
</style>
