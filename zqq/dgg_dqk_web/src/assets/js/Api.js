import axios from 'axios'
import { Message , MessageBox } from 'element-ui';

import Cookies from "js-cookie"
import qs from 'qs'
import popup from "@/components/popup"; //loading
import router from '../../router'

var fn; //定义的一个 this 对象

var IsFrom = null;
var secApi=null; //二次请求
var requireNUm = 0;
///定义fetch函数，config为配置
// const baseUrl = 'http://172.16.2.71:8090/' //江胜辉
// const baseUrl = 'http://172.16.0.57:8090/' //赵
// const baseUrl = 'http://172.16.3.83:8090/'  //万国

const baseUrl = 'http://localhost:8090/' //测试地址

const fetch = async function (config) {
    let loadBox = null;
    if (config.loading) {
        loadBox = popup.created()
        delete config.loading
    }

    if (!Cookies.get('token')) {
        let {
          data
        } = await axios.post(baseUrl + 'sysUser/session/gettoken')
        if (data.code == 0) {
           
          Cookies.set('token', data.data);
        }
    }
    return new Promise((resolve, reject) => {
        //创建axios实例，把基本的配置放进去
        const instance = axios.create({
            headers: {
                'Content-Type': IsFrom ? 'application/x-www-form-urlencoded' :'application/json' ,
                'token': Cookies.get('token'),
                'Authorization':Cookies.get('userInfo') ?  JSON.parse(Cookies.get('userInfo')).userId : ""
            },
            timeout: 120000,
            baseURL: baseUrl
        });
        instance(config).then(res => {
            IsFrom = null
            if (loadBox) {
                loadBox.close()
            }
            if (res.data.code == 0) {
                resolve(res.data);
            } 
            else if(res.data.code == 2){
               
                let obj =JSON.parse(Cookies.get('userInfo'));
                    obj.userId = res.data.data.newToken
                    Cookies.set('userInfo',obj);
              
                fetch(secApi).then(res =>{
                    resolve(res);
                })
              
            }
            else if(res.data.code == 3){
                if(requireNUm != 0) return;
                    requireNUm++
                    Cookies.remove('userInfo');

                MessageBox.confirm(res.data.msg, '温馨提示', {
                    confirmButtonText: '确定',
                    showClose:false,
                    closeOnClickModal:false,
                    closeOnPressEscape:false,
                    showCancelButton:false,
                    type: 'error'
                  }).then(() => {
                    router.replace('/')
                    requireNUm = 0
                  }).catch(() => {
                 
                  });
              
            }
            else {
                reject(res.data)
                if(fn){
                    fn(res.data);
                    fn = null;
                    return;
                }
                console.log('请求code不为0', config.url, res.data)
                Message({
                    showClose: true,
                    message: res.data.msg,
                    type: 'error',
                    time:1500
                });
            }
        }).catch(err => {
            IsFrom = null
            if (loadBox) {
                loadBox.close()
            }
            Message({
                showClose: true,
                message: '请求失败，请稍后再试',
                type: 'error'
            });
            console.log(err)
        })
    });
}

// 封装调用的接口  如果type为空，默认为get,是否出现loading, from是否表单提交 
export default function Http(url, data, type, loading, from ,fn2) {

    if (fn2) fn = fn2;

    let requireUrl = url,
        requireData = data;
    if (!type || type == 'get') {  //(get的时候有问题，会出现中文乱码的情况，下次用的时候注意封装)
        type = 'get'
        let str = '';
        for (let key in data) {
            if (data[key] !== '') {
                str += key + '=' + data[key] + "&";
            }
        }
        requireUrl = url + "?" + str.slice(0,str.length-1);
        requireData = {};
    }
    if (from){
        IsFrom = true;   
        requireData = qs.stringify(requireData)
    }

    secApi = {
        url: requireUrl,
        method: type,
        data: requireData,
        loading:loading
    }

    return fetch(secApi)
}
