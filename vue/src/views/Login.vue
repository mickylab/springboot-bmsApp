<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>Log In</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <div style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="medium" autocomplete="off" @click="login">Log In</el-button>
          <el-button type="warning" size="medium" autocomplete="off">Sign Up</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: "Please input a username!", trigger: "blur" },
          { min: 5, max: 10, message: "5 to 10 characters in length", trigger: "blur" }
        ],
        password: [
          { required: true, message: "Please input a password!", trigger: "blur" },
          { min: 5, max: 10, message: "5 to 10 characters in length", trigger: "blur" }
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) { // 表单校验是否合法, 表单数据合法的时候发送请求, 不合法时点击login也不发送请求
          this.request.post("/user/login", this.user).then(res => {
            if (!res) {
              this.$message.error("Either the username or password is invalid!")
            } else {
              this.$router.push("/")
            }
          })
        } else {
          return false;
        }
      });
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