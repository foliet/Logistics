<template>
  <router-view></router-view>
</template>

<script>
export default {
  title: "App",
  data(){
    return{
      user:null
    }
  },
  created() {
    this.$axios.get("https://mc.rainspace.cn:4443/get-user").then(res=>{
      if(res.data.status>=10) {
        if(this.$route.path!=='/login'){
          this.$router.replace('/login')
          this.$message.error(res.data.msg)
        }
      }
    })
  }
};
</script>

