import request from '@/utils/request'

export default {
  addRole(data){
    return request({
      url:"/rbac/role/add",
      method:"POST",
      data
    })
  },
  getRoleList(data){
    return request({
      url:"/rbac/role/list",
      method:"POST",
      params:data
    })
  },
  deleteRole(id){
    return request({
      url:`/rbac/role/delete?id=${id}`,
      method:"GET",
    })
  },
  selectRoleById(id){
    return request({
      url:`/rbac/role/findById?id=${id}`,
      method:"GET",
    })
  },
  updateRoleById(id,data){
    return request({
      url:`/rbac/role/update?id=${id}`,
      method:"POST",
      data
    })
  },
  selectRoleList(){
    return request({
      url: '/rbac/role/selectRoleList',
      method: 'GET'
    })
  },
  selectRoleByName(name){
    return request({
      url:`/rbac/role/selectRoleByName?name=${name}`,
      method:'GET'
    })
  }
}
