
<template>
  <el-container style="height: 100vh; ">
    <el-header id="head">
      <span class="title">RainSpace 物流</span>
      <a>
      <el-icon id="home-icon" @click="$router.push('/admin')"><HomeFilled/></el-icon>
      </a>
    </el-header>
    <el-container>
      <el-aside width="15%" style="background-color: rgb(238, 241, 246)">
        <el-menu router :default-active="this.$route.path">
          <el-menu-item index="/home" route="/home">
            <template #title >
              <el-icon><Message /></el-icon>
              <span class="list">我的消息</span><el-badge  :value="5" style="display: inline;margin-bottom: 37px;
  margin-left: 15px;"/>
            </template>
          </el-menu-item>
          <el-sub-menu index="2" >
            <template #title>
              <el-icon><Menu /></el-icon>
              <span class="list">我的资料</span>
            </template>
            <el-menu-item index="/profile" route="/profile"><span class="list">基本信息</span></el-menu-item>
            <el-menu-item index="/directory" route="/directory"><span class="list">联系地址</span></el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><Setting /></el-icon><span class="list">我的订单</span>
            </template>
            <el-menu-item index="/order/send" route="/order/send"><span class="list">我寄出的</span></el-menu-item>
            <el-menu-item index="/order/receive" route="/order/receive"><span class="list">我收到的</span></el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <router-view></router-view>
    </el-container>
  </el-container>
</template>

<script>
import {HomeFilled, Menu, Message, Setting} from '@element-plus/icons'

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
    HomeFilled
  },
}
</script>
<style>
.title{
  font-size: 1.2em;
  font-weight: 500;
}
a{
  cursor: pointer;
}
#home-icon{
  float:right;
  font-size: 2em;
  margin-top: 0.4em;
}
.list{
  font-size: 1rem;
}
#head{
  background-color: #3f51b5;
  color: #fff;
  line-height: 60px;
  box-shadow:0 0 7px 3px #aaa;
  z-index: 1;
}
</style>