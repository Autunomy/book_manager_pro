<template>
  <div style="width: 1500px;margin: 0 auto">
    <div class="demo-input-suffix" >
      <el-input
        style="width: 270px;margin-top:10px;margin-bottom: 10px;margin-right: 5px"
        placeholder="请输入院系名称"
        prefix-icon="el-icon-search"
        v-on:keyup.enter="searchDepartment">
      </el-input>
      <el-button type="primary">搜索</el-button>
    </div>
    <el-table
      :data="tableData"
      stripe
      :border="true"
      :highlight-current-row="true"
      :fit="true">
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
            @click="editDep(scope.$index, tableData)">编辑
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
      layout="prev, pager, next"
      :total="tableData.length"
      :page-size="13"
      :page-count="tableData.length/13"
      :pager-count="5"
      style="text-align: center;">
    </el-pagination>
  </div>
</template>

<script>
import departmentApi from "@/api/department";

export default {
  name: "List",
  data() {
    return {
      tableData: []
    }
  },
  mounted() {
    this.getDepartmentList();
  },
  methods: {
    getDepartmentList() {
      departmentApi.getDepartmentList().then(resp => {
        this.tableData = resp.data;
      }).catch(error => {
        this.$message.error({
          message: "获取信息失败:" + error
        })
      })
    },

    //删除信息
    deleteDep(idx, data) {
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
        this.getDepartmentList();
      }).catch(error => {
        this.$message.error({
          message: "网络出错啦",
          duration: 1000
        })
      })
    },
    //修改信息
    editDep(idx, data) {
      //需要修改的信息是 data[idx]
    },
    //查询院系
    searchDepartment(){

    }
  }
}
</script>

<style scoped>

</style>
