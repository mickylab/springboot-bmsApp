<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48, 65, 86)" text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
      <b style="color: white" v-show="logoTextShow">Backstage Management System</b>
    </div>
    <el-menu-item index="/">
      <template slot="title">
        <i class="el-icon-message"></i>
        <span slot="title">Homepage</span>
      </template>
    </el-menu-item>
    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-setting"></i>
        <span slot="title">Setting</span>
      </template>
      <el-menu-item index="/user">
        <i class="el-icon-s-custom"></i>
        <template slot="title">User Setting</template>
      </el-menu-item>
      <el-menu-item index="/file">
        <i class="el-icon-document"></i>
        <template slot="title">File Setting</template>
      </el-menu-item>
    </el-submenu>
    <el-menu-item index="/im">
      <template slot="title">
        <i class="el-icon-chat-dot-round"></i>
        <span slot="title">Chatting Room</span>
      </template>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'Aside',
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
