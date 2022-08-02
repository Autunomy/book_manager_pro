<template>
  <div style="width: 1400px;margin: 0 auto;padding:10px">
    <div style="padding-left: 10px;padding-top:10px;">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="图书名称" style="margin-left: 20px">
          <el-input v-model="formData.name" placeholder="请输入图书名称查询"></el-input>
        </el-form-item>

        <el-form-item label="图书发行日期" style="margin-left: 20px">
          <el-date-picker
            v-model="formData.release_date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button @click="searchBookListHandler" style="width: 140px;margin-left: 30px" type="primary">搜索
          </el-button>
          <el-button @click="clearSearch" style="width: 140px;margin-left: 30px" type="default">清空搜索条件</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-divider></el-divider>
    <el-table
      :v-loading="isLoading"
      :data="tableData"
      stripe
      :border="true"
      :highlight-current-row="true"
      :fit="true"
      height="481">
      <el-table-column
        label="id"
        prop="id">
      </el-table-column>
      <el-table-column
        label="图书名称"
        prop="name">
      </el-table-column>
      <el-table-column
        label="发行时间"
        prop="release_date">
      </el-table-column>
      <el-table-column
        label="作者"
        prop="author">
      </el-table-column>
      <el-table-column
        label="价格(分)"
        prop="price">
      </el-table-column>
      <el-table-column
        label="发行日期"
        prop="release_date">
      </el-table-column>
      <el-table-column
        label="库存"
        prop="stock">
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作">
        <template v-slot="scope">
          <el-button
            size="mini"
            @click="editBook(scope.$index, tableData)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteBook(scope.$index, tableData)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin:20px;"></div>
    <!--分页-->
    <el-pagination
      background
      layout="total, prev, pager, next, sizes"
      :current-page.sync="pagination.currentPage"
      :total="pagination.total"
      :page-size="pagination.pageSize"
      :page-sizes="[5,8,10,20,30]"
      style="text-align: center;"
      @current-change="handleCurrentPage"
      @size-change="handleSizeChange">
    </el-pagination>

    <!--编辑按钮的弹出框-->
    <el-dialog title="修改学生信息" :visible.sync="isShow" width="40%" top="10vh">
      <el-form ref="form" :model="editForm" label-width="80px">
        <el-form-item label="图书名称">
          <el-input placeholder="请输入图书名称" v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="图书作者">
          <el-input placeholder="请输入图书作者" v-model.number="editForm.author"></el-input>
        </el-form-item>
        <el-form-item label="图书价格">
          <el-input placeholder="请输入图书价格" type="number" v-model="editForm.price"></el-input>
        </el-form-item>
        <el-form-item label="出版日期">
          <el-date-picker value-format="yyyy-MM-dd" type="date" placeholder="选择出版日期" v-model="editForm.release_date"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="库存">
          <el-input placeholder="请输入库存" type="number" v-model="editForm.stock"></el-input>
        </el-form-item>
        <el-form-item label="图书封面">
          <el-upload
            name="pic"
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img v-if="editForm.pic_path" :src="editForm.pic_path" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="updateBook">修改</el-button>
          <el-button @click="cancle">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>


</template>

<script>
import bookApi from "@/api/book"
import qs from "qs";

