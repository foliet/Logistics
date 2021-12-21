<template>
  <el-container style="height: 100vh; ">
    <el-header id="head">
      <span class="title">物流管理系统仪表盘</span>
      <a>
        <el-icon id="home-icon" @click="showcard"><HomeFilled/></el-icon>
      </a>
      <div id="card">
        <el-card shadow="always" style="margin-left: 58em;z-index: 5"
                 :body-style="{ padding: '0' }" >
          <template #header >
            <a style="float: right" @click.stop="nocard">
              <el-icon><Close /></el-icon>
            </a>
            <span style="font-size: 1rem;font-weight: 500;line-height: 2;">{{ user.username }}</span>
            <div style="font-size: 0.8rem; color: rgba(0, 0, 0, 0.54);line-height: 1.5;">
              {{ user.email }}
            </div>
            <el-tag type="success">{{ user.groupId==1?'管理员':'普通用户' }}</el-tag>
          </template>
          <el-menu router :default-active="this.$route.path">
            <el-menu-item index="/home" route="../home">
              <template #title >
                <el-icon style="color: #03A9F4"><Document /></el-icon>&nbsp;
                <span >物流面板</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/logout" route="/logout" >
              <template #title >
                <el-icon style="color: #FF3D00"><SwitchButton/></el-icon>&nbsp;
                <span >退出登录</span>
              </template>
            </el-menu-item>
          </el-menu>
        </el-card>
      </div>
    </el-header>
    <el-container>
      <el-aside width="15%" style="background-color: rgb(238, 241, 246)">
        <el-menu router  :default-active="this.$route.path.substr(7)">
          <el-sub-menu index="home" route="./home">
            <template #title >
              <el-icon><Message /></el-icon>
              <span class="list">站点统计</span>
            </template>
            <el-menu-item index="user" route="./user">
              <template #title >
                <el-icon><User /></el-icon>
                <span class="list">用户</span>
              </template>
            </el-menu-item>
            <el-menu-item index="staff" route="./staff">
              <template #title >
                <el-icon><UserFilled /></el-icon>
                <span class="list">员工</span>
              </template>
            </el-menu-item>
            <el-menu-item index="chunk" route="./chunk">
              <template #title >
                <el-icon><Odometer /></el-icon>
                <span class="list">车辆</span>
              </template>
            </el-menu-item>
            <el-menu-item index="order" route="./order">
              <template #title >
                <el-icon><Document /></el-icon>
                <span class="list">订单</span>
              </template>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <router-view></router-view>
    </el-container>
  </el-container>
</template>

<script>
import {HomeFilled, Odometer, Message, UserFilled,User,SwitchButton,Close,Document} from '@element-plus/icons'

export default {
  data() {
    return {
      onActive: [],
      user:{},
    }
  },
  methods: {
    showcard: function () {
      var a = document.getElementById("card");
      a.style.display = "inline";
    },
    nocard: function () {
      var a = document.getElementById("card");
      a.style.display = "none";
    }
  },
  created() {
    this.$axios.get('https://mc.rainspace.cn:4443/get-user').then(res=>{
      if(res.data.status<10){
        this.user=res.data.user
      } else {
        this.$message.error(res.data.msg)
      }
    })
  },
  components: {
    Message,
    UserFilled,
    Odometer,
    HomeFilled,
    User,
    SwitchButton,
    Close,
    Document
  }
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
#card{
  display: none;
}
</style>