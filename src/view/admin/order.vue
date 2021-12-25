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
          <el-table-column label="状态" prop="status"/>
          <el-table-column label="分配车辆">
            <template #default="scope">
              <div>
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
                <el-button @click="matching(scope.row)">确定</el-button>
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
      pageSize: 5,
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
        this.orders = res.data.orders
      })
    },
    getChunks() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-chunks').then(res => {
        this.chunks = res.data.chunks.filter(chunk=>chunk.status===0)
      })
    },
    getStaffs() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-staffs').then(res => {
        this.staffs = res.data.staffs.filter(staff=>staff.status===0)
      })
    },
    matching(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/matching', {orderId: row.id, chunkId: row.chunk, staffId: row.staff})

    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/delete-order', {orderId: row.id})
    }
  }
}
</script>

<style scoped>
</style>