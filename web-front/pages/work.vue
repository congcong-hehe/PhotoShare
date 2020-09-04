<template>
  <div>
    <div v-if="$store.state.isLogin"> 
      <el-row>
        <el-card style="width:1000px">
          <div slot="header">
            <el-row>
              <el-col :span="18">
                <span>上传图片</span>
              </el-col>
              <el-col :span="3" :offset="3">
                <el-button type="primary" style="float:right" @click="submitUpload">上传</el-button>
              </el-col>
            </el-row>
          </div>
          <div>
            <el-upload
              ref="upload"
              action="/api/photo/upload"
              list-type="picture-card"
              :auto-upload="false"
              :limit="3"
              :data="upData"
              :on-exceed="handleExceed"
              :file-list="fileList"
              :on-success="handleSuccess"
              :before-upload="beforeAvatarUpload"
              :on-change="change"
              :class="{disabled:uploadDisabled}"
              :on-remove="handleRemove"
              :on-preview="handlePictureCardPreview">
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
            <el-form :model="photoForm" ref="photoForm" style="margin-top:15px">
              <el-form-item label="图片标签" prop="dynamicTags">
                <el-tag
                  :key="tag"
                  v-for="tag in photoForm.dynamicTags"
                  closable
                  :disable-transitions="false"
                  @close="handleClose(tag)">
                  {{tag}}
                </el-tag>
                <el-input
                  class="input-new-tag"
                  v-if="inputVisible"
                  v-model="inputValue"
                  ref="saveTagInput"
                  size="small"
                  @keyup.enter.native="handleInputConfirm"
                  @blur="handleInputConfirm">
                </el-input>
                <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
              </el-form-item>
              <el-form-item label="图片描述" prop="des">
                <el-input v-model="photoForm.des"></el-input>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-row>
      <el-row>
        <el-card style="float:left; width:295px" v-for="(item,index) in myPhotoList" :key="index">
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
            <el-col :span="5" :offset="6">
              <el-button type="primary" size="mini" @click="deletePhoto(item.location,index)">删除</el-button>
            </el-col>
          </el-row>
        </el-card>
      </el-row>
    </div>
    <div v-else>
      请首先登录。。。
    </div>
  </div>
</template>

<script>
import Login from '~/components/Login.vue';

import axios from 'axios';

export default {
  components: {
    Login
  },
  data() {
    return {
      count: 0,
      fileList: [],
      myPhotoList: [],
      photoForm: {
        des: '',
        dynamicTags: [],
      },
      inputVisible: false,
      inputValue: '',
      uploadDisabled: false,
      dialogVisible: false,
      dialogImageUrl: ''
    }
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    },

    handleSuccess(res) {
      //console.log(res);
      this.$refs.photoForm.resetFields();
      if(res != null) {
        var item = res;
        item.pictureName = item.location.substring(14);
        this.$notify({
          title: '通知',
          message: item.pictureName.substring(36) +  '上传成功,信息已经被存入区块链',
          duration: 3000
        });
        let i = item.pictureName.indexOf('.');
        item.pictureName = item.pictureName.substring(0,i);
        this.myPhotoList.push(item);
      }else {
        this.$alert('该照片已经被人所有', '警告', {
          confirmButtonText: '确定',
        });
      }
    },

    handleExceed() {
      this.$message({
        message: '最多只能上传三个文件',
        type: 'warning'
      });
    },

    getMyPhotoList() {
      axios.get("/api/photo/getMyPhotoList", {
        params: {
          name: this.cookie.getCookie('user')
        }
      })
      .then(res => {
        //console.log(res.data);
        this.myPhotoList = res.data;
        for(let item of this.myPhotoList){
            item.pictureName = item.location.substring(14);
            let i = item.pictureName.indexOf('.');
            item.pictureName = item.pictureName.substring(0,i);
        }
      })
      .catch(err => {
        console.log(err);
      })
    },

    deletePhoto(location,index) {
      axios
        .get("/api/photo/deletePhoto", {
          params: {
            location: location
          }
        })
        .then(res => {
          if(res.data == 1) {
            //console.log(index);
            this.myPhotoList.splice(index,1);
          }
        })
        .catch(err => {
          console.log(err);
        })
    },

    beforeAvatarUpload(file) {
      var isJPG = false;
      if(file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png') {
        isJPG = true;
      }
      var isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传文件只能是图片格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },

    change(file) {
      //console.log(file.status);
      if(file.status == "ready") {
        this.uploadDisabled = true;
      }
    },

    handleClose(tag) {
        this.photoForm.dynamicTags.splice(this.photoForm.dynamicTags.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.photoForm.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    },

    handleRemove() {
      this.uploadDisabled = false;
      this.$refs.photoForm.resetFields();
    },

    handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
    }
  },
  computed: {
    upData: function() {
      //console.log(this.photoForm.dynamicTags);
      return {
        name: this.cookie.getCookie('user'),
        des: this.photoForm.des,
        tag: this.photoForm.dynamicTags.join('-'),
      }
    }
  },
  mounted() {
      this.getMyPhotoList();
  }
}
</script>

<style>
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
  .disabled .el-upload--picture-card {
    display: none;
}
</style>