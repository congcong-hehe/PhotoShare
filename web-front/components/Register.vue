<template>
  <div>
    <el-form :model="registerForm" :rules="rules">
      <el-form-item label="昵称" prop="name">
        <el-input placeholder="请输入昵称" v-model="registerForm.name"></el-input>
      </el-form-item> 
      <el-form-item label="密码" prop="password">
        <el-input placeholder="请输入密码" v-model="registerForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item label="自我描述" prop="description">
        <el-input placeholder="请输入自我描述" type="textarea" v-model="registerForm.description"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Register',
  data() {
    return {
      registerForm: {
        name: '',
        password: '',
        description: ''
      },
      rules: {
        name: [{
          required: true,
          message: '请输入昵称',
          trigger: 'blur'
          },{
          min: 2,
          max: 10,
          message: '长度在2到10个字符之间'
          },{
          pattern: /^\w+$/,
          message: '只能输入字母、数字和下划线'
          }
        ],
        password: [{
          required: true,
          message: '请输入昵称',
          trigger: 'blur'
          },{
          min: 2,
          max: 10,
          message: '长度在5到12个字符之间'
          },{
          pattern: /^\w+$/,
          message: '只能输入字母、数字和下划线'
          }
        ],
      }
    }
  },

  methods: {
    register() {
      axios
        .post("api/user/register", {
          name: this.registerForm.name,
          password: this.registerForm.password,
          description: this.registerForm.description
        })
        .then(res => {
          if(res.data == 1) {
            this.$message({
                message: '注册成功',
                type: 'success'
            });
          }else {
            this.$message({
                message: '昵称已存在,注册失败',
                type: 'warning'
            });
          }
        })
        .catch(err => {
          console.log(err);
        })
    }
  }
}
</script>

<style>

</style>