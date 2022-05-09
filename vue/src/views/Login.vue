<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>Log In</b></div>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
      <div style="margin: 10px 0; text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="login">Log In</el-button>
        <el-button type="primary" size="small" autocomplete="off">Sign Up</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {}
    }
  },
  methods: {
    login() {

    }
  }
}
</script>

<style>
  .wrapper {
    height: 100vh; /*百分百窗口高度*/
    background-image: linear-gradient(to bottom right, #FC466B , #3F5EFB); /*渐变色*/
    overflow: hidden; /*超出部分隐藏掉*/
  }
</style>