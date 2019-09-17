const resolve = require('path').resolve

module.exports = {
  /*
   ** Headers of the page
   */
  head: {
    title: '知千秋',
    meta: [{
        charset: 'utf-8'
      },
      {
        name: 'viewport',
        content: 'width=device-width, initial-scale=1'
      },
      {
        hid: 'description',
        name: 'description',
        content: '知千秋致力于知千秋致力于运营大数据技术、人工智能等技术重新定义知识产权生态链。提供商标查询,商标注册,专利申请,专利查询,版权登记全流程服务。提供免费商标查询Saas工具,智能商标注册0服务费省钱到底,商标查询,商标注册监控,专利申请等服务获得客户的高度认可'
      },
      {
        'http-equiv': 'X-UA-Compatible',
        'content': 'IE=edge,Chrome=1'
      }
    ],
    script: [{
      src: "https://s23.cnzz.com/z_stat.php?id=1275873214&web_id=1275873214",
      language:"JavaScript",
      defer: "defer",
      id:"zztj"
    }],
    link: [{
      rel: 'icon',
      type: 'image/x-icon',
      href: '/logo.png'
    }]
  },
  /*
   ** Customize the progress bar color
   */
  loading: {
    color: '#fd7d22'
  },
  env: {
    // baseUrl: 'http://172.16.2.71:8099/' //江胜辉
    // baseUrl: 'https://www.zhiqianqiu.com/api/' //后台地址
    baseUrl: 'http://172.16.0.57:8099/' //赵
    // baseUrl: 'http://172.16.0.116:8099/' //后台地址
    // baseUrl: 'http://172.16.2.36:8099/' //刘阳
    //  baseUrl: 'http://192.168.254.166:8099/' //测试地址
     ,
    webSite: 'https://www.zhiqianqiu.com' //地址
  },
  plugins: ['~plugins/veeValidate','~plugins/axios'],
  /*
   ** Build configuration
   */
  build: {
    /*
     ** Run ESLint on save
     */
    extend(config, {
      isDev,
      isClient,
    }) {
      if (isDev && isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/,
        })
        config.entry.vendor.push('babel-polyfill') //兼容ie 
      }
    },
    extractCSS: {
      allChunks: true
    }
  },
  //全局的css
  css: [
    'assets/css/base.css', 'assets/css/common.css', 'assets/css/amimation.css', 'assets/css/page.css'
  ],
  // 静态生成
  generate: {
    minify: {
      collapseBooleanAttributes: true,
      collapseWhitespace: false,
      decodeEntities: true,
      minifyCSS: true,
      minifyJS: true
    }
  },
  render: {
    resourceHints: false // 关闭预加载
  },
  // 自定义路由
  router: {
    extendRoutes(route) {
      route.push({
        name: 'custom',
        path: '*',
        component: resolve(__dirname, 'pages/404.vue')
      })
    }
  }
}
