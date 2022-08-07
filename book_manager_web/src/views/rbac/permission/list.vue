<template>
  <div style="margin: 0 auto;padding:10px">
    <div style="padding-left: 10px;padding-top:10px;">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="权限名称" style="margin-left: 20px">
          <el-input v-model="formData.tag" placeholder="请输入权限名称"></el-input>
        </el-form-item>

        <el-form-item label="权限描述" style="margin-left: 20px">
          <el-input v-model="formData.descp" placeholder="请输入权限描述"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="searchPermissionListHandler" style="width: 140px;margin-left: 30px" type="primary">搜索
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
        label="权限名称"
        prop="tag">
      </el-table-column>
      <el-table-column
        label="权限描述"
        prop="descp">
      </el-table-column>
      <el-table-column
        label="父权限标识"
        prop="parent_tag">
        <template v-slot="scope">
          <el-tag
            :type="scope.row.parent_tag ? 'primary' : 'success'"
            disable-transitions>{{scope.row.parent_tag ? scope.row.parent_tag : '顶级权限'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column min-width="140px" fixed="right" align="center" label="操作">
        <template v-slot="scope">
          <el-button
            size="mini"
            @click="editPermission(scope.$index, tableData)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deletePermission(scope.$index, tableData)">删除
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
    <el-dialog title="修改权限信息" :visible.sync="isShow" width="40%" top="20vh">
      <el-form ref="form" :model="editForm" label-width="80px">
        <el-form-item label="id">
          <el-input disabled v-model="editForm.id"></el-input>
        </el-form-item>
        <el-form-item label="权限名称">
          <el-input placeholder="请输入权限名称" v-model="editForm.tag"></el-input>
        </el-form-item>
        <el-form-item label="权限描述">
          <el-input type="textarea" placeholder="请输入权限描述" v-model.number="editForm.descp"></el-input>
        </el-form-item>
        <el-form-item label="父权限" label-width="80px">
          <el-select v-model="editForm.parent_id" filterable placeholder="请选择父权限(不选默认为顶级权限)">
            <el-option
              v-for="item in permissionList"
              :key="item.id"
              :label="item.tag"
              :value="item.id">
              <span style="float: left">{{ item.tag }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.descp }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updatePermission">修改</el-button>
          <el-button @click="cancle">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>


</template>

<script>
import permissionApi from "@/api/permission"
import qs from "qs";

export default {
  name: "List",
  data() {
    return {
      pagination: {
        total: 0, //总条数
        pageSize: 8, //每页大小
        currentPage: 1 // 当前页码
      },
      tableData: [],
      isLoading: false,//加载动画是否开启 false表示不开启
      editForm: {
        id:'',
        tag:'',
        descp:'',
        parent_id:''
      },
      isShow: false,
      formData:{
        tag:'',
        descp:''
      },
      permissionList:[]
    }
  },
  mounted() {
    this.getPermissionList();
  },
  methods: {
    //获取全部的权限列表
    getAllPermissionList() {
      permissionApi.selectAllPermissionList().then(resp => {
        this.permissionList = resp.data;
      }).catch(error => {
        this.$message.error({
          message:"获取权限列表失败",
          duration:2000
        })
      })
    },
    //获取当前页面应该显示的元素
    getPermissionList() {
      //开启加载动画
      this.isLoading = true;
      const paramsData = {
        tag: this.formData.tag,
        descp: this.formData.descp,
        currentPage: this.pagination.currentPage,
        pageSize: this.pagination.pageSize
      }
      permissionApi.getPermissionList(paramsData).then(resp => {
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
    deletePermission(idx, data) {
      //消息弹出框
      this.$confirm('是否要删除当前权限(删除将无法找回)?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击了确定按钮 那么就删除当前院系
        permissionApi.deletePermission(data[idx].id).then(resp => {
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
          this.getPermissionList();
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
    editPermission(idx, data) {
      //需要修改的信息是 data[idx]
      this.isShow = true;
      this.editForm.id = data[idx].id;
      this.editForm.tag = data[idx].tag;
      this.editForm.descp = data[idx].descp;
      this.editForm.parent_id = data[idx].parent_id;
      this.getAllPermissionList();
    },
    cancle(){
      this.isShow = false
    },
    //修改信息
    updatePermission() {
      permissionApi.updatePermission(qs.stringify(this.editForm)).then(resp => {
        console.log(this.editForm)

        if (resp.code === 6666) {//修改成功
          this.getPermissionList()
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
    //当前页码改变的时候的回调函数
    handleCurrentPage(currentPage) {
      //更新当前页码
      this.pagination.currentPage = currentPage
      //访问服务器数据
      this.getPermissionList()
    },
    //当前页面大小改变的回调函数
    handleSizeChange(pageSize) {
      this.pagination.pageSize = pageSize
      //访问服务器
      this.getPermissionList()
    },
    //清空搜索条件
    clearSearch() {
      this.formData.tag = ""
      this.formData.descp = ""
      this.pagination.currentPage = 1;
      this.getPermissionList()
    },
    searchPermissionListHandler(){
      this.pagination.currentPage = 1;
      this.getPermissionList();
    }
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
