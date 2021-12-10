
<template>
  <el-container style="height: 100vh; ">
    <el-header style="{
  letter-spacing: 20px;
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}"><span>物流管理系统仪表盘</span>
    </el-header>
    <el-container>
      <el-aside width="15%" style="background-color: rgb(238, 241, 246)">
        <el-menu router :default-active="this.$route.path.substr(7)">
          <el-menu-item index="home" route="./home">
            <template #title >
              <el-icon><Message /></el-icon>
              <span>站点统计</span><el-badge  :value="5" style="display: inline;margin-bottom: 37px;
  margin-left: 15px;"/>
            </template>
          </el-menu-item>
          <el-menu-item index="user" route="./user">用户</el-menu-item>
          <el-menu-item index="staff" route="./staff">员工</el-menu-item>
          <el-menu-item index="chunk" route="./chunk">车辆</el-menu-item>
          <el-menu-item index="order" route="./order">订单</el-menu-item>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><Setting /></el-icon>我的订单
            </template>
            <el-menu-item index="/orders/send" route="./orders/send">我寄出的</el-menu-item>
            <el-menu-item index="/orders/receive" route="./orders/receive">我收到的</el-menu-item>
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
import { Message, Setting } from '@element-plus/icons'

export default {
  data(){
    return{
      onActive:[]
    }
  },
  inject:['user'],
  components: {
    Message,
    Setting,
  },
  created() {
    this.$axios.get('https://mc.rainspace.cn:4443/get-user').then(res=>{
      if(res.data.status<10){
        if(res.data.user.groupId!==1){
          this.$router.go(-1)
          this.$message.error('不具有管理员权限！')
        }
      } else {
        this.$message.error(res.data.msg)
      }
    })
  }
}
</script>
