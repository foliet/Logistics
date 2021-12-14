<template>
  <el-container>
    <el-container>
      <el-main>
  <el-table :data="chunks" style="width: 100%;z-index: -1">
    <el-table-column prop="id" label="Id"  />
    <el-table-column prop="number" label="车牌号" />
    <el-table-column prop="model" label="型号" />
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
      chunks: [],
    }
  },
  created() {
    this.getChunks()
  },
  mounted() {
    document.title="车辆管理"
  },
  methods:{
    getChunks(){
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-chunks').then(res=>{
        this.chunks=res.data.chunks
      })
    },
  },
}
</script>

<style scoped>
</style>