<template>
  <div class="homeView">
    <!--100等分-->
    <!--侧边栏-->
    <el-container style="min-height: 100vh">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(0 21 41 / 35%)">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"/>
        </el-header>

        <el-main>
          <!--表示当前页面的子路会在router-view里面展示-->
          <router-view @refreshUser="getUser"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'HomeView',
  components: {Header, Aside},
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: {},
    }
  },
  created() {
    // 从后台获取最新的数据
    this.getUser()
  },
  methods: {
    // 点击收缩按钮触发
    collapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = 64;
        this.collapseBtnClass = 'el-icon-s-unfold';
        this.logoTextShow = false
      } else {
        this.sideWidth = 200;
        this.collapseBtnClass = 'el-icon-s-fold';
        this.logoTextShow = true
      }
    },
    getUser() {
      let username = JSON.parse(localStorage.getItem("user")) ? JSON.parse(localStorage.getItem("user")).username : ""
      // 从后台获取User数据
      this.request.get("/user/username/" + username).then(res => {
        // 重新赋值后台最新的 User 对象
        this.user = res.data
      })
    }
  }
}
</script>

<style>

</style>
