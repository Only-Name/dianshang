import axios from 'axios'
//配置请求的根路径
axios.defaults.baseURL = 'http://127.0.0.1:8888/api/private/v1/'
export default {
  post(url,data){
    return axios({
      methods:'post',
      url:url,
      data:data,
      timeout:5000,
      headers:''
    })
  },
  put(url,data){
    return axios({
      methods:'put',
      url:url,
      data:data,
      timeout:5000,
      headers:''
    })
  }
}
