<template>
  <div style="width: 1400px;margin: 0 auto;padding:10px">
    <div style="padding-left: 10px;padding-top:10px;">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="学生名称" style="margin-left: 20px">
          <el-input v-model="formData.name" placeholder="请输入学生名称查询"></el-input>
        </el-form-item>

        <el-form-item label="学生入学日期" style="margin-left: 20px">
          <el-date-picker
            v-model="formData.join_date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button @click="searchStudentListHandler" style="width: 140px;margin-left: 30px" type="primary">搜索
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
        prop="sid"
        min-width="30px">
      </el-table-column>
      <el-table-column
        label="学生姓名"
        prop="sname"
        min-width="40px">
      </el-table-column>
      <el-table-column
        label="入学时间"
        prop="join_date"
        min-width="40px">
      </el-table-column>
      <el-table-column
        label="性别"
        prop="sex"
        min-width="20px">
      </el-table-column>
      <el-table-column
        label="年龄"
        prop="age"
        min-width="20px">
      </el-table-column>
      <el-table-column
        label="邮箱"
        prop="email"
        min-width="50px">
      </el-table-column>
      <el-table-column
        label="所属院系"
        prop="dep_name"
        min-width="50px">
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作">
        <template v-slot="scope">
          <el-button
            size="mini"
            @click="editStu(scope.$index, tableData)"
          >编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteStu(scope.$index, tableData)">删除
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
        <el-form-item label="学生姓名">
          <el-input placeholder="请输入学生姓名" v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input placeholder="请输入年龄" type="number" v-model.number="editForm.age"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="editForm.sex" label="男">男</el-radio>
          <el-radio v-model="editForm.sex" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input placeholder="请输入邮箱" type="email" v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input placeholder="请输入学号" v-model="editForm.number"></el-input>
        </el-form-item>
        <el-form-item label="入学日期">
          <el-date-picker value-format="yyyy-MM-dd" type="date" placeholder="选择学生入学日期" v-model="editForm.join_date"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="所属院系">
          <el-select v-model="editForm.dep_id" clearable placeholder="请选择所属院系">
            <el-option
              v-for="item in depList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateStu">修改</el-button>
          <el-button @click="cancle">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>


</template>

<script>
import departmentApi from "@/api/department";
import studentApi from "@/api/student"
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
        join_date: []
      },
      isLoading: false,//加载动画是否开启 false表示不开启
      editForm: {
        id:'',
        name: '',
        age: 0,
        sex: "",
        email: "",
        number: "",
        join_date: "",
        dep_id: ""
      },
      isShow: false,
      depList:[]
    }
  },
  computed: {
    startJoinDate() {
      if (this.formData.join_date !== null && this.formData.join_date.length === 2)
        return this.formData.join_date[0];
    },
    endJoinDate() {
      if (this.formData.join_date !== null && this.formData.join_date.length === 2)
        return this.formData.join_date[1];
    }
  },
  mounted() {
    this.getStudentList();
  },
  methods: {
    //获取当前页面应该显示的元素
    getStudentList() {
      //开启加载动画
      this.isLoading = true;
      const paramsData = {
        name: this.formData.name,
        startJoinDate: this.startJoinDate,
        endJoinDate: this.endJoinDate,
        currentPage: this.pagination.currentPage,
        pageSize: this.pagination.pageSize
      }
      studentApi.getStudentList(paramsData).then(resp => {
        // console.log(resp)
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
    deleteStu(idx, data) {
      //消息弹出框
      this.$confirm('是否要删除当前院系(删除将无法找回)?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击了确定按钮 那么就删除当前院系
        studentApi.deleteStudent(data[idx].sid).then(resp => {
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
          this.getStudentList();
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
    editStu(idx, data) {
      //需要修改的信息是 data[idx]

      this.isShow = true;
      this.editForm.id = data[idx].sid;
      this.editForm.name = data[idx].sname;
      this.editForm.age = data[idx].age;
      this.editForm.sex = data[idx].sex;
      this.editForm.email = data[idx].email;
      this.editForm.number = data[idx].number;
      this.editForm.join_date = data[idx].join_date;
      this.editForm.dep_id = data[idx].dep_id;
      this.selectStuList();
    },
    cancle(){
      this.isShow = false
    },
    //修改信息
    updateStu() {
      studentApi.updateStudent(qs.stringify(this.editForm)).then(resp => {
        if (resp.code === 6666) {//修改成功
          this.getStudentList()
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
    searchStudentListHandler() {
      //搜索之后将当前页码置为第一页
      this.pagination.currentPage = 1;
      this.getStudentList();
    },
    //当前页码改变的时候的回调函数
    handleCurrentPage(currentPage) {
      //更新当前页码
      this.pagination.currentPage = currentPage
      //访问服务器数据
      this.getStudentList()
    },
    //当前页面大小改变的回调函数
    handleSizeChange(pageSize) {
      this.pagination.pageSize = pageSize
      //访问服务器
      this.getStudentList()
    },
    //清空搜索条件
    clearSearch() {
      this.formData.name = ""
      this.formData.join_date = []
      this.pagination.currentPage = 1;
      this.getStudentList()
    },

    //获取院系列表
    selectStuList() {
      departmentApi.listAllDepartment().then(resp => {
        if (resp.code === 6666) {
          this.depList = resp.data
        } else {
          this.$message.warning({
            message: "获取院系列表失败",
            duration: 2000
          })
        }
      }).catch(error => {
        this.$message.error({
          message: "获取院系列表出错",
          duration: 2000
        })
      })
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
</style>
