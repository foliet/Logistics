<template>
  <el-header style="text-align: right; font-size: 12px">
  </el-header>

  <el-main>
    <a >
    <el-card shadow="hover" v-for="notice in notices" :key="notice.createAt" @click="dialogTableVisible = true">
        <div id="msgtitle">{{notice.title}}</div>
      <el-divider></el-divider>
      <div style="font-size: 13px;color: #666666;">{{notice.createAt}}</div>
      <br/>
      <div style="font-size: 15px">{{notice.content}}</div>
    </el-card>
    </a>
    <el-empty description="暂无消息" :style="{display: notices.length>=0?'none':'flex'}" ></el-empty>
    <el-dialog title="商品详情" v-model="dialogTableVisible" id="good_dia" >
      <el-descriptions class="margin-top" :column="3" direction="vertical" border>
        <el-descriptions-item>
          <template v-slot:label>
            <el-icon><Goods /></el-icon>  商品名
          </template>
          kooriookami
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            <el-icon><Timer/></el-icon>  购买时间
          </template>
          18:00
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            <el-icon><Phone/></el-icon>
            商家电话
          </template>
          1800000000
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            <el-icon><User/></el-icon>
              配送员
          </template>
          大大方方
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            <el-icon><PhoneFilled/></el-icon>
              配送员电话
          </template>
          13333333
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            <el-icon><Money/></el-icon>
            商品价格
          </template>
          3
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            <el-icon><Box/></el-icon>
            商品体积
          </template>
          999
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            <el-icon><Suitcase/></el-icon>
            商品重量
          </template>
          98
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            <el-icon><ShoppingCart /></el-icon>
            商品状态
          </template>
          已购买
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </el-main>
</template>

<script>

import {Goods, Phone, Timer, User, PhoneFilled, Money,Box,Suitcase,ShoppingCart} from "@element-plus/icons";

export default {
  title: "user",
  data() {
    return{
      dialogTableVisible:false,
      notices: [],
    }
  },
  components: {
    User,
    Goods,
    Timer,
    Phone,
    PhoneFilled,
    Money,
    Box,
    Suitcase,
    ShoppingCart
  },
  created() {
    this.$axios.get("https://mc.rainspace.cn:4443/get-notices").then(res=>{
      if(res.data.status<10)this.notices=res.data.notices
    })
  },
  mounted() {
    document.title="我的消息"
  },
}
</script>

<style scoped>
#msgtitle{
  font-size: 20px;
  letter-spacing: 0.02em;
  font-weight: bold;
}
</style>