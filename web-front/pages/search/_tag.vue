<template>
  <div>
    <div v-if="!isSearch">
      未找到符合的图片
    </div>
    <div v-else>
      <el-card style="float:left; width:295px" v-for="(item,index) in searchPhotoList" :key="index">
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
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      searchPhotoList: [],
      isSearch: false
    }
  },
  methods: {
    getSearchPhotoList() {
      axios.get("/api/photo/getSearchPhotoList", {
        params: {
          tag: this.$route.params.tag
        }
      })
      .then(res => {
        //console.log(res.data);
        this.searchPhotoList = res.data;
        if(this.searchPhotoList.length > 0) {
          this.isSearch = true;
          for(let item of this.searchPhotoList){
              item.pictureName = item.location.substring(14);
              let i = item.pictureName.indexOf('.');
              item.pictureName = item.pictureName.substring(0,i);
          }
        }
      })
      .catch(err => {
        console.log(err);
      })
    }
  },
  mounted() {
    this.getSearchPhotoList();
  }
}
</script>

<style>

</style>