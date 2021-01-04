// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
//导入全局样式表
import './assets/css/global.css'
import less from 'less'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

/*//axios拦截器，把token值存起来，没有token值时就返回登录界面
axios.interceptors.request.use(config => {
  config.headers.Authorization = window.sessionStorage.getItem('token')
//  最后必须返回config
  return config
})*/
Vue.config.productionTip = false

Vue.use(less)
Vue.use(ElementUI)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
})
