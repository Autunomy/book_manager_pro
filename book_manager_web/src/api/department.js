import request from '@/utils/request'

export default {
  //添加一个院系
  add(data){
    return request({
      url:"/department/add",
      method:"POST",
      data
    })
  },
  //获取院系信息
  getDepartmentList(data){
    return request({
      url:"/department/getDepartmentList",
      method:"GET",
      params:data
    })
  },
  //删除一个院系
  deleteDepartment(id){
    return request({
      url:"/department/deleteDepartment?id="+id,
      method:"GET",
    })
  },
  //修改一个院系
  updateDepartment(data){
    return request({
      url:"/department/update",
      method:"POST",
      data
    })

  }
}
