import request from '@/utils/request'

export default {
  //添加一个图书
  add(data){
    return request({
      url:"/book/add",
      method:"POST",
      data
    })
  },
  //获取学生信息
  getBookList(data){
    return request({
      url:"/book/getBookList",
      method:"GET",
      params:data
    })
  },
  //删除一个学生
  deleteBook(id){
    return request({
      url:`/book/deleteBook?id=${id}`,
      method:"GET",
    })
  },
  //修改一个院系
  updateBook(data){
    return request({
      url:"/book/update",
      method:"POST",
      data
    })

  }
}
