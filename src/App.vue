<template>
  <router-view></router-view>
</template>

<script>
import Cookie from "@/assets/cookie";
export default {
  title: "App",
  created: function (){
    if(this.$route.fullPath!='/login'&&Cookie.get('id')==''){
      this.$router.replace('/login')
    }
  }
};
</script>

