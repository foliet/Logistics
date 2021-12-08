
<template>
  <el-container style="height: 100vh; ">
    <el-header style="{
  letter-spacing: 20px;
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}"><span>RainSpace物流</span>
    </el-header>
    <el-container>
      <el-aside width="15%" style="background-color: rgb(238, 241, 246)">
        <el-menu router :default-active="this.$route.path">
          <el-menu-item index="/home" route="/home">
            <template #title >
              <el-icon><Message /></el-icon>
              <span>我的消息</span><el-badge  :value="5" style="display: inline;margin-bottom: 37px;
  margin-left: 15px;"/>
            </template>
          </el-menu-item>
          <el-sub-menu index="2" >
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>我的资料</span>
            </template>
            <el-menu-item index="/profile" route="/profile">基本信息</el-menu-item>
            <el-menu-item index="/directory" route="/directory">联系地址</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><Setting /></el-icon>我的订单
            </template>
            <el-menu-item index="/orders/send" route="/orders/send">我寄出的</el-menu-item>
            <el-menu-item index="/orders/receive" route="/orders/receive">我收到的</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { Message, Menu, Setting } from '@element-plus/icons'

export default {
  data(){
    return{
      onActive:[]
    }
  },
  components: {
    Message,
    Setting,
    Menu,
  },
}
</script>
