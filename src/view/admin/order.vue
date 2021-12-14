<template>
  <el-container>
    <el-container>
      <el-main>
  <el-table :data="orders" style="width: 100%;z-index: -1">
    <el-table-column prop="id" label="Id"  />
    <el-table-column prop="driverId" label="司机Id" />
    <el-table-column prop="chunkId" label="货车Id" />
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
      orders: [],
    }
  },
  created() {
    this.getOrders()
  },
  mounted() {
    document.title="订单管理"
  },
  methods:{
    getOrders(){
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-orders').then(res=>{
        this.orders=res.data.orders//将这个用户的数据库的所有orders都push到cards，一个orderData为一个元素
      })
    },
  },
}
</script>

<style scoped>
</style>