<template>
  <div>
    <el-col :span="6" :offset="9" style="margin-top: 20px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="院系名称">
          <el-input placeholder="请输入院系名称" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker value-format="yyyy-MM-dd" type="date" placeholder="选择日期" v-model="form.create_date" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addDep">添加</el-button>
          <el-button @click="clear">清空</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </div>
</template>

<script>
import qs from "qs";
import departmentApi from "@/api/department";

export default {
  name: "Add",
  data() {
    return {
      form: {
        name: '',
        create_date: ''
      }
    }
  },
  methods: {
    addDep() {
      departmentApi.add(qs.stringify(this.form)).then(resp => {
        if(resp.code === 6666){ // 添加成功
          this.$message.success({
            message:"添加成功",
            duration:2000
          })
          this.clear()
          // 路由跳转到院系列表
          // this.$router.push("/department/list");
        }else{
          this.$message.warning({
            message:"添加失败",
            duration:2000
          })
        }
      }).catch(error => {
        this.$message.error({
          message:"出错了",
          duration:2000
        })
      })
    },
    clear(){
      this.form = {}
    }
  }
}
</script>

<style scoped>

</style>
