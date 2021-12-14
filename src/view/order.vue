<template>
  <el-container class="nameness">
    <el-header>
      <div style="padding:10px 0 0 0">
        <el-input v-model="search" placeholder="输入关键字" style="width: 93%" type="text"
                  @keypress.enter="searching"></el-input>
        <el-button style="width: 7%" @click="zero">重置</el-button>
      </div>
    </el-header>
    <el-main>
      <od-dialog ref="a"></od-dialog>
      <a v-for="order in orders" :key="order.createAt">
      <el-card  @confirm="getOrders" @click="dialogTableVisible = true" shadow="hover" :body-style="{ padding: '0px' }">
        <el-container >
          <el-header class="orderhead">
            <span style="font-weight: bolder;font-size: 13px;letter-spacing: 0.05em">
            {{ order.createAt.slice(0,10) }}
          </span>&nbsp;
            <span style="font-size: 13px;letter-spacing: 0.05em">
              id号：{{order.id}}
            </span>
            <span style="font-size: 12px;float:right;">
              点击可查看详细信息
            </span>
          </el-header>
            <el-container>
              <el-aside width="50%" class="orderbody">
                <el-icon style="color: #00BF96"><Goods /></el-icon>&nbsp;
                <span style="font-size: 14px;margin-bottom: 0.5em" >货物名称：</span>
                <span style="font-size: 14px;margin-left: 10%" >{{order.title}}</span>
              </el-aside>
              <div style="width: 100%" class="orderbody">
                <el-icon style="color: #FF8200"><Money/></el-icon>&nbsp;
                <span style="font-size: 14px;" >货物价值：</span>
                <span style="font-size: 14px;font-weight: bolder;margin-left: 10%" >
                  ￥{{order.value}}
                </span>
              </div>
            </el-container>
          <el-container>
            <el-aside width="50%" class="orderbody">
              <el-icon style="color: #FFB500"><User/></el-icon>&nbsp;
              <span style="font-size: 14px;margin-bottom: 0.5em" >收件人：</span>
              <span style="font-size: 14px; margin-left: 13%" >{{order.receiverName}}</span>
            </el-aside>
            <div style="width: 100%" class="orderbody">
              <el-icon style="color:#FF3D00"><UserFilled/></el-icon>&nbsp;
              <span style="font-size: 14px;" >发件人：</span>
              <span style="font-size: 14px;margin-left: 14%" >
                  {{order.senderName}}
                </span>
            </div>
          </el-container>
            <el-footer style="border: 1px gainsboro solid;padding-top: 0.5em;">
              <el-icon style="color: #03A9F4" ><ChatLineRound/></el-icon>&nbsp;
              <span style="font-size: 13px;font-weight:500">备注：{{order.remark}}</span>
            </el-footer>
            </el-container>
      </el-card>
        <br/>
      </a>
      <el-dialog title="商品详情" v-model="dialogTableVisible" >
        <el-descriptions class="margin-top" :column="3" direction="vertical" border>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #00BF96"><Goods /></el-icon>&nbsp;
              <span class="info">商品名</span>
            </template>
            kooriookami
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #FFB500"><User/></el-icon>&nbsp;
              <span class="info">收件人</span>
            </template>
            发发发
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color:#FF3D00"><UserFilled/></el-icon>&nbsp;
              <span class="info">发件人</span>
            </template>
            大大方方
          </el-descriptions-item>
            <el-descriptions-item>
              <template v-slot:label>
                <el-icon style="color: #FF3D00"><ShoppingCart /></el-icon>&nbsp;
                <span class="info">商品状态</span>
              </template>
              已购买
            </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #FF8200"><Money/></el-icon>&nbsp;
              <span class="info">商品价格</span>
            </template>
            3
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #FF4081"><Box/></el-icon>&nbsp;
              <span class="info">商品体积</span>
            </template>
            999
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon style="color: #03A9F4"><Suitcase/></el-icon>&nbsp;
              <span class="info">商品重量</span>
            </template>
            98
          </el-descriptions-item>
            <el-descriptions-item :span="2">
            <template v-slot:label>
              <el-icon style="color: #00BF96" ><Location/></el-icon>&nbsp;
              <span class="info">发货地址</span>
            </template>
            13333333
          </el-descriptions-item>
          <el-descriptions-item :span="3">
            <template v-slot:label>
              <el-icon style="color: #FF3D00" ><ChatLineRound/></el-icon>&nbsp;
              <span class="info">备注</span>
            </template>
            13333333
          </el-descriptions-item>
        </el-descriptions>
      </el-dialog>
    </el-main>
    <el-footer :style="{display: type==='send'?'':'none'}" height="40px" class="nameless" @click="showDialog">
      <div class="align">
        <el-button round style="width: 100%">
          <el-icon size="20">
            <circle-plus/>
          </el-icon>
          <span style="margin-left:1em ">新增</span>
        </el-button>
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
  UserFilled
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
    UserFilled
  },
  data() {
    return {
      orders: [],
      dialogTableVisible: false,
      search: '',
    }
  },
  watch: {
    type() {
      this.getOrders()
      this.search = null;
    }
  },
  created() {
    this.getOrders()
  },
  mounted() {
    document.title = "我的订单"
  },
  methods: {
    getOrders() {
      this.$axios.get('https://mc.rainspace.cn:4443/get-orders?type=' + this.type).then(res => {
        this.orders = res.data.orders//将这个用户的数据库的所有orders都push到cards，一个orderData为一个元素;
      })
    },
    showDialog() {
      this.$refs.a.dialogVisible = true;
    },
    searching() {
      this.searched = 0;
      const search = this.search;
      if (search) {
        this.orders = this.orders.filter(function (order_inner) {
          console.log(order_inner)
          return Object.keys(order_inner).some(function (key) {
            console.log(key)
            return String(order_inner[key]).toLowerCase().indexOf(search) > -1
          })
        })
      }
    },
    zero() {
      this.$axios.get('https://mc.rainspace.cn:4443/get-orders?type=' + this.type).then(res => {
        this.orders = res.data.orders//将这个用户的数据库的所有orders都push到cards，一个orderData为一个元素;
      })
    }
  }
}
</script>

<style scoped>
.align{
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: justify;
  height:37px;
}

.nameless{
  padding:0;

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
</style>