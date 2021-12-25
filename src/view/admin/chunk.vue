<template>
  <el-container>
    <el-container>
      <el-main>
        <div style="height: 96%">
        <el-table :data="chunks.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%;height:100%">
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
        </div>
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
      <el-pagination :current-page="currentPage" :page-size="pageSize" :total="chunks.length" background
                     layout="prev, pager, next, jumper" style="width: 40%;float: left" @current-change="currentChange">
      </el-pagination>
      <el-button size="mini" style="width:20%;float:right" @click="reset();this.dialogVisible=true">
        <el-icon size="14">
          <circle-plus/>
        </el-icon>
        新建
      </el-button>
    </el-footer>
  </el-container>
</template>
<script>

export default {
  data() {
    return {
      search: null,
      pageSize: 7,
      currentPage: 1,
      dialogVisible: '',
      chunks: [],
      allChunks: [],
      chunk: {
        id: null,
        model: '',
        number: '',
        status: null,
      },
    }
  },
  watch: {
    search() {
      this.chunks.length = 0;
      this.chunks = this.allChunks.filter(
          (data) =>
              !this.search || data.model.toLowerCase().includes(this.search.toLowerCase())
              || data.number.toLowerCase().includes(this.search.toLowerCase())
      )
    }
  },
  created() {
    this.getChunks()
    this.dialogVisible = false
  },
  mounted() {
    document.title = "车辆管理"
  },
  methods: {
    currentChange(index) {
      this.currentPage = index
    },
    getChunks() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-chunks').then(res => {
        this.allChunks = res.data.chunks
        this.search = '';
      })
    },
    reset: function () {
      for (const key in this.chunk) {
        this.chunk[key] = null;
      }
    },
    addChunk: function () {
      if (this.chunk.id == null) {
        this.$axios.post('https://mc.rainspace.cn:4443/admin/add-chunk', this.chunk).then(() => {
          this.allChunks.length = 0
          this.getChunks()
        })

      } else {
        this.$axios.post('https://mc.rainspace.cn:4443/admin/edit-chunk', this.chunk).then(() => {
          this.allChunks.length = 0
          this.getChunks()
        })
      }
      this.dialogVisible = false;
      this.$emit('confirm');
      this.search = null;
    },
    edit(row) {
      this.chunk.model = row.model;
      this.chunk.number = row.number;
      this.chunk.id = row.id;
      this.dialogVisible = true;
    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/delete-chunk', {id: row.id}).then(()=>{
        this.chunks.length = 0
        this.getChunks()
      })
    }
  },
}
</script>

<style scoped>
</style>