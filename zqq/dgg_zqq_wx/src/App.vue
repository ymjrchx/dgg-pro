<script>
export default {
    methods: {},
    created() {
        let that = this;
        
        // wx.login({
        //   success (res) {
        //     if (res.code) {
        //         console.log(res.code)
        //       //发起网络请求
        //       that.$Http('/wechatApp/login',
        //         {
        //           code: res.code
        //         }).then(() => {
                    
        //         })
               
        //     } else {
        //       console.log('登录失败！' + res.errMsg)
        //     }
        //   }
        //})
    }
};
</script>

<style>
/*在这里引入全局公共的CSS,并且style标签不加scoped,所有页面都有效*/
@import "~@/styles/common.css";
</style>
