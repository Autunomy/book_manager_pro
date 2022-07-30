import request from '@/utils/request'

// export function login(data) {
//   return request({
//     url: '/vue-admin-template/user/login',
//     method: 'post',
//     data
//   })
// }
//
// export function getInfo(token) {
//   return request({
//     url: '/vue-admin-template/user/info',
//     method: 'get',
//     params: { token }
//   })
// }
//
// export function logout() {
//   return request({
//     url: '/vue-admin-template/user/logout',
//     method: 'post'
//   })
// }


export default {
  login(data){
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  },
  getInfo(token){
    //这里不需要在请求头中携带token，因为在请求拦截器中已经自动进行配置了
    return request({
      url: '/user/userInfo',
      method: 'get'
    })
  }
}
