<template>
  <el-container class="nameness">
    <el-main>
      <el-card v-for="order in orders" :key="order.create_at" @confirm="getOrders">
        <div>货物名称：{{order.title}}</div>
      </el-card>
    </el-main>
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
    document.title="我的订单"
  },
  methods:{
    getOrders(){
      this.$axios.get('https://mc.rainspace.cn:4443/get-orders?type=all').then(res=>{
        this.orders=res.data.orders//将这个用户的数据库的所有orders都push到cards，一个orderData为一个元素
      })
    },
    showDialog() {
      this.$refs.a.dialogVisible = true;
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