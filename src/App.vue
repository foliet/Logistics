<template>
  <router-view v-slot="{ Component }">
    <transition name="fade">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<script>
export default {
  title: "App",
  created() {
    this.$axios.get("https://mc.rainspace.cn:4443/get-user").then(res=>{
      if(res.data.status>=10) {
        if(this.$route.path!=='/login'&&this.$route.path!=='/retrieve'){
          this.$router.replace('/login')
          this.$message.error(res.data.msg)
        }
      }
    })
  }
};
</script>
<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
