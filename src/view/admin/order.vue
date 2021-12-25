<template>
  <el-container>
    <el-container>
      <el-main>
        <div style="height: 96%">
        <el-table :data="orders.slice((currentPage-1)*pageSize,currentPage*pageSize)"
           height="94%" style="width: 100%;">
          <el-table-column label="Id" prop="id"/>
          <el-table-column label="寄件人名字" prop="senderName"/>
          <el-table-column label="取件人名字" prop="receiverName"/>
          <el-table-column label="状态" prop="statusName"/>
          <el-table-column label="操作">
            <template #default="scope">
              <div>
                <el-popover
                    :width="200"
                    placement="right"
                    title="处理该订单："
                    trigger="click"
                >
                  <el-select v-model="scope.row.chunk" :disabled="scope.row.status!==0" placeholder="Select chunk">
                    <el-option
                        v-for="item in chunks"
                        :key="item.status"
                        :label="item.model"
                        :value="item.id"
                    >
                      {{ item.model }}
                    </el-option>
                  </el-select>
                  <div class="space1"></div>
                  <el-select v-model="scope.row.staff" :disabled="scope.row.status!==0" placeholder="Select staff">
                    <el-option
                        v-for="item in staffs"
                        :key="item.status"
                        :label="item.name"
                        :value="item.id"
                    >
                      {{ item.name }}
                    </el-option>
                  </el-select>
                  <div class="space1"></div>
                  <el-button :disabled="scope.row.status!==0" style="float: right" @click="matching(scope.row)">确定
                  </el-button>
                  <template #reference>
                    <el-button>处理订单</el-button>
                  </template>
                </el-popover>

              </div>
            </template>
          </el-table-column>
          <el-table-column>
            <template #default="scope">
              <el-button @click="deleted(scope.row)">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column>

          </el-table-column>
        </el-table>
        </div>
        <div style="text-align: center">
        <el-pagination :current-page="currentPage" @current-change="currentChange"
                       background layout="prev, pager, next, jumper" :total="orders.length" :page-size="pageSize">
        </el-pagination>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      currentPage: 1,
      pageSize: 7,
      orders: [],
      chunks: [],
      staffs: [],
    }
  },
  created() {
    this.getOrders()
    this.getChunks()
    this.getStaffs()
  },
  mounted() {
    document.title = "订单管理"
  },
  methods: {
    currentChange(index){
      this.currentPage=index
    },
    getOrders() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-orders').then(res => {
        this.orders.length=0
        for(const order of res.data.orders){
          if(order.status===0){
            order.statusName='待出仓'
          }else if(order.status===1){
            order.statusName='运输中'
          }else if(order.status===2){
            order.statusName='待收货'
          }else{
            order.statusName='已送达'
          }
          this.orders.push(order)
        }
      })
    },
    getChunks() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-chunks').then(res => {
        this.chunks.length=0
        this.chunks = res.data.chunks.filter(chunk=>chunk.status===0)
      })
    },
    getStaffs() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-staffs').then(res => {
        this.staffs.length=0
        this.staffs = res.data.staffs.filter(staff=>staff.status===0)
      })
    },
    matching(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/match', {orderId: row.id, chunkId: row.chunk, staffId: row.staff}).then(()=>{
        this.getOrders()
        this.getChunks()
        this.getStaffs()
        setTimeout(()=>{
          this.$axios.post("https://mc.rainspace.cn:4443/admin/arrival",{orderId:row.id})
        },10000)
      })
    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/delete-order', {orderId: row.id}).then(()=>{
        this.getOrders()
        this.getChunks()
        this.getStaffs()
      })
    }
  }
}
</script>

<style scoped>
.space1 {
  padding: 10px 0;
}
</style>