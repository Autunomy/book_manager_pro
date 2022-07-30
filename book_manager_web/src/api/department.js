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
  getDepartmentList(){
    return request({
      url:"/department/getDepartmentList",
      method:"GET"
    })
  },
  deleteDepartment(id){
    return request({
      url:"/department/deleteDepartment?id="+id,
      method:"GET",
    })
  },
  searchDepartment(){
    return request({
      
    })
  }
}
