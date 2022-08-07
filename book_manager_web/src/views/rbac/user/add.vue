<template>
  <div>
    <el-col :span="10" :offset="5" style="margin-top: 20px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input placeholder="请输入用户名" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input placeholder="请输入密码" v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="用户描述">
          <el-input type="textarea" placeholder="请输入用户描述" v-model="form.descp"></el-input>
        </el-form-item>
        <el-form-item label="所属角色">
          <el-select v-model="form.rid" filterable placeholder="请选择角色" style="width: 100%">
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
              <span style="float: left">{{ item.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.descp }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addUser">添加</el-button>
          <el-button @click="clear">清空</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </div>
</template>

<script>
import qs from "qs";
import userApi from "@/api/user";
import roleApi from "@/api/role";

export default {
  name: "Add",
  data() {
    return {
      roleList: [],
      form: {
        username: '',
        password: '',
        descp: '',
        rid:''
      }
    }
  },

  methods: {
    addUser() {
      userApi.addUser(qs.stringify(this.form)).then(resp => {
        if (resp.code === 6666) { // 添加成功
          this.$message.success({
            message: "添加用户成功",
            duration: 2000
          })
          this.clear()
        } else {
          this.$message.warning({
            message: "添加用户失败",
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
      this.selectRoleList()
    },
    selectRoleList() {
      roleApi.selectRoleList().then(resp => {
        if(resp.code === 6666){
          this.roleList = resp.data
        }else{
          this.$message.error({
            message: "获取角色列表失败",
            duration: 2000
          })
        }
        console.log(resp)
      }).catch(error => {
        this.$message.error({
          message: "获取角色列表出错"+error,
          duration: 2000
        })
      })
    }
  },
  mounted() {
    this.selectRoleList();
  }
}
</script>

<style scoped>

</style>
