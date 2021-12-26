<template>
  <el-container style="height: 100vh; ">
    <el-header id="head">
      <span class="title">RainSpace 物流</span>
      <a>
        <el-icon id="home-icon" @click="showCard=true">
          <HomeFilled/>
        </el-icon>
      </a>
      <div id="card" :style="{display: showCard?'inline':'none'}">
        <el-card shadow="always" style="margin-left: 58em;"
                 :body-style="{ padding: '0' }">
          <template #header>
            <a style="float: right" @click.stop="showCard=false">
              <el-icon>
                <Close/>
              </el-icon>
            </a>
            <span style="font-size: 1rem;font-weight: 500;line-height: 2;">{{ user.username }}</span>
            <div style="font-size: 0.8rem; color: rgba(0, 0, 0, 0.54);line-height: 1.5;">
              {{ user.email }}
            </div>
            <el-tag type="success">{{ user.groupId == 1 ? '管理员' : '普通用户' }}</el-tag>
          </template>
          <el-menu router :default-active="this.$route.path">
            <el-menu-item index="/admin" route="/admin">
              <template #title>
                <el-icon style="color: #FFB500">
                  <User/>
                </el-icon>&nbsp;
                <span>管理面板</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/logout" route="/logout">
              <template #title>
                <el-icon style="color: #FF3D00">
                  <SwitchButton/>
                </el-icon>&nbsp;
                <span>退出登录</span>
              </template>
            </el-menu-item>
          </el-menu>
        </el-card>
      </div>
    </el-header>
    <el-container>
      <el-aside width="15%" style="background-color: rgb(238, 241, 246)">
        <el-menu router :default-active="this.$route.path">
          <el-menu-item index="/home" route="/home">
            <template #title>
              <el-icon>
                <Message/>
              </el-icon>
              <span class="list">我的消息</span>
              <el-badge v-if="user.unreadMessage>0" :value="user.unreadMessage" style="display: inline;margin-bottom: 37px;
  margin-left: 15px;"/>
            </template>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title>
              <el-icon>
                <Menu/>
              </el-icon>
              <span class="list">我的资料</span>
            </template>
            <el-menu-item index="/profile" route="/profile"><span class="list">基本信息</span></el-menu-item>
            <el-menu-item index="/directory" route="/directory"><span class="list">联系地址</span></el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon>
                <Setting/>
              </el-icon>
              <span class="list">我的订单</span>
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
import {HomeFilled, Menu, Message, Setting, User, SwitchButton, Close} from '@element-plus/icons'

export default {
  data() {
    return {
      onActive: [],
      user: {},
      showCard: false,
    }
  },
  created() {
    this.$axios.get('https://mc.rainspace.cn:4443/get-user').then(res => {
      if (res.data.status < 10) {
        this.user = res.data.user
      } else {
        this.$router.replace("/login")
        this.$message.error(res.data.msg)
      }
    })
  },
  components: {
    Message,
    Setting,
    Menu,
    HomeFilled,
    User,
    SwitchButton,
    Close
  }
}
</script>
<style>
.title {
  font-size: 1.2em;
  font-weight: 500;
}

a {
  cursor: pointer;
}

#home-icon {
  float: right;
  font-size: 2em;
  margin-top: 0.4em;
}

.list {
  font-size: 1rem;
}

#head {
  background-color: #3f51b5;
  color: #fff;
  line-height: 60px;
  box-shadow: 0 0 7px 3px #aaa;
  z-index: 1;
}

#card {
  display: none;
}
</style>