<template>
  <el-header style="text-align: right; font-size: 12px">
  </el-header>

  <el-main>
    <el-card v-for="notice in notices" :key="notice.create_at" shadow="hover" >
      <template #header>
        <div style="font-size: 20px;font-weight: bold">{{notice.title}}</div>
      </template>
      <div style="font-size: 13px">{{notice.create_at}}</div>
      <br/>
      <div style="font-size: 15px">{{notice.content}}</div>
    </el-card>
    <el-empty description="暂无消息" :style="{display: notices.length>=0?'none':'flex'}" ></el-empty>
  </el-main>
</template>

<script>

export default {
  title: "user",
  data() {
    return{
      notices: [],
    }
  },
  created() {
    this.$axios.get("https://mc.rainspace.cn:4443/get-notices").then(res=>{
      if(res.data.status<10)this.notices=res.data.notices
    })
  },
  mounted() {
    document.title="我的消息"
  },
}
</script>

<style scoped>

</style>