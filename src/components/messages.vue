<template>
  <el-empty description="暂无消息" id="background" ></el-empty>
  <div v-for="notice in data" :key="notice.index">

    <br/>
  </div>
</template>

<script>
export default {
  props:['data'],
  name: "message"
}
</script>

<style scoped>

</style>