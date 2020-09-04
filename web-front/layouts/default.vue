<template>
  <el-container>
    <el-aside width="150px"></el-aside>
    <el-main>
      <Header></Header>
      <nuxt/>
    </el-main>
    <el-aside width="150px"></el-aside>
  </el-container>
</template>

<script>
import Header from '~/components/Header.vue'

export default {
  components: {
    Header
  },
  data() {
    return {

    }
  },
  mounted() {
    var user = this.cookie.getCookie('user');
    if(user != ''){
      this.$store.commit('online');
      this.$store.commit('login',user);
    }
  }
}

</script>

<style>
  .el-main {
    padding: 0px;
  }
</style>

