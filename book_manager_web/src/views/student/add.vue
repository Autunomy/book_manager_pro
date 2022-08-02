<template>
  <div>
    <el-col :span="6" :offset="9" style="margin-top: 20px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="学生姓名">
          <el-input placeholder="请输入学生姓名" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input placeholder="请输入年龄" type="number" v-model.number="form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input placeholder="请输入邮箱" type="email" v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input placeholder="请输入学号" v-model="form.number"></el-input>
        </el-form-item>
        <el-form-item label="入学日期">
          <el-date-picker value-format="yyyy-MM-dd" type="date" placeholder="选择学生入学日期" v-model="form.join_date"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="所属院系">
          <el-select v-model="form.dep_id" clearable placeholder="请选择所属院系">
            <el-option
              v-for="item in depList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addStu">添加</el-button>
          <el-button @click="clear">清空</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </div>
</template>

<script>
import qs from "qs";
import departmentApi from "@/api/department";
import studentApi from "@/api/student"

export default {
  name: "Add",
  data() {
    return {
      form: {
        name: '',
        age: 0,
        sex: "",
        email: "",
        number: "",
        join_date: "",
        dep_id: ""
      },
      depList: []
    }
  },
  mounted() {
    this.selectDepList()
  },
  methods: {
    addStu() {
      studentApi.add(qs.stringify(this.form)).then(resp => {
        if (resp.code === 6666) { // 添加成功
          this.$message.success({
            message: "添加成功",
            duration: 2000
          })
          this.clear()
        } else {
          this.$message.warning({
            message: "添加失败",
            duration: 2000
          })
        }
      }).catch(error => {
        this.$message.error({
          message: "出错了",
          duration: 2000
        })
      })
    },
    clear() {
      this.form = {}
    },

    //获取院系列表
    selectDepList(){
      departmentApi.listAllDepartment().then(resp => {
        if(resp.code === 6666){
          this.depList = resp.data
        }else{
          this.$message.warning({
            message:"获取院系列表失败",
            duration:2000
          })
        }
      }).catch(error => {
        this.$message.error({
          message:"获取院系列表出错",
          duration:2000
        })
      })
    }

  }
}
</script>

<style scoped>

</style>
