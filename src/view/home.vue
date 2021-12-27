<template>
  <el-header style="text-align: right; font-size: 12px">
  </el-header>

  <el-main>
    <div v-for="notice in notices" :key="notice.id">
      <el-card shadow="hover">
        <div id="msgtitle">{{ notice.title }}</div>
        <el-divider></el-divider>
        <div style="font-size: 13px;color: #666666;">
          {{ notice.createAt.slice(0, 10) + ' ' + notice.createAt.slice(11, 19) }}
        </div>
        <br/>
        <div style="font-size: 15px">{{ notice.content }}</div>
      </el-card>
      <br/>
    </div>
    <el-empty description="暂无消息" :style="{display: notices.length>0?'none':'flex'}"></el-empty>
  </el-main>
</template>

<script>
export default {
  title: "user",
  data() {
    return {
      notices: [],
    }
  },
  created() {
    this.$axios.get("https://mc.rainspace.cn:4443/get-notices").then(res => {
      if (res.data.status < 10) this.notices = res.data.notices.sort(function (a, b) {
        return b.id - a.id
      })
    })
  },
  mounted() {
    document.title = "我的消息"
  },
}
</script>

<style scoped>
#msgtitle {
  font-size: 20px;
  letter-spacing: 0.02em;
  font-weight: bold;
}
</style>