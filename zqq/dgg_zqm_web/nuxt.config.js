const pkg = require('./package')

module.exports = {
  mode: 'universal',

  /*
  ** Headers of the page
  */
  head: {
    title: '智企名',
    meta: [{
        charset: 'utf-8'
      },
      {
        name: 'viewport',
        content: 'width=device-width, initial-scale=1'
      },
      {
        hid: 'keywords',
        name: 'keywords',
        content: '智企名，企业起名，企业核名'
      },
      {
        hid: 'description',
        name: 'description',
        content: '智企名 专注公司起名、核名的网站'
      },
      {
        'http-equiv': 'X-UA-Compatible',
        'content': 'IE=edge,Chrome=1'
      }
    ],
    link: [{
      rel: 'icon',
      type: 'image/x-icon',
      href: '/favicon.ico'
    }
    ],
    script: [{
        async: "async",
        src: "https://s96.cnzz.com/z_stat.php?id=1275783418&web_id=1275783418"
    },{
      src: "http://php.dgg.net/kefu/js/kefu3-xdw.js"
    }],
  },
  ErrorPage: './layouts/error.vue',
  /*
  ** Customize the progress-bar color
  */
  loading: { color: '#1c8bee' },

  /*
  ** Global CSS
  */
  css: [
    '@/assets/css/common.css'
  ],
  
  /*
  ** Plugins to load before mounting the App
  */
  plugins: [
    '~/plugins/axios'
  ],
  /*
  ** Nuxt.js modules
  */
  modules: [
    // Doc: https://github.com/nuxt-community/axios-module#usage
    '@nuxtjs/axios'
  ],
  /*
  ** Axios module configuration
  */
  axios: {
    // See https://github.com/nuxt-community/axios-module#options

  },
  // proxy: {
  //   '/api': { target: 'http://192.168.254.166:8065'}
  // },
  render: {
    resourceHints: false // 关闭预加载
  },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** You can extend webpack config here
    */
    extend(config, ctx) {
      // Run ESLint on save
      if (ctx.isDev && ctx.isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/
        })
      }
    },
    extractCSS: true,
  }
}
