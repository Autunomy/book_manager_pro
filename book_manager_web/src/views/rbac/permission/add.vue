<template>
  <div>
    <el-col :span="10" :offset="6" style="margin-top: 20px">
      <el-form ref="form" :model="form">
        <el-form-item label="权限标识">
          <el-input placeholder="请输入权限标识字符串" v-model="form.tag" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="权限描述">
          <el-input type="textarea" placeholder="请输入权限描述" v-model="form.descp" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="父权限">
          <el-select v-model="form.parent_id" filterable placeholder="请选择父权限(不选默认为顶级权限)" style="margin-left: 15px;width: 400px">
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
          <el-button type="primary" @click="addPermission">添加权限</el-button>
          <el-button @click="clear">清空</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </div>
</template>

<script>
import qs from "qs";
import permissionApi from "@/api/permission";

export default {
  name: "Add",
  data() {
    return {
      form: {
        tag: '',
        descp: '',
        parent_id: ''
      },
      permissionList: []
    }
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
    addPermission() {
      //校验
      if(!this.form.parent_id){
        this.form.parent_id = 0;
      }

      permissionApi.add(qs.stringify(this.form)).then(resp => {
        if (resp.code === 6666) { // 添加成功
          this.$message.success({
            message: "添加权限成功",
            duration: 2000
          })
          this.clear()
        } else {
          this.$message.warning({
            message: "添加权限失败",
            duration: 2000
          })
        }
        this.getAllPermissionList();
      }).catch(error => {
        this.$message.error({
          message: "出错了",
          duration: 2000
        })
      })
    },
    clear() {
      this.form = {}
    }
  },
  mounted() {
    this.getAllPermissionList()
  }
}
</script>

<style scoped>

</style>