export default {
  name: "List",
  data() {
    return {
      uploadUrl: process.env.VUE_APP_BASE_API + '/book/uploadPic',
      pagination: {
        total: 0, //总条数
        pageSize: 8, //每页大小
        currentPage: 1 // 当前页码
      },
      tableData: [],
      formData: {
        name: "",
        release_date: []
      },
      isLoading: false,//加载动画是否开启 false表示不开启
      editForm: {
        id:'',
        name: '',
        author: '',
        price:0,
        release_date: "",
        stock: 0,
        pic_path:''
      },
      isShow: false
    }
  },
  computed: {
    startReleaseDate() {
      if (this.formData.release_date !== null && this.formData.release_date.length === 2)
        return this.formData.release_date[0];
    },
    endReleaseDate() {
      if (this.formData.release_date !== null && this.formData.release_date.length === 2)
        return this.formData.release_date[1];
    }
  },
  mounted() {
    this.getBookList();
  },
  methods: {
    //获取当前页面应该显示的元素
    getBookList() {
      //开启加载动画
      this.isLoading = true;
      const paramsData = {
        name: this.formData.name,
        startReleaseDate: this.startReleaseDate,
        endReleaseDate: this.endReleaseDate,
        currentPage: this.pagination.currentPage,
        pageSize: this.pagination.pageSize
      }
      bookApi.getBookList(paramsData).then(resp => {
        this.tableData = resp.data;
        //再次重新设置分页信息 为了避免客户端的分页信息展示和服务器返回的当前页的数据不一致的问题
        this.pagination.pageSize = resp.pageInfo.pageSize;
        this.pagination.currentPage = resp.pageInfo.currentPage;
        this.pagination.total = resp.pageInfo.total;
      }).catch(error => {
        this.$message.error({
          message: "获取信息失败:" + error
        })
      })

      //结束动画
      this.isLoading = false;
    },
    //删除信息
    deleteBook(idx, data) {
      //消息弹出框
      this.$confirm('是否要删除当前学生(删除将无法找回)?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击了确定按钮 那么就删除当前院系
        bookApi.deleteBook(data[idx].id).then(resp => {
          if (resp.code === 6666) {
            this.$message.success({
              message: "删除成功",
              duration: 1000
            })
          } else {
            this.$message.error({
              message: "删除失败",
              duration: 1000
            })
          }
          if (this.tableData.length === 1 && this.pagination.currentPage > 1) {
            this.pagination.currentPage--;
          }
          this.getBookList();
        }).catch(error => {
          this.$message.error({
            message: "网络出错啦",
            duration: 1000
          })
        })
      }).catch(() => {
        //点击取消按钮
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    //修改信息弹出框
    editBook(idx, data) {
      //需要修改的信息是 data[idx]
      this.isShow = true;
      this.editForm.id = data[idx].id;
      this.editForm.name = data[idx].name;
      this.editForm.author = data[idx].author;
      this.editForm.price = data[idx].price;
      this.editForm.release_date = data[idx].release_date;
      this.editForm.stock = data[idx].stock;
      this.editForm.pic_path = process.env.VUE_APP_BASE_API + "/images/" +data[idx].pic_path;
    },
    cancle(){
      this.isShow = false
    },
    //修改信息
    updateBook() {
      bookApi.updateBook(qs.stringify(this.editForm)).then(resp => {
        if (resp.code === 6666) {//修改成功
          this.getBookList()
          this.$message.success({
            message: "修改成功",
            duration: 2000
          })
        } else {
          this.$message.warning({
            message: "修改失败",
            duration: 2000
          })
        }
      }).catch(error => {
        this.$message.error({
          message: "出错了" + error,
          duration: 2000
        })
      })
      this.isShow = false;
    },
    //查询
    searchBookListHandler() {
      //搜索之后将当前页码置为第一页
      this.pagination.currentPage = 1;
      this.getBookList();
    },
    //当前页码改变的时候的回调函数
    handleCurrentPage(currentPage) {
      //更新当前页码
      this.pagination.currentPage = currentPage
      //访问服务器数据
      this.getBookList()
    },
    //当前页面大小改变的回调函数
    handleSizeChange(pageSize) {
      this.pagination.pageSize = pageSize
      //访问服务器
      this.getBookList()
    },
    //清空搜索条件
    clearSearch() {
      this.formData.name = ""
      this.formData.release_date = []
      this.pagination.currentPage = 1;
      this.getBookList()
    },

    handleAvatarSuccess(response,file,fileList){
      if(response.code === 6666){
        this.editForm.pic_path = process.env.VUE_APP_BASE_API + "/images/" +response.data.pic_path;
      }
    },
  }
}
</script>

<style scoped>
.el-form-item {
  margin-bottom: 0;
}

.el-divider {
  margin: 10px 0px;
}

.el-dialog {
  padding: 0 0;
}

.el-dialog .el-form-item {
  margin-top: 10px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
