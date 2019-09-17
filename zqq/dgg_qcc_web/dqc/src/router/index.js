import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  // mode: 'history', //去掉#
  scrollBehavior: () => ({
    y: 0
  }),
  routes: [{
      path: '/',
      name: 'MainPage',
      component: resolve => require(['@/pages/main'], resolve), //主页+路由
      children: [{ //不带搜索框的头部
          path: '/',
          name: 'Body',
          component: resolve => require(['@/pages/Body'], resolve), //导航+路由
          children: [{
              path: '/',
              name: 'Body2',
              component: resolve => require(['@/pages/Body2'], resolve), //主内容
              // meta: { keepAlive: true },//当前的.vue文件需要缓存
            },
            {
              path: '/Qualifications_box',
              name: 'Qualifications_box',
              component: resolve => require(['@/pages/Qualifications_box'], resolve), //资质 页面盒子
              redirect: '/Qualifications_company',
              children: [{
                path: '/Qualifications_company',
                name: 'Qualifications_company',
                component: resolve => require(['@/pages/Qualifications_company'], resolve), //企业资质
              }, {
                path: '/Qualifications_resume',
                name: 'Qualifications_resume',
                component: resolve => require(['@/pages/Qualifications_resume'], resolve), //挂靠简历
              }]
            }, {
              path: '/Qualifications_company_d',
              name: 'Qualifications_company_d',
              component: resolve => require(['@/pages/Qualifications_company_d'], resolve), //企业资质详情
            },
            {
              path: '/Qualifications_resume_d',
              name: 'Qualifications_resume_d',
              component: resolve => require(['@/pages/Qualifications_resume_d'], resolve), //挂靠简历详情
            }
          ]
        },
        { //带搜索框的头部
          path: '/SearchResult',
          name: 'SearchResult',
          component: resolve => require(['@/pages/BodySearch'], resolve), //主线 —— 搜索导航+路由
          redirect: "/SearchList",
          children: [{
              path: '/SearchList',
              name: 'SearchList',
              component: resolve => require(['@/pages/SearchList'], resolve), //主线 —— 搜索导航+list  
              redirect: "/Company",
              children: [{
                path: '/Company',
                name: 'Company',
                component: resolve => require(['@/components/search_three_modul/Company'], resolve), //主线 —— 搜索列表list-公司
              }, {
                path: '/Knowledge',
                name: 'Knowledge',
                component: resolve => require(['@/components/search_three_modul/Knowledge'], resolve), //主线 —— 搜索列表list-知识产权
              }, {
                path: '/Risk',
                name: 'Risk',
                component: resolve => require(['@/components/search_three_modul/risk'], resolve), //主线 —— 搜索列表list-风险信息
              }]
            },
            {
              path: '/Company_detail',
              name: 'Company_detail',
              component: resolve => require(['@/pages/Company_detail'], resolve), //主线 —— 搜索导航+公司详情
            },
            {
              path: '/Industry',
              name: 'Industry',
              component: resolve => require(['@/pages/Industry'], resolve), //按行业查公司
            },
            {
              path: '/NewsIndex',
              name: 'NewsIndex',
              component: resolve => require(['@/pages/news/NewsIndex'], resolve), //新闻主页
            },
            {
              path: '/NewsDetail',
              name: 'NewsDetail',
              component: resolve => require(['@/pages/news/NewsDetail'], resolve), //新闻详情
            }
          ]
        },
        { //登录注册
          path: '/User',
          name: 'User',
          component: resolve => require(['@/pages/login_reg/index'], resolve), //登录注册
          children: [{
            path: '/Login',
            name: 'Login',
            component: resolve => require(['@/pages/login_reg/Login'], resolve), //登录
          }, {
            path: '/Register',
            name: 'Register',
            component: resolve => require(['@/pages/login_reg/Register'], resolve), //注册
          }, {
            path: '/LosePwd',
            name: 'LosePwd',
            component: resolve => require(['@/pages/login_reg/LosePwd'], resolve), //忘记密码
          }]
        },
        { //协议
          path: '/Agreement',
          name: 'Agreement',
          component: resolve => require(['@/pages/login_reg/Agreement'], resolve), //注册协议
        },
        { //demo 测试
          path: '/Demo',
          name: 'Demo',
          component: resolve => require(['@/components/Lunbo2.vue'], resolve), //做测试的页面******************************************************************************
        },
        { //接口 API
          path: '/Api',
          name: 'Api',
          component: resolve => require(['@/pages/api/Index'], resolve), //接口Api
          redirect: '/DataApi',
          children: [{
              path: '/DataApi',
              name: 'DataApi',
              component: resolve => require(['@/pages/api/Tab_content'], resolve), //接口Api-data选项页  
            },
            {
              path: '/Apikey',
              name: 'Apikey',
              component: resolve => require(['@/pages/api/Apikey'], resolve), //接口 详情  
            }
          ]
        },
        { //关于我们
          path: '/About',
          name: 'About',
          component: resolve => require(['@/pages/About'], resolve), //关于我们
        },
        { //税号_专利_商标 盒子
          path: '/TaxIndex',
          name: 'TaxIndex',
          component: resolve => require(['@/pages/tax_brand_patent/index'], resolve), //税号_专利_商标-有头部
          redirect: '/TaxSearch',
          children: [{
              path: '/Search',
              name: 'Search',
              component: resolve => require(['@/pages/Search'], resolve), //税号_专利_商标 搜索页面
            },
            { //税号内容左右
              path: '/TaxContent',
              name: 'TaxContent',
              component: resolve => require(['@/pages/tax_brand_patent/TaxContent'], resolve), //税号_专利_商标 - 带右边推送框
              redirect: "/TaxList",
              children: [{ //税号列表
                  path: '/TaxList',
                  name: 'TaxList',
                  component: resolve => require(['@/pages/tax_brand_patent/TaxList'], resolve), //税号列表
                },
                { //商标列表
                  path: '/BrandList',
                  name: 'BrandList',
                  component: resolve => require(['@/pages/tax_brand_patent/BrandList'], resolve), //税号列表
                },
                { //专利列表
                  path: '/PatentList',
                  name: 'PatentList',
                  component: resolve => require(['@/pages/tax_brand_patent/PatentList'], resolve), //税号列表
                },
                {
                  path: '/Brand_detail',
                  name: 'Brand_detail',
                  component: resolve => require(['@/pages/tax_brand_patent/Brand_detail'], resolve), //商标详情 
                },
                {
                  path: '/Patent_detail',
                  name: 'Patent_detail',
                  component: resolve => require(['@/pages/tax_brand_patent/Patent_detail'], resolve), //专利详情 
                }
              ]
            },

          ]
        },
        { //裁判文书-简单版
          path: '/lawIndex',
          name: 'lawIndex',
          component: resolve => require(['@/pages/easy_law/index'], resolve), //裁判文书-有头部-简单版
          redirect: '/LawSearch',
          children: [{
              path: '/LawSearch',
              name: 'LawSearch',
              component: resolve => require(['@/pages/easy_law/easy_law_search'], resolve), //裁判文书-查询-简单版
            },
            {
              path: '/lawContent',
              name: 'lawContent',
              component: resolve => require(['@/pages/easy_law/easy_law_content'], resolve), //裁判文书-内容左右-简单版
              redirect: "/lawList",
              children: [{
                  path: '/lawList',
                  name: 'lawList',
                  component: resolve => require(['@/pages/easy_law/easy_law_list'], resolve), //裁判文书-列表-简单版
                },
                {
                  path: '/lawDetail',
                  name: 'lawDetail',
                  component: resolve => require(['@/pages/easy_law/easy_law_detail'], resolve), //裁判文书-详情-简单版 
                },
              ]
            }
          ]
        },
        { //裁判文书-工具
          path: '/lawTool',
          name: 'lawTool',
          component: resolve => require(['@/pages/law/index'], resolve), //裁判文书-有头部-工具
          redirect: '/LawToolSearch',
          children: [{
              path: '/LawToolSearch',
              name: 'LawToolSearch',
              component: resolve => require(['@/pages/law/LawSearch'], resolve), //裁判文书-查询-工具
            },
            {
              path: '/lawToolContent',
              name: 'lawToolContent',
              component: resolve => require(['@/pages/law/LawContent'], resolve), //裁判文书-内容左右-工具
            },
            {
              path: '/lawToolDetail',
              name: 'lawToolDetail',
              component: resolve => require(['@/pages/law/lawToolDetail'], resolve), //裁判文书-详情
            }
          ]
        },
        { //用户中心
          path: '/userCenter',
          name: 'userCenter',
          component: resolve => require(['@/pages/userCenter/index'], resolve), //用户中心
          redirect: "/Msg",
          children: [{
              path: '/Msg',
              name: 'Msg',
              component: resolve => require(['@/pages/userCenter/msg'], resolve), //用户消息
            },
            {
              path: '/follow',
              name: 'follow',
              component: resolve => require(['@/pages/userCenter/follow'], resolve), //用户关注
            },
            {
              path: '/MyData',
              name: 'MyData',
              component: resolve => require(['@/pages/userCenter/data/myData'], resolve), //用户中心-我的数据
            },
            {
              path: '/ApplyData',
              name: 'ApplyData',
              component: resolve => require(['@/pages/userCenter/data/ApplyData'], resolve), //用户中心-申请数据
            }
            // {
            //   path: '/Collection',
            //   name: 'Collection',
            //   component: resolve => require(['@/pages/userCenter/user/coll'], resolve),   //用户中心-我的收藏
            // }
          ]
        },
        // import Verify_company from '@/pages/Verify_company' //公司核名

        { //公司核名
          path: '/Verify_company',
          name: 'Verify_company',
          component: resolve => require(['@/pages/Verify_company'], resolve), //公司核名
        },
        { //公司经营范围
          path: '/CompanyScop',
          name: 'CompanyScop',
          component: resolve => require(['@/pages/CompanyScop'], resolve), //公司核名
        },
      ]
    },
    {
      path: '/404',
      name: '404',
      component: resolve => require(['@/pages/error/404'], resolve), //404页面
    }
  ]
})
