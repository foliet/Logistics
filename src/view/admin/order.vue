<template>
  <el-container>
    <el-container>
      <el-main>
        <el-table :data="orders" style="width: 100%;height:100%">
          <el-table-column label="Id" prop="id"/>
          <el-table-column label="司机Id" prop="driverId"/>
          <el-table-column label="货车Id" prop="chunkId"/>
          <el-table-column label="状态" prop="status"/>
          <el-table-column label="分配车辆">
            <template #default="scope">
              <div>
                <el-select v-model="scope.row.chunk" :disabled="!scope.row.status" placeholder="Select chunk">
                  <el-option
                      v-for="item in chunks"
                      :key="item.status"
                      :disabled="item.status"
                      :label="item.model"
                      :value="item.id"
                  >
                    {{ item.model }}
                  </el-option>
                </el-select>
                <el-select v-model="scope.row.staff" :disabled="!scope.row.status" placeholder="Select staff">
                  <el-option
                      v-for="item in staffs"
                      :key="item.status"
                      :disabled="item.status"
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
        <div class="demo-pagination-block">
          <span class="demonstration">Jump to</span>
          <el-pagination
              :page-size="100"
              :total="1000"
              layout="prev, pager, next, jumper"
          >
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
      orders: [],
      chunks: [],
      staffs: [],
      chunk: '',
      staff: '',
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
    getOrders() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-orders').then(res => {
        this.orders = res.data.orders//将这个用户的数据库的所有orders都push到cards，一个orderData为一个元素
      })
    },
    getChunks() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-chunks').then(res => {
        this.chunks = res.data.chunks
      })
    },
    getStaffs() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-staffs').then(res => {
        this.staffs = res.data.staffs
      })
    },
    matching(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/matching', {id1: row.id, id2: row.chunk, id3: row.staff})

    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/delete', {id1: row.id})
    }
  }
}
</script>

<style scoped>
</style>