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
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">Navigator Two</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Group 1</template>
        <el-menu-item index="2-1">Option 1</el-menu-item>
        <el-menu-item index="2-2">Option 2</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="Group 2">
        <el-menu-item index="2-3">Option 3</el-menu-item>
      </el-menu-item-group>
      <el-submenu index="2-4">
        <template slot="title">Option 4</template>
        <el-menu-item index="2-4-1">Option 4-1</el-menu-item>
      </el-submenu>
    </el-submenu>
    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-setting"></i>
        <span slot="title">Setting</span>
      </template>
      <el-menu-item index="/user">
        <template slot="title">User Setting</template>
      </el-menu-item>
      <el-menu-item-group title="Group 2">
        <el-menu-item index="3-3">Option 3</el-menu-item>
      </el-menu-item-group>
      <el-submenu index="3-4">
        <template slot="title">Option 4</template>
        <el-menu-item index="3-4-1">Option 4-1</el-menu-item>
      </el-submenu>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: 'Aside',
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
