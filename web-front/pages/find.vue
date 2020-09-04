<template>
  <div>
    <el-card style="float:left; width:295px" v-for="(item,index) in newPhotoList" :key="index">
      <nuxt-link :to="'/picture/' + item.pictureName">
        <el-image 
          :src="'http://localhost:8080' + item.location"
          style="width:245px; height:245px"
          fit="fill"></el-image>
      </nuxt-link>
      <el-row>
        <el-col :span="2">
          <i class="fontFamily congcong-iconyonghu"></i>
        </el-col>
        <el-col :span="8">
          <nuxt-link :to="'/user/' + item.userName">{{item.userName}}</nuxt-link>
        </el-col>
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
import axios from 'axios'
export default {
  mounted() {
    this.getNewPhotoList();
  },

  data() {
    return {
      newPhotoList: []
    }
  },

  methods: {
    getNewPhotoList() {
      axios.get("/api/photo/getNewPhotoList")
      .then(res => {
        //console.log(res.data);
        this.newPhotoList = res.data;
        for(let item of this.newPhotoList){
            item.pictureName = item.location.substring(14);
            let i = item.pictureName.indexOf('.');
            item.pictureName = item.pictureName.substring(0,i);
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