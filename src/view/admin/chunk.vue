<template>
  <el-container>
    <el-container>
      <el-main>
        <el-table :data="chunks.filter(
            (data) =>!search || data.number.toLowerCase().includes(search.toLowerCase())
    || data.model.toLowerCase().includes(search.toLowerCase()))" style="width: 100%;height:100%">
          <el-table-column prop="id" label="Id"/>
          <el-table-column prop="number" label="车牌号"/>
          <el-table-column prop="model" label="型号"/>
          <el-table-column prop="status" label="状态"/>
          <el-table-column prop="operations">
            <template #header>
              <el-input v-model="search" placeholder="Type to search" size="mini"/>
            </template>
            <template #default="scope">
              <el-button round style="color: #FFB500; border: 1px #FFB500 solid" @click="edit(scope.row)">
                修改
              </el-button>
              |
              <el-button round style="color: #FF3D00;border: 1px #FF3D00 solid" @click="deleted(scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
    <el-dialog v-model="dialogVisible"
               title="编辑车辆"
               top="5vh" width="40%">
      <div class="space1">车牌号</div>
      <div>
        <el-input v-model="chunk.number" clearable placeholder="车牌号" type="text"/>
      </div>
      <div class="space1">型号</div>
      <div>
        <el-input v-model="chunk.model" clearable placeholder="型号" type="text"/>
      </div>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button @click="reset()">Reset</el-button>
        <el-button type="primary" @click="addChunk()"
        >Confirm</el-button>
      </span>
      </template>
    </el-dialog>
    <el-footer>
      <el-button style="width: 100%" @click="this.dialogVisible=true">新增</el-button>
    </el-footer>
  </el-container>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      search: '',
      dialogVisible: '',
      chunks: [],
      chunk: {
        id: null,
        model: '',
        number: '',
      },
    }
  },
  created() {
    this.getChunks()
    this.dialogVisible = false
  },
  mounted() {
    document.title="车辆管理"
  },
  methods: {
    getChunks() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-chunks').then(res => {
        this.chunks = res.data.chunks
      })
    },
    reset: function () {
      for (const key in this.chunk) {
        this.chunk[key] = null;
      }
    },
    addChunk: function () {
      if (this.chunk.uid == null) {
        axios.post('https://mc.rainspace.cn:4443/add-chunk', this.chunk).then(res => {
          if (res.data.status >= 10) {
            this.$message.error(res.data.msg)
          }
        })

      } else {
        axios.post('https://mc.rainspace.cn:4443/edit-chunk', this.chunk).then(res => {
          if (res.data.status >= 10) {
            this.$message.error(res.data.msg)
          }
        })
      }
      this.uid = null;
      this.dialogVisible = false;
      this.$emit('confirm')
    },
    edit(row) {
      this.chunk.model = row.model;
      this.chunk.number = row.number;
      this.chunk.uid = row.id;
      this.dialogVisible = true;
    },
    deleted(row) {
      axios.post('https://mc.rainspace.cn:4443/delete-chunk', {id: row.id})
      setTimeout(() => {
        this.chunks.length = 0
        this.getChunks()
      }, 500)
    }
  },
}
</script>

<style scoped>
</style>