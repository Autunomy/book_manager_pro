<template>
  <div>
    <el-col :span="6" :offset="9" style="margin-top: 20px">
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="图书名称">
          <el-input placeholder="请输入图书名称" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="图书作者">
          <el-input placeholder="请输入图书作者" v-model.number="form.author"></el-input>
        </el-form-item>
        <el-form-item label="图书价格">
          <el-input placeholder="请输入图书价格" type="number" v-model.number="form.price"></el-input>
        </el-form-item>
        <el-form-item label="图书发行日期">
          <el-date-picker value-format="yyyy-MM-dd" type="date" placeholder="选择图书发行日期" v-model="form.release_date"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>

        <el-form-item label="图书封面">
          <el-upload
            name="pic"
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img v-if="form.pic_path" :src="form.pic_path" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>


        <el-form-item style="margin-top: 10px">
          <el-button type="primary" @click="addBook">添加</el-button>
          <el-button @click="clear">清空</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </div>
</template>

<script>
import qs from "qs";
import bookApi from "@/api/book"

export default {
  name: "Add",
  data() {
    return {
      uploadUrl: process.env.VUE_APP_BASE_API + '/book/uploadPic',
      form: {
        name: '',
        author: '',
        price: 0,
        pic_path:'',
        release_date: ""
      }
    }
  },
  methods: {
    handleAvatarSuccess(response,file,fileList){
      if(response.code === 6666){
        this.form.pic_path = process.env.VUE_APP_BASE_API + "/images/" +response.data.pic_path;
      }
    },

    addBook() {
      bookApi.add(qs.stringify(this.form)).then(resp => {
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
    }
  }
}
</script>

<style scoped>
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
