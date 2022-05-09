<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">
    <!--设置收缩按钮-->
    <div style="flex: 1">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 19px" @click="collapse"></span>
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="{ path: '/' }">homepage</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 70px; cursor: pointer">
      <span>Tom</span><i class="el-icon-setting" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>View</el-dropdown-item>
        <el-dropdown-item>Logout</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Function,
  },
  // 监听路由变化
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName
    }
  },
}
</script>

<style scoped>

</style>