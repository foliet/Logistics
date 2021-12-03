
<template>
  <el-container style="height: 100vh; ">
    <el-aside width="15%" style="background-color: rgb(238, 241, 246)">
      <el-menu router default-active="home">
        <el-menu-item index="home" @click="this.$router.push('home')">
          <template #title >
            <el-icon><Message /></el-icon>
            <span>我的消息</span>
          </template>
        </el-menu-item>
        <el-menu-item index="2" @click="this.$router.push('order')">
          <template #title>
            <el-icon><Menu /></el-icon>
            <span>我的资料</span>
          </template>
        </el-menu-item>
        <el-sub-menu index="3">
          <template #title>
            <el-icon><Setting /></el-icon>我的订单
          </template>
          <el-menu-item index="3-1">我寄出的</el-menu-item>
          <el-menu-item index="3-2">我收到的</el-menu-item>
          <el-menu-item index="3-3">在路上的</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-container>
      <router-view></router-view>
    </el-container>
  </el-container>
</template>

<script>
import { Message, Menu, Setting } from '@element-plus/icons'

export default ({
  components: {
    Message,
    Setting,
    Menu,
  },
})
</script>
