<template>
  <div>
    <el-form 
    :model="loginForm" 
    ref="loginForm">
      <el-form-item prop="name">
        <el-input placeholder="请输入昵称" v-model="loginForm.name"></el-input>
      </el-form-item> 
      <el-form-item prop="password">
        <el-input placeholder="请输入密码" v-model="loginForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Login',
  props: ["dialogLoginVisible"],
  data() {
    return {
      loginForm: {
        name: '',
        password: ''
      }
    }
  },

  methods: {
    login() {
      if(this.$store.state.isLogin == true) {
        this.$message({
          message: '不要重复登录，请手动退出',
          type: 'warning'
        });
      }else {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            axios
            .get("/api/user/login",{
              params: {
                name: this.loginForm.name,
                password: this.loginForm.password
              }
            })
            .then(res => {
              //console.log(res.data);
              if(res.data == true) {
                  this.cookie.setCookie('user',this.loginForm.name, 3);
                  this.$store.commit('online');
                  this.$store.commit('login',this.loginForm.name);
                  this.$message({
                    message: '登录成功',
                    type: 'success'
                  });
                  this.$emit("changeLoginDialog", false);
                  this.$refs.loginForm.resetFields();
                  location.reload();
              }else {
                  this.$message({
                    message: '账号和密码不匹配，登录失败',
                    type: 'warning'
                  });
              }
            })
            .catch(err =>{
              console.log(err);
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
}
</script>

<style>

</style>