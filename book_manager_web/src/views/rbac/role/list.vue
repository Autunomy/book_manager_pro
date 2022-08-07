<template>
  <div style="margin: 0 auto;padding:10px">
    <div style="padding-left: 10px;padding-top:10px;">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="角色名称" style="margin-left: 20px">
          <el-input v-model="formData.name" placeholder="请输入角色名称"></el-input>
        </el-form-item>

        <el-form-item label="角色描述" style="margin-left: 20px">
          <el-input v-model="formData.descp" placeholder="请输入角色描述"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="searchRoleListHandler" style="width: 140px;margin-left: 30px" type="primary">搜索
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
        label="角色名称"
        prop="name">
        <template v-slot="scope">
          <el-tag
            :type="scope.row.name == 'ROLE_super-admin' ? 'danger' : (scope.row.name == 'ROLE_boss' ? 'warning' : 'info')"
            disable-transitions>{{ scope.row.name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="角色描述"
        prop="descp">
      </el-table-column>
      <!--      <el-table-column-->
      <!--        label="角色列表"-->
      <!--        prop="parent_tag">-->

      <!--      </el-table-column>-->
      <el-table-column min-width="140px" fixed="right" align="center" label="操作">
        <template v-slot="scope">
          <el-button
            size="mini"
            @click="editRole(scope.$index, tableData)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteRole(scope.$index, tableData)">删除
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
    <el-dialog title="修改角色信息" :visible.sync="isShow" width="40%" top="20vh">
      <el-form ref="form" :model="editForm" label-width="80px">
        <el-form-item label="id">
          <el-input disabled v-model="editForm.id"></el-input>
        </el-form-item>
        <el-form-item label="角色名称">
          <el-input placeholder="请输入角色名称" v-model="editForm.name">
            <template slot="prepend">ROLE_</template>
          </el-input>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input type="textarea" placeholder="请输入角色描述" v-model.number="editForm.descp"></el-input>
        </el-form-item>
        <el-form-item label="权限列表">
          <el-tree
            :default-expanded-keys="this.editForm.permissionListIds"
            :default-checked-keys="this.editForm.permissionListIds"
            :check-strictly="true"
            node-key="id"
            ref="tree"
            style="border: 1px solid #DCDFE6;border-radius: 4px"
            :data="permissionList"
            :props="props"
            show-checkbox
            @check-change="handlePermissionCheckChange">
          </el-tree>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateRole">修改</el-button>
          <el-button @click="cancle">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>


</template>

<script>
import permissionApi from "@/api/permission"
import qs from "qs";
import roleApi from "@/api/role";

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
        id: '',
        name: '',
        descp: '',
        permissionListIds:[]//当前角色具有权限id集合
      },
      isShow: false,
      formData: {
        name: '',
        descp: ''
      },
      permissionList: [],//权限树
      props:{
        label:'tag'
      }
    }
  },
  mounted() {
    this.getRoleList();
  },
  methods: {
    handlePermissionCheckChange(){
      this.editForm.permissionListIds = this.$refs.tree.getCheckedKeys()
    },

    //获取全部的权限列表
    getAllPermissionList() {
      permissionApi.selectAllTreePermissionList().then(resp => {
        if(resp.code === 6666){
          this.permissionList = resp.data;
        }else{
          this.$message.warning({
            message: "获取权限树失败",
            duration: 2000
          })
        }
      }).catch(error => {
        this.$message.error({
          message: "获取权限树出错",
          duration: 2000
        })
      })
    },
    //获取当前页面应该显示的元素
    getRoleList() {
      //开启加载动画
      this.isLoading = true;
      const paramsData = {
        name: this.formData.name,
        descp: this.formData.descp,
        currentPage: this.pagination.currentPage,
        pageSize: this.pagination.pageSize
      }
      roleApi.getRoleList(paramsData).then(resp => {
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
    deleteRole(idx, data) {
      //消息弹出框
      this.$confirm('是否要删除当前角色(删除将无法找回)?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击了确定按钮 那么就删除当前院系
        roleApi.deleteRole(data[idx].id).then(resp => {
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
          this.getRoleList();
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
    editRole(idx, data) {
      //需要修改的信息是 data[idx]
      this.isShow = true;
      this.editForm.id = data[idx].id;
      this.editForm.name = data[idx].name.substring(5);
      this.editForm.descp = data[idx].descp;
      //获取权限树
      this.getAllPermissionList();

      roleApi.selectRoleById(data[idx].id).then(resp => {
        this.editForm.permissionListIds = []
        if(resp.code === 6666){
          resp.data.permissionList.forEach(permission => {
            this.editForm.permissionListIds.push(permission.id);
          })
          console.log(this.editForm.permissionListIds)
        }else{
          this.$message.warning({
            message:"获取权限列表失败",
            duration:2000
          })
        }
      }).catch(error => {
        this.$message.error({
          message:"获取权限列表出错",
          duration:2000
        })
      })

    },
    cancle() {
      this.isShow = false
    },
    //修改信息
    updateRole() {
      roleApi.updateRoleById(this.editForm.id,qs.stringify(this.editForm,{indices: false})).then(resp => {
        if (resp.code === 6666) {//修改成功
          this.getRoleList()
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
      this.getRoleList()
    },
    //当前页面大小改变的回调函数
    handleSizeChange(pageSize) {
      this.pagination.pageSize = pageSize
      //访问服务器
      this.getRoleList()
    },
    //清空搜索条件
    clearSearch() {
      this.formData.name = ""
      this.formData.descp = ""
      this.pagination.currentPage = 1;
      this.getRoleList()
    },
    searchRoleListHandler() {
      this.pagination.currentPage = 1;
      this.getRoleList();
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
