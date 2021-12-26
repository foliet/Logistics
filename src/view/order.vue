<template>
  <el-container class="nameness">
    <el-header>
      <div style="padding:10px 0 0 0">
        <el-input v-model="search" placeholder="输入关键字搜索" style="width: 88%" type="text"
                  @input="searching"></el-input>
        <el-button :style="{display: type==='send'?'':'none'}" class="align" size="mini" @click="showDialog">
          <el-icon size="20">
            <circle-plus/>
          </el-icon>
          <span style="margin-left:1em ">新增</span></el-button>
      </div>
    </el-header>
    <el-main>
      <od-dialog ref="a"></od-dialog>
      <div v-for="order in currentOrders" :key="order.id">
      <el-card @confirm="getOrders" shadow="hover" :body-style="{padding: '0px' }">
        <el-container >
          <el-header class="orderhead">
            <span style="font-weight: bolder;font-size: 13px;letter-spacing: 0.05em">
            {{ order.createAt.slice(0,10) }}
          </span>&nbsp;
            <span style="font-size: 13px;letter-spacing: 0.05em">
              id号：{{order.id}}
            </span>
            <el-icon style="float:right;margin-top: 1.2em;font-size: 15px;cursor: pointer"
                     @click="dialogTableVisible = true;this.clickedOrder=order">
              <View />
            </el-icon>
            <span style="font-size: 12px;float:right;margin-right: 0.5em">
              点击可查看详细信息
            </span>
          </el-header>
          <el-container>
            <el-aside width="50%" class="orderbody">
              <el-icon style="color: #00BF96"><Goods /></el-icon>&nbsp;
              <span style="font-size: 14px;margin-bottom: 0.5em" >物品名称：</span>
              <span style="font-size: 14px;margin-left: 10%" >{{order.title}}</span>
            </el-aside>
            <div style="width: 100%" class="orderbody">
              <el-icon style="color: #FF8200"><Money/></el-icon>&nbsp;
              <span style="font-size: 14px;" >订单状态：</span>
              <span style="font-size: 14px;font-weight: bolder;margin-left: 10%" >
                  {{order.statusName}}
                </span>
            </div>
          </el-container>
          <el-container>
            <div style="width: 100%" class="orderbody">
              <el-icon style="color:#FF3D00"><UserFilled/></el-icon>&nbsp;
              <span style="font-size: 14px;" >发件人：</span>
              <span style="font-size: 14px;margin-left: 14%" >
                  {{order.senderName}}
                </span>
            </div>
            <el-aside width="50%" class="orderbody">
              <el-icon style="color: #FFB500"><User/></el-icon>&nbsp;
              <span style="font-size: 14px;margin-bottom: 0.5em" >收件人：</span>
              <span style="font-size: 14px; margin-left: 13%" >{{order.receiverName}}</span>
            </el-aside>
          </el-container>
          <el-footer style="border: 1px gainsboro solid;padding-top: 0.5em;">
            <el-icon style="color: #03A9F4" ><ChatLineRound/></el-icon>&nbsp;
            <span style="font-size: 13px;font-weight:500">备注：{{order.remark}}</span>
          </el-footer>
        </el-container>
        <el-button v-if="type==='receive'&&order.status===2" size="medium" round class="takegoods" @click="takeGoods(order.id)" >
          <span class="takegood" >
            确认收货
          </span>
        </el-button>
      </el-card>
      <br/>
      </div>
      <el-dialog title="商品详情" v-model="dialogTableVisible" >
        <el-descriptions class="margin-top" :column="3" direction="vertical" border>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #00BF96"><Goods /></el-icon>&nbsp;
              <span class="info">商品名</span>
            </template>
            {{clickedOrder.title}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #FFB500"><User/></el-icon>&nbsp;
              <span class="info">发件人</span>
            </template>
            {{ clickedOrder.senderName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color:#FF3D00"><UserFilled/></el-icon>&nbsp;
              <span class="info">收件人</span>
            </template>
            {{ clickedOrder.receiverName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #FF8200"><Money/></el-icon>&nbsp;
              <span class="info">物品价值</span>
            </template>
            ¥{{clickedOrder.value}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #FF4081"><Box/></el-icon>&nbsp;
              <span class="info">物品体积</span>
            </template>
            {{ clickedOrder.volume }}cm³
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #03A9F4"><Suitcase/></el-icon>&nbsp;
              <span class="info">物品重量</span>
            </template>
            {{clickedOrder.weight}}kg
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #FF3D00"><ShoppingCart /></el-icon>&nbsp;
              <span class="info">订单状态</span>
            </template>
            {{ clickedOrder.statusName }}
          </el-descriptions-item>
          <el-descriptions-item :span="2">
            <template v-slot:label>
              <el-icon style="color: #00BF96" ><Location/></el-icon>&nbsp;
              <span class="info">收货地址</span>
            </template>
            {{ clickedOrder.receiverAddress }}
          </el-descriptions-item>
          <el-descriptions-item :span="3">
            <template v-slot:label>
              <el-icon style="color: #FF3D00" ><ChatLineRound/></el-icon>&nbsp;
              <span class="info">备注</span>
            </template>
            {{ clickedOrder.remark }}
          </el-descriptions-item>
        </el-descriptions>
      </el-dialog>
    </el-main>
    <el-footer>
      <div style="padding-left: 40%">
      <el-pagination :current-page="currentPage" :page-size="pageSize" :total="orders.length" background
                     layout="prev, pager, next, jumper" style="width: 40%;float: left" @current-change="currentChange">
      </el-pagination>
      </div>
    </el-footer>
  </el-container>
</template>
<script>
import {
  Box,
  ChatLineRound,
  CirclePlus,
  Goods,
  Location,
  Money,
  ShoppingCart,
  Suitcase,
  User,
  UserFilled,
  View
} from "@element-plus/icons";
import dialog from '../components/dialog'

export default {
  props: ['type'],
  components: {
    'od-dialog': dialog,
    CirclePlus,
    User,
    Goods,
    Location,
    Money,
    Box,
    Suitcase,
    ShoppingCart,
    ChatLineRound,
    UserFilled,
    View
  },
  data() {
    return {
      pageSize:3,
      currentPage: 1,
      clickedOrder:{},
      currentOrders:[],
      orders: [],
      allOrders: [],
      dialogTableVisible: false,
      search: '',
    }
  },
  watch: {
    type() {
      this.getOrders()
      this.search = "";
    }
  },
  created() {
    this.getOrders()
  },
  mounted() {
    document.title = "我的订单"
  },
  methods: {
    currentChange(index){
      this.currentOrders=this.orders.slice((index-1)*this.pageSize,index*this.pageSize)
      this.currentPage=index
    },
    getOrders() {
      this.$axios.get('https://mc.rainspace.cn:4443/get-orders?type=' + this.type).then(res => {
        this.allOrders.length=0
        for(const order of res.data.orders.sort(function (a,b){
          if(a.status!==b.status){
            return a.status-b.status
          }
          return b.id-a.id
        })){
          if(order.status===0){
            order.statusName='待出仓'
          }else if(order.status===1){
            order.statusName='运输中'
          }else if(order.status===2){
            order.statusName='待收货'
          }else{
            order.statusName='已送达'
          }
          this.allOrders.push(order)
        }
        this.searching()
      })
    },
    showDialog() {
      this.$refs.a.dialogVisible = true;
    },
    searching() {
      const search = this.search;
      if (search.length>0) {
        this.orders = this.allOrders.filter(function (order_inner) {
          return Object.keys(order_inner).some(function (key) {
            return String(order_inner[key]).toLowerCase().indexOf(search) > -1
          })
        })
      } else {
        this.orders = this.allOrders.filter(function (){
          return true
        })
      }
      this.currentChange(1)
    },
    takeGoods(id){
      this.$axios.post("https://mc.rainspace.cn:4443/take-goods",{orderId:id}).then(()=>{
        this.getOrders()
      })
    }
  }
}
</script>

<style scoped>
.align {
  width: 10%;
  float: right;
  height: 40px;
}

.nameness{
  margin: 0;
  display: flex;
  vertical-align: center;
}
.info{
  font-weight: 400;
  color: black;
}
.orderhead{
  background-color: #f5f5f5;
  color: black;
  line-height: 3em;
  height: 3em;
}
.orderbody{
  padding-left: 2%;
  border: 1px gainsboro solid;
  padding-top: 0.5em;
  padding-bottom: 0.5em
}
.takegoods{
  margin:0.5em 0 0.5em 89%;
  border: #FF8200 1px solid;
  background-color: white;
}
.takegood{
  color: #FF8200;
}
.takegoods:hover{
  margin:0.5em 0 0.5em 89%;
  border: #FF8200 1px solid;
  background-color: #FF8200;
}
.takegoods:hover .takegood{
  color: white;
}
</style>