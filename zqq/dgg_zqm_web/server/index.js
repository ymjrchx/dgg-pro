const session = require('express-session')
const bodyParser = require('body-parser')
const express = require('express')
const consola = require('consola')
const { Nuxt, Builder } = require('nuxt')
const app = express()
const port = process.env.port || 3000

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

//  缓存token
app.post('/server/token', function (req, res) {
  req.session.serverToken = {
    token: req.body.token
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

// Import and Set Nuxt.js options
let config = require('../nuxt.config.js')
config.dev = !(process.env.NODE_ENV === 'production')


async function start() {
  // Init Nuxt.js
  const nuxt = new Nuxt(config)

  // Build only in dev mode
  if (config.dev) {
    const builder = new Builder(nuxt)
    await builder.build()
  }

  // Give nuxt middleware to express
  app.use(nuxt.render)

  // Listen the server
  app.listen(port)
  consola.ready({
    message: `Server listening on ${port}`,
    badge: true
  })
}
start()
