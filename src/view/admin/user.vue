<template>
  <el-table :data="users" style="width: 100%">
    <el-table-column prop="id" label="Id"  />
    <el-table-column prop="username" label="用户名" />
    <el-table-column prop="email" label="Email"/>
    <el-table-column prop="groupId" label="用户组" />
  </el-table>
</template>

<script>
export default {
  data(){
    return{
      users:[]
    }
  },
  created() {
    this.$axios.get('https://mc.rainspace.cn:4443/admin/get-users').then(res=>{
      if(res.data.status<10){
        this.users=res.data.users
      } else {
        this.$message.error(res.data.msg)
      }
    })
  }
}
</script>

<style scoped>

</style>