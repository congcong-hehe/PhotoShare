<template>
  <div>
    <div v-if="$store.state.isLogin">
      <el-table :data="followList">
        <el-table-column label="用户名" prop="name">
          <template slot-scope="scope">
            <nuxt-link :to="'/user/' + scope.row.name">{{scope.row.name}}</nuxt-link>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="cancelFollow(scope.$index, scope.row)">取关</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-else>
      请首先登录。。。
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      followList: []
    }
  },
  methods: {
    cancelFollow(index, row) {
      //console.log(index, row);
      axios.get("/api/user/cancelFollow", {
        params:{
          userName:this.cookie.getCookie('user'),
          followName:row.name
        }
      }).then(res => {
        if(res.data == 1) {
          this.followList.splice(index,1);
        }
      }).catch(err => {
        console.log(err);
      })
    },
    getFollowList() {
      axios.get("/api/user/getFollowList", {
        params:{
          userName:this.cookie.getCookie('user'),
        }
      }).then(res => {
        //console.log(res.data);
        for(let item of res.data) {
          this.followList.push({
            name: item
          });
        }
      }).catch(err => {
        concole.log(err);
      })
    }
  },
  mounted() {
    if(this.$store.state.isLogin) {
      this.getFollowList();
    }
  }
}
</script>

<style>

</style>