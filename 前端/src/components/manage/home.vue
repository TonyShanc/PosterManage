<template>
  <div>
    <div style="text-align: center">
      <el-menu default-active="$route.path"
               router
               class="el-menu-demo"
               mode="horizontal"
               @select="handleSelect"
               style="display: flex;justify-content: center"
      >
        <el-menu-item index="/manage/m_users">用户管理</el-menu-item>
        <el-menu-item index="/manage/m_picture">海报管理</el-menu-item>
      </el-menu>
    </div>
    <div><router-view></router-view></div>

  </div>

<!--  <users></users>-->

</template>

<script>

    export default {
        name: "home",
    }
</script>

<style scoped>

</style>
