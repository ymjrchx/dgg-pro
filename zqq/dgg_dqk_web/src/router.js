import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

Vue.use(Router)


export default new Router({
  scrollBehavior: () => ({
    y: 0
  }),
  // mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
     {
       path: '/users/agreement',
        name: 'users/agreement',
        component: () => import('@/views//users/agreement'), //协议
     },
     {
       path: '/passport/register',
       name: 'passport/register',
       component: () => import('@/views/passport/register')  //注册
     },
    {
      path: '/main',
      name: 'main',
      component: () => import( /* webpackChunkName: "about" */ './views/Main.vue'),
      redirect: '/workBeach',
      children: [
          {
            path: '/workBeach',
            name: 'workBeach',
          component: () => import('@/views/workBeach')  //工作台
          },
        {
          path: '/seek/company',
          name: 'seek/company',
          component: () => import('@/views/seek/company') //企业查询
        },
        {
          path: '/seek/companyInfo',
          name: 'seek/companyInfo',
          component: () => import('@/views/seek/companyInfo') //企业详情
        },
        {
          path: '/seek/recommond',
          name: 'seek/recommond',
          component: () => import('@/views/seek/recommond') //智能推荐
        },
        {
          path: '/seek/advanced_filter',
          name: 'seek/advanced_filter',
          component: () => import('@/views/seek/advanced_filter') //高级筛选
        },
      ]
    },
    {
      path: '/center',
      name: '/center',
      component: () => import('@/views/center'),  //个人中心
      redirect: '/center/main',
      children: [
        {
          path: '/center/main',
          name: 'center/main',
          component: () => import('@/views/center/main')  //工作台
        },
      ]
    },
    {
      path: '/404',
      name: '404',
      component:  () => import('@/views/404.vue'), //404页面
    },
    {
      path: "*",
      redirect: "/404"
    }
  ]
})
