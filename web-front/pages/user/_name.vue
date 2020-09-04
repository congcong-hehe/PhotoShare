<template>
  <div>
    <el-card>
      <el-row>
        <h3>用户名：{{user.userName}}</h3>
      </el-row>
      <el-row>
        <el-col :span="2" style="height:50px">
          <el-button :type="buttonType" style="margin-top:8px" ref="followButton" @click="userFollow">{{buttonContent}}</el-button>
        </el-col>
        <el-col :span="5" style="height:50px">
          <p>ta共有{{userPhotoList.length}}个作品</p>
        </el-col>
      </el-row>
      <el-row>
        <p>自我介绍：{{user.userDes}}</p>
      </el-row>
    </el-card>
    <el-card style="float:left; width:295px" v-for="(item,index) in userPhotoList" :key="index">
      <nuxt-link :to="'/picture/' + item.pictureName">
        <el-image 
          :src="'http://localhost:8080' + item.location"
          style="width:245px; height:245px"
          fit="fill"></el-image>
      </nuxt-link>
      <el-row>
        <el-col :span="2">
          <i class="fontFamily congcong-iconxihuan"></i>
        </el-col>
        <el-col :span="4">
          <i>{{item.photoLikes}}</i>
        </el-col>
        <el-col :span="2">
          <i class="fontFamily congcong-iconcollection"></i>
        </el-col>
        <el-col :span="4">
          <i>{{item.photoCollects}}</i>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      user: {},
      userPhotoList: [],
      buttonContent: '关注',
      buttonType: 'primary'
    }
  },
  methods: {
    getUserDetail() {
      axios
        .get("/api/user/getUserDetail", {
          params: {
            name: this.$route.params.name
          }
        })
        .then(res => {
          //console.log(res.data);
          this.user = res.data;
        })
        .catch(err => {
          console.log(err);
        })
    },
    getUserPhotoList() {
      axios.get("/api/photo/getMyPhotoList", {
        params: {
          name: this.$route.params.name
        }
      })
      .then(res => {
        //console.log(res.data);
        this.userPhotoList = res.data;
        for(let item of this.userPhotoList){
            item.pictureName = item.location.substring(14);
            let i = item.pictureName.indexOf('.');
            item.pictureName = item.pictureName.substring(0,i);
        }
      })
      .catch(err => {
        console.log(err);
      })
    },
    userFollow() {
      if(this.$store.state.isLogin == false) {
        this.$message({
          $message: '请首先登录',
          type: 'warning'
        })
      }else {
        if(this.buttonType == "primary") {
          axios
            .get("/api/user/userFollow", {
              params: {
                userName: this.cookie.getCookie('user'),
                followName: this.$route.params.name,
                num: 1
              }
            })
            .then(res => {
              if(res.data == 1) {
                this.buttonType = "info";
                this.buttonContent = '取关';
              }
            })
        }else {
          axios
            .get("/api/user/userFollow", {
              params: {
                userName: this.cookie.getCookie('user'),
                followName: this.$route.params.name,
                num: -1
              }
            })
            .then(res => {
              if(res.data == 1) {
                this.buttonType = "primary";
                this.buttonContent = '关注';
              }
            })
        }
      }
    },
    getIsFollow() {
      axios
        .get("/api/user/getIsFollow", {
          params: {
            followName: this.$route.params.name,
            userName: this.cookie.getCookie('user')
          }
        })
        .then(res => {
          //console.log(res.data);
          if(res.data == true) {
            this.buttonType = "info";
            this.buttonContent = '取关';
          }
        })
        .catch(err => {
          console.log(err);
        })
    }
  },
  mounted() {
    this.getUserDetail();
    this.getUserPhotoList();
    if(this.$store.state.isLogin){
      this.getIsFollow();
    }
  }
}
</script>

<style>

</style>