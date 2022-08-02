<template>
  <div style="width: 1300px;margin: 0 auto;padding:10px">
    <div style="padding-left: 10px;padding-top:10px;">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="院系名称" style="margin-left: 20px">
          <el-input  v-model="formData.name" placeholder="请输入院系查询名称"></el-input>
        </el-form-item>

        <el-form-item label="院系成立日期" style="margin-left: 20px">
          <el-date-picker
            v-model="formData.create_date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button @click="searchDepartmentListHandler" style="width: 140px;margin-left: 30px" type="primary">搜索院系</el-button>
          <el-button @click="clearSearch" style="width: 140px;margin-left: 30px" type="default">清空搜索条件</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-divider></el-divider>
    <el-table
      v-loading="isLoading"
      :data="tableData"
      stripe
      :border="true"
      :highlight-current-row="true"
      :fit="true"
      height="481">
      <el-table-column
        label="id"
        prop="id"
        width="70">
      </el-table-column>
      <el-table-column
        label="院系名称"
        prop="name">
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="create_date">
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template v-slot="scope">
          <el-button
            size="mini"
            @click="editDep(scope.$index, tableData)"
          >编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteDep(scope.$index, tableData)">删除
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
    <el-dialog title="修改院系信息" :visible.sync="isShow" width="40%" top="20vh">
      <el-form ref="form" :model="editForm">
        <el-form-item label="id" label-width="80px">
          <el-input disabled v-model="editForm.id"></el-input>
        </el-form-item>
        <el-form-item label="院系名称" label-width="80px">
          <el-input placeholder="请输入院系名称" v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="创建时间" label-width="80px">
          <el-date-picker value-format="yyyy-MM-dd" type="date" placeholder="选择日期" v-model="editForm.create_date" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item >
          <el-button style="margin-left: 420px" type="primary" @click="updateDep">修改</el-button>
          <el-button @click="isShow = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>


</template>

<script>
import departmentApi from "@/api/department";
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
      formData: {
        name: "",
        create_date: []
      },
      isLoading:false,//加载动画是否开启 false表示不开启
      editForm:{
        id:"",
        name:"",
        create_date:""
      },
      isShow:false
    }
  },
  computed: {
    startCreateDate() {
      if(this.formData.create_date !== null && this.formData.create_date.length === 2)
        return this.formData.create_date[0];
    },
    endCreateDate() {
      if(this.formData.create_date !== null && this.formData.create_date.length === 2)
        return this.formData.create_date[1];
    }
  },
  mounted() {
    this.getDepartmentList();
  },
  methods: {
    //获取当前页面应该显示的元素
    getDepartmentList() {
      //开启加载动画
      this.isLoading = true;

      const paramsData = {
        name: this.formData.name,
        startCreateDate: this.startCreateDate,
        endCreateDate: this.endCreateDate,
        currentPage: this.pagination.currentPage,
        pageSize: this.pagination.pageSize
      }
      departmentApi.getDepartmentList(paramsData).then(resp => {
        // if(resp.data.length === 0 && this.pagination.currentPage > 1){
        //   this.pagination.currentPage --;
        // }

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
    deleteDep(idx, data) {
      //消息弹出框
      this.$confirm('是否要删除当前院系(删除将无法找回)?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击了确定按钮 那么就删除当前院系
        departmentApi.deleteDepartment(data[idx].id).then(resp => {
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
          if(this.tableData.length === 1 && this.pagination.currentPage > 1){
            this.pagination.currentPage --;
          }
          this.getDepartmentList();
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
    editDep(idx, data) {
      //需要修改的信息是 data[idx]
      this.isShow = true;
      this.editForm.id = data[idx].id;
      this.editForm.name = data[idx].name;
      this.editForm.create_date = data[idx].create_date;
    },
    //修改信息
    updateDep(){
      departmentApi.updateDepartment(qs.stringify(this.editForm)).then(resp => {
        if(resp.code === 6666){//修改成功
          this.getDepartmentList()
          this.$message.success({
            message:"修改成功",
            duration:2000
          })
        }else{
          this.$message.warning({
            message:"修改失败",
            duration:2000
          })
        }
      }).catch(error => {
        this.$message.error({
          message:"出错了"+error,
          duration:2000
        })
      })
      this.isShow = false;
    },
    //查询
    searchDepartmentListHandler() {
      //搜索之后将当前页码置为第一页
      this.pagination.currentPage = 1;
      this.getDepartmentList();
    },
    //当前页码改变的时候的回调函数
    handleCurrentPage(currentPage) {
      //更新当前页码
      this.pagination.currentPage = currentPage
      //访问服务器数据
      this.getDepartmentList()
    },
    //当前页面大小改变的回调函数
    handleSizeChange(pageSize) {
      this.pagination.pageSize = pageSize
      //访问服务器
      this.getDepartmentList()
    },
    //清空搜索条件
    clearSearch(){
      this.formData.name = ""
      this.formData.create_date = []
      this.pagination.currentPage = 1;
      this.getDepartmentList()
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

.el-dialog{
  padding: 0  0;
}

.el-dialog .el-form-item{
  margin-top: 10px;
}
</style>
