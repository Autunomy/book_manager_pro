import request from '@/utils/request'

export default {
  add(data){
    return request({
      url:"/rbac/permission/add",
      method:"POST",
      data
    })
  },
  getPermissionList(data){
    return request({
      url:"/rbac/permission/list",
      method:"POST",
      params:data
    })
  },
  deletePermission(id){
    return request({
      url:`/rbac/permission/delete?id=${id}`,
      method:"GET",
    })
  },
  updatePermission(data){
    return request({
      url:"/rbac/permission/update",
      method:"POST",
      data
    })
  },
  selectAllPermissionList(){
    return request({
      url:"/rbac/permission/listAll",
      method:"GET"
    })
  },
  selectAllTreePermissionList(){
    return request({
      url:"/rbac/permission/allTreeList",
      method:"GET"
    })
  }

}
