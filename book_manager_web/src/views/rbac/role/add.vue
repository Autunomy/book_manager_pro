<template>
  <div>
    <el-col :span="10" :offset="5" style="margin-top: 20px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="角色名称">
          <el-input placeholder="请输入角色名称" v-model="form.name">
            <template slot="prepend">ROLE_</template>
          </el-input>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input type="textarea" placeholder="请输入角色描述" v-model="form.descp"></el-input>
        </el-form-item>
        <el-form-item label="权限列表">
          <el-tree
            :check-strictly="true"
            ref="tree"
            style="border: 1px solid #DCDFE6;border-radius: 4px"
            :data="permissionList"
            :props="props"
            node-key="id"
            show-checkbox
            @check-change="handlePermissionCheckChange">
          </el-tree>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addRole">添加角色</el-button>
          <el-button @click="clear">清空</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </div>
</template>

<script>
import qs from "qs";
import roleApi from "@/api/role";
import permissionApi from "@/api/permission";

export default {
  name: "Add",
  data() {
    return {
      //树形结构的配置项
      props: {
        label: "tag",
      },
      permissionList: [],
      form: {
        name: '',
        descp: '',
        permissionIdList: []
      }
    }
  },
  methods: {
    handlePermissionCheckChange(data, self, childrenChecked) {
      this.form.permissionIdList = this.$refs.tree.getCheckedKeys()
    },
    addRole() {
      this.form.name = "ROLE_"+this.form.name;
      roleApi.addRole(qs.stringify(this.form, {indices: false})).then(resp => {
        if (resp.code === 6666) { // 添加成功
          this.$message.success({
            message: "添加角色成功",
            duration: 2000
          })
          this.clear()
        } else {
          this.$message.warning({
            message: "添加角色失败",
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
      this.selectAllTreePermissionList()
    },
    selectAllTreePermissionList() {
      permissionApi.selectAllTreePermissionList().then(resp => {
        this.permissionList = resp.data
        console.log(resp)
      }).catch(error => {
        this.$message.error({
          message: "获取权限树形结构数据出错",
          duration: 2000
        })
      })
    }
  },
  mounted() {
    this.selectAllTreePermissionList();
  }
}
</script>

<style scoped>

</style>
