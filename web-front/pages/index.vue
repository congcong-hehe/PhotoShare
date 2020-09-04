<template>
  <el-container>
    <el-main>
      <el-row style="padding:30px">
        <el-col :span="16" :offset="4">
          <el-carousel>
            <el-carousel-item v-for="url in urls" :key="url">
              <el-image :src="url"></el-image>
            </el-carousel-item>
          </el-carousel>
        </el-col>
      </el-row>
      <el-row>
        <el-card>
          <div slot="header">
            <span>本月推荐</span>
          </div>
          <div>
            <el-row>
              <el-col v-for="(item,index) in photoRecomList" :key="index" :span="6">
                <el-card>
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
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-row>
    </el-main>
    <el-footer style="text-align:center; line-height:50px">
      <el-row>
        <el-col :offset="5" :span="4"><nuxt-link to="/about">关于本站</nuxt-link></el-col>
        <el-col :span="4"><el-link :underline="false">CSDN</el-link></el-col>
        <el-col :span="4"><el-link :underline="false">Gitee</el-link></el-col>
      </el-row>
    </el-footer>
  </el-container>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      carUrlList: [],
      photoRecomList: [],
      urls: [
        'http://localhost:8080/upload/source/1.png',
        'http://localhost:8080/upload/source/2.png'
      ],
    }
  },
  mounted() {
    this.getPhotoList();
  },
  methods: {
    getPhotoList() {
      axios.get("/api/photo/getShowPhoto")
        .then(res => {
          //console.log(res.data);
          this.photoRecomList = res.data;
          for(let item of this.photoRecomList){
            item.pictureName = item.location.substring(14);
            let i = item.pictureName.indexOf('.');
            item.pictureName = item.pictureName.substring(0,i);
          }
          //console.log(this.photoRecomList);
        })
        .catch(err => {
          console.log(err);
        })
    }
  } 
}
</script>

<style>
  .el-carousel__item {
     background-color: #99a9bf;
  }
</style>
