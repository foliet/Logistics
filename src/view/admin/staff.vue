<template>
  <el-container>
    <el-container>
      <el-main>
  <el-table :data="staffs" style="width: 100%;z-index: -1">
    <el-table-column prop="id" label="Id"  />
    <el-table-column prop="name" label="姓名" />
    <el-table-column prop="gender" label="性别" />
    <el-table-column prop="status" label="状态" />
  </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  data(){
    return{
      staffs: [],
    }
  },
  created() {
    this.getStaffs()
  },
  mounted() {
    document.title="员工管理"
  },
  methods:{
    getStaffs(){
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-staffs').then(res=>{
        this.staffs=res.data.staffs//将这个用户的数据库的所有staffs都push到cards，一个staffData为一个元素
      })
    },
  },
}
</script>

<style scoped>
</style>