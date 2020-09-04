<template>
  <el-row>
    <el-col :span="14">
      <el-menu :default-active="path" mode="horizontal" :router="true">
        <template v-for="(item, index) in nav_menu_data">
          <el-menu-item :index = "item.path" :key = "index">
            {{item.title}}
          </el-menu-item>
        </template>
      </el-menu>
    </el-col>
    <el-col :span="7" style="height:50px;margin-top:10px">
      <el-form :inline="true" :model="formInline" style="height:50px">
        <el-form-item>
          <el-input v-model="formInline.tag" placeholder="请输入标签搜索"></el-input>
        </el-form-item>
        <el-form-item>
          <nuxt-link :to="'/search/' + formInline.tag">搜索</nuxt-link>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="3" style="margin-top:10px">
      <el-dropdown type="text" v-if="$store.state.isLogin">
        <span><i class="el-icon-user-solid" circle>：</i>{{$store.state.user}}</span>
        <el-dropdown-menu slot="dropdown" style="padding:0px;margin:0px">
          <el-dropdown-item><el-button @click="quitLogin" type="text">退出</el-button></el-dropdown-item>
          <el-dropdown-item><el-button @click="signOff" type="text">注销</el-button></el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-button type="text" @click="dialogLoginVisible = true" v-else>登录</el-button>
      <el-dialog
        title="登录"
        :visible.sync="dialogLoginVisible"
        width="30%">
        <Login 
        v-bind:dialogLoginVisible="dialogLoginVisible"
        v-on:changeLoginDialog="sonChange($event)"></Login>
      </el-dialog>
      <el-button type="text" @click="dialogRegisterVisible = true">注册</el-button>
      <el-dialog
        title="注册"
        :visible.sync="dialogRegisterVisible"
        width="30%">
        <Register></Register>
      </el-dialog>
    </el-col>
  </el-row>
</template>

<script>
import Login from '~/components/Login.vue'
import Register from '~/components/Register.vue'
import axios from 'axios'

export default {
  components: {
    Login,
    Register
  },
  
  name: 'Header',
  data() {
    return {
      activeIndex: '1',
      path: '',
      nav_menu_data: [
        {
          title: '首页',
          path: '/'
        },
        {
          title: '发现',
          path: '/find'
        },
        {
          title: '收藏',
          path: '/collect'
        },
        {
          title: '作品',
          path: '/work'
        },
        {
          title: '关注',
          path: '/follow'
        }
      ],
      dialogLoginVisible: false,
      dialogRegisterVisible: false,
      formInline: {
        tag: ''
      }
    }
  },
  methods: {
    quitLogin() {
      this.cookie.setCookie('user','',-1);
      this.$store.commit('offline');
    },
    sonChange(msg) {
      this.dialogLoginVisible = msg;
    },
    signOff() {
      this.$confirm('此操作将永久注销账户?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios
          .get("/api/user/signOff", {
            params: {
              name: this.cookie.getCookie('user')
            }
          }).then(res => {
            if(res.data > 0) {
              this.$message({
                message: '注销成功',
                type: 'success'
              });
              this.quitLogin();
            } else {
              this.$message({
                message: '注销失败',
                type: 'warning'
              });
            }
          }).catch(err => {
            console.log(err);
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    }
  }
}
</script>
<style>
  a {
    text-decoration: none;
  }
</style>