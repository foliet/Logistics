<template>
  <el-container class="nameness">
    <el-main>
      <od-dialog ref="a"></od-dialog>
      <el-card v-for="order in orders" :key="order.create_at">
        <div>货物名称：{{order.title}}</div>
      </el-card>
    </el-main>
    <el-footer :style="{display: type==='send'?'':'none'}" height="40px" class="nameless" @click="showDialog">
      <div class="align">
        <el-icon size="23"><circle-plus/></el-icon>
        新增
      </div>
    </el-footer>
  </el-container>
</template>
<script>
import {CirclePlus} from "@element-plus/icons";
import dialog from '../components/dialog'
export default {
  props:['type'],
  components:{
    'od-dialog': dialog,
    CirclePlus
  },
  data(){
    return{
      orders: [],
    }
  },
  watch:{
    type(){
      this.getOrders()
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
      this.$axios.get('https://mc.rainspace.cn:4443/get-orders?type='+this.type).then(res=>{
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