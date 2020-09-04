<template>
  <div>
    <el-card>
      <el-image
        :src="'http://localhost:8080' + photo.location"
        fit="fill">
        <div slot="error"></div>
      </el-image>
      <el-row>
        <el-col :span="2">上传时间：</el-col>
        <el-col :span="5">{{photo.storeTime}}</el-col>
      </el-row>
      <el-row style="line-height:50px">
        <el-col :span="1">
          <i class="fontFamily congcong-iconyonghu"></i>
        </el-col>
        <el-col :span="4">
          <nuxt-link :to="'/user/' + photo.userName">{{photo.userName}}</nuxt-link>
        </el-col>
        <el-col :span="1">
          <el-button 
          style="padding:0px;border:0px" 
          :class="likeButtonClass"
           @click="photoLike"></el-button>
        </el-col>
        <el-col :span="4">
          <p style="margin:0px">{{photo.photoLikes}}</p>
        </el-col>
        <el-col :span="1">
          <el-button 
          style="padding:0px;border:0px" 
          :class="collectButtonClass" 
          @click="photoCollect"></el-button>
        </el-col>
        <el-col :span="4">
          <p style="margin:0px">{{photo.photoCollects}}</p>
        </el-col>
      </el-row>
      <el-row>
        <el-tag :key="tag" v-for="tag in photo.photoTag" type="info">{{tag}}</el-tag>
      </el-row>
    </el-card>
    <el-card>
      <div slot="header">
        <span><h3>描述</h3></span>
      </div>
      <div>
        <p>{{photo.photoDes}}</p>
      </div>
      </el-card>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      photo: {},
      likeButtonClass: "fontFamily congcong-iconxihuan",
      collectButtonClass: "fontFamily congcong-iconcollection",
    }
  },
  methods: {
    async getPhotoDetail() {
      await axios
        .get("/api/photo/getPhotoDetail", {
          params: {
            name: this.$route.params.id
          }
        })
        .then(res => {
          //console.log(res.data);
          this.photo = res.data;
          var time = this.photo.storeTime;
          time = time.slice(0,10) + ' ' +  time.slice(11,19);
          this.photo.storeTime = time;
          if(this.photo.photoTag != null) {
            this.photo.photoTag = this.photo.photoTag.split('-');
          }
          //console.log(this.photo.photoTag);
        })
        .catch(err => {
          console.log(err);
        })
    },
    photoLike() {
      if(!this.$store.state.isLogin) {
        this.$message({
          message: '请首先登录',
          type: 'warning'
        });
      }else{
        if(this.likeButtonClass == "fontFamily congcong-iconxihuan") {
          axios
            .get("/api/photo/photoLike", {
              params: {
                location: this.photo.location,
                num: 1
              }
            })
            .then(res => {
              //console.log(res.data);
              if(res.data == 1) {
                this.photo.photoLikes += 1;
                this.likeButtonClass = "fontFamily congcong-iconxihuaned";
              }
            })
            .catch(err => {
              console.log(err);
            })
        }else {
          axios
            .get("/api/photo/photoLike", {
              params: {
                location: this.photo.location,
                num: -1
              }
            })
            .then(res => {
              //console.log(res.data);
              if(res.data == 1) {
                this.photo.photoLikes -= 1;
                this.likeButtonClass = "fontFamily congcong-iconxihuan";
              }
            })
            .catch(err => {
              console.log(err);
            })
        }
      }
    },
    photoCollect() {
      if(!this.$store.state.isLogin) {
        this.$message({
          message: '请首先登录',
          type: 'warning'
        });
      }else {
        if(this.collectButtonClass == "fontFamily congcong-iconcollection") {
          axios
            .get("/api/photo/photoCollect", {
              params: {
                location: this.photo.location,
                name: this.cookie.getCookie('user'),
                num: 1
              }
            })
            .then(res => {
              //console.log(res.data);
              if(res.data == 2) {
                this.photo.photoCollects += 1;
                this.collectButtonClass = "fontFamily congcong-iconcollectioned";
              }
            })
            .catch(err => {
              console.log(err);
            })
        }else {
          axios
            .get("/api/photo/photoCollect", {
              params: {
                location: this.photo.location,
                name: this.cookie.getCookie('user'),
                num: -1
              }
            })
            .then(res => {
              //console.log(res.data);
              if(res.data == 2) {
                this.photo.photoCollects -= 1;
                this.collectButtonClass = "fontFamily congcong-iconcollection";
              }
            })
            .catch(err => {
              console.log(err);
            })
        }
      }
    },
    getIsCollect() {
      axios
        .get("/api/photo/getIsCollect", {
          params: {
            location: this.$route.params.id,
            name: this.cookie.getCookie('user')
          }
        })
        .then(res => {
          if(res.data == true) {
            this.collectButtonClass = "fontFamily congcong-iconcollectioned";
          }
        })
        .catch(err => {
          console.log(err);
        })
    },
    handleTime(time) {
      return time.slice(0,10) + ' ' +  time.slice(11,19);
    }
      
  },
  mounted() {
    this.getPhotoDetail();
    if(!this.$store.state.isLogin){
      this.getIsCollect();
    }
  }
}
</script>

<style>
  .el-card__header {
    padding: 10px;
  }
</style>