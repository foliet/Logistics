<template>
  <el-container>
    <el-container>
      <el-main>
        <div style="padding:10px 0 0 0">
          <el-icon style="font-size: 20px;margin-right: 1em">
            <Search/>
          </el-icon>
          <el-input v-model="search" placeholder="输入关键字搜索" style="width: 95%" type="text"></el-input>
        </div>
        <br/>
        <div style="height: 79%">
          <el-table :data="chunks.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="100%" id="table1">
            <el-table-column sortable prop="id" label="Id"/>
            <el-table-column sortable prop="number" label="车牌号"/>
            <el-table-column sortable prop="model" label="型号"/>
            <el-table-column sortable prop="statusName" label="状态"/>
            <el-table-column>
              <template #header>
                <el-button style="width: 50%;float: right;margin-right: 25%" @click="reset();this.dialogVisible=true">
                  新增
                </el-button>
              </template>
              <template #default="scope">
                <el-button round style="color: #FFB500; border: 1px #FFB500 solid" @click="edit(scope.row)">
                  修改
                </el-button>
                |
                <el-button :class="choose(check(scope.row.statusName))" :disabled="check(scope.row.statusName)" round
                           @click="scope.row.visible = true">删除
                </el-button>
                <el-dialog v-model="scope.row.visible">确定要删除吗？
                  <template #footer>
      <span class="dialog-footer">
        <el-button @click="scope.row.visible = false">取消</el-button>
        <el-button type="primary" @click="deleted(scope.row)"
        >确认</el-button>
      </span>
                  </template>
                </el-dialog>
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
        <el-button type="primary" @click="addChunk()"
        >Confirm</el-button>
      </span>
      </template>
    </el-dialog>
    <el-footer>
      <div style="padding-left: 35%">
        <el-pagination :current-page="currentPage" :page-size="pageSize" :total="chunks.length" background
                       layout="prev, pager, next, jumper" style="width: 40%;float: left"
                       @current-change="currentChange">
        </el-pagination>
        <a>选择每页展示数：</a>
        <el-select v-model="pageSize" size="mini" style="width: 10%;">
          <el-option
              v-for="typer in typeses"
              :key="typer"
              :value="typer.value">
            {{ typer.value }}
          </el-option>
        </el-select>
      </div>
    </el-footer>
  </el-container>
</template>
<script>

import {Search} from "@element-plus/icons";

export default {
  components: {
    Search
  },
  data() {
    return {
      search: null,
      pageSize: 5,
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
      typeses: [{value: 1,},
        {value: 2,},
        {value: 3,},
        {value: 4,},
        {value: 5,},
        {value: 6,},
        {value: 7,},
        {value: 8,},
        {value: 9,},
        {value: 10,},
        {value: 11,},
        {value: 12,},
        {value: 13,},
      ]
    }
  },
  watch: {
    search() {
      this.filter()
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
    check(param) {
      return param !== '空闲中';
    },
    choose(param) {
      if (param == true) return 'disable'
      else return 'able'
    },
    getChunks() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-chunks').then(res => {
        const currentPage = this.currentPage
        this.allChunks.length = 0
        for (const chunk of res.data.chunks) {
          chunk.visible = false
          if (chunk.status === 0) {
            chunk.statusName = '空闲中'
          } else {
            chunk.statusName = '运输中'
          }
          this.allChunks.push(chunk)
        }
        this.filter()
        this.currentPage = currentPage
      })
    },
    filter(){
      this.chunks.length = 0;
      this.chunks = this.allChunks.filter(
          (data) =>
              !this.search || data.model.toLowerCase().includes(this.search.toLowerCase())
              || data.number.toLowerCase().includes(this.search.toLowerCase())
      )
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
      this.chunk.status = row.status
      this.chunk.model = row.model;
      this.chunk.number = row.number;
      this.chunk.id = row.id;
      this.dialogVisible = true;
    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/delete-chunk', {id: row.id}).then(() => {
        this.getChunks()
      })
    }
  },
}
</script>

<style scoped>
#table1 {
  width: 100%;
  border: #E5E5E5 2px solid;
  border-radius: 15px;
}

.able {
  color: #FF3D00;
  border: 1px #FF3D00 solid
}

.disable {
  color: gray;
  border: 1px gray solid
}
</style>