import request from '@/utils/request'

export default {
  //添加一个学生
  add(data){
    return request({
      url:"/student/add",
      method:"POST",
      data
    })
  },
  //获取学生信息
  getStudentList(data){
    return request({
      url:"/student/getStudentList",
      method:"GET",
      params:data
    })
  },
  //删除一个学生
  deleteStudent(id){
    return request({
      url:`/student/deleteStudent?id=${id}`,
      method:"GET",
    })
  },
  //修改一个院系
  updateStudent(data){
    return request({
      url:"/student/update",
      method:"POST",
      data
    })

  }
}
