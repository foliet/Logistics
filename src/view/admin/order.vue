<template>
  <el-table :data="orders" style="width: 100%">
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
      orders: [],
    }
  },
  created() {
    this.getOrders()
  },
  mounted() {
    document.title="我的订单"
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
.align{
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: justify;
  border-style:solid solid solid none;
  border-width: 2px;
  color:black;
  height:37px;
}

.nameless{
  padding:0;

}
.nameness{
  margin: 0;
  display: flex;
  vertical-align: center;
  border-style:none none none solid;
  border-width: 3px;
  color:black;
}
</style>