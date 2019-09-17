const {
  Nuxt,
  Builder
} = require('nuxt')
const bodyParser = require('body-parser')
const session = require('express-session')
const app = require('express')()
const NuxtConfig = require('./nuxt.config')
const request = require('request')
const baseURI = NuxtConfig.env.baseUrl

// Body parser，用来封装 req.body
app.use(bodyParser.json())
// Sessions 来创建 req.session
app.use(session({
  secret: 'super-secret-key',
  resave: false,
  saveUninitialized: false,
  cookie: {
    maxAge: 2 * 60 * 60 * 1000
  }
}))

app.use(function (req, res, next) {
  if (req.url.indexOf('/classify/all_') > -1) {
    let id = req.url.split('_')[1].split('.')[0]
    res.redirect('/classify/' + id + '.html')
  }
  if (req.url.indexOf('/patentSearch/list_') > -1) {
    let word = req.url.split('_')[1].split('.')[0]
    res.redirect('/patentSearch/' + word + '.html')
  }
  if (req.url == '/list/searchPage') {
    res.redirect('/shangbiao/freesearch')
  }

  next()
})

// 快捷登录获取信息 进行一次缓存
app.post('/server/loginSession', function (req, res) {

  let requireObj = req.body;
  req.session.authUser = {
    nickname: requireObj.nickname,
    userId: requireObj.userId,
    phoneNo: requireObj.phoneNo
  }
  if (requireObj.time == 7) {
    req.session.cookie.originalMaxAge = 7 * 24 * 60 * 60 * 1000
  }
  return res.json('OK')
})

// 发起 POST /server/logout 请求注销当前用户，并从 req.session 中移除
app.post('/server/logout', function (req, res) {
  delete req.session.authUser
  res.json({
    ok: true
  })
})

// 我们用这些选项初始化 Nuxt.js：
const isProd = process.env.NODE_ENV === 'production'
const nuxt = new Nuxt(NuxtConfig)
// 生产模式不需要 build
if (!isProd) {
  const builder = new Builder(nuxt)
  builder.build()
}
app.use(nuxt.render)
app.listen(8050)
console.log('Server is listening ' + baseURI)
