<template>
  <el-container>
    <el-container class="nameness">
      <el-header>
        <div style="padding:10px 0 0 0">
          <el-icon style="font-size: 20px;margin-right: 1em">
            <Search/>
          </el-icon>
          <el-input v-model="search" placeholder="输入关键字搜索" style="width: 95%" type="text"></el-input>
        </div>
      </el-header>
      <el-main>
        <el-table :data="contacts.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                  height="100%" id="table1">
          <el-table-column label="姓名" prop="receiverName" sortable width="190"/>
          <el-table-column label="省份/城市/地区" prop="PCD" sortable width="300"/>
          <el-table-column label="电话" prop="telephone" sortable width="250"/>
          <el-table-column label="地址" prop="address" sortable/>
          <el-table-column prop="operations">
            <template #header>
              <el-button @click="add" class="addPeople" size="medium" round>
                <el-icon size="17" class="addPeople1">
                  <circle-plus/>
                </el-icon>
                <span class="addPeople1">添加联系人</span>
              </el-button>
            </template>
            <template #default="scope">
              <el-button @click="edit(scope.row)">修改</el-button>
              |
              <el-button @click="scope.row.visible=true">删除</el-button>
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
        <dia type='others' ref="c" @confirm="confirm"></dia>
      </el-main>
    </el-container>
    <el-footer>
      <div style="text-align: center">
        <a>选择每页展示数：</a>
        <el-select v-model="pageSize" size="mini" style="width: 10%;">
          <el-option
              v-for="typer in typeses"
              :key="typer"
              :value="typer.value">
            {{ typer.value }}
          </el-option>
        </el-select>
        <el-pagination :current-page="currentPage" style="float: left" @current-change="currentChange"
                       background layout="prev, pager, next, jumper" :total="contacts.length" :page-size="pageSize">
        </el-pagination>
      </div>
    </el-footer>
  </el-container>
</template>

<script>

import {CirclePlus, Search} from "@element-plus/icons";
import dia from '../components/dia'

export default {

  data() {
    return {
      pageSize: 6,
      currentPage: 1,
      search: null,
      contacts: [],
      allContacts: [],
    }
  },
  watch: {
    search() {
      this.filter()
    }
  },
  mounted() {
    document.title = "联系地址"
  },
  created() {
    this.getContacts()
  },
  components:
      {
        CirclePlus,
        dia,
        Search
      },
  methods: {

    currentChange(index) {
      this.currentPage = index
    },
    getContacts() {
      this.$axios.get('https://mc.rainspace.cn:4443/get-contacts?type=others').then(res => {
        if (res.data.status < 10) {
          const currentPage = this.currentPage
          this.allContacts.length = 0
          for (const contact of res.data.contacts) {
            contact.visible=false
            contact.PCD = contact.province + contact.city + contact.district
            this.allContacts.push(contact)
          }
          this.filter()
          this.currentPage = currentPage
        } else {
          this.$message.error(res.data.msg)
        }
      })
    },
    filter(){
      this.contacts = this.allContacts.filter(
          (data) =>
              !this.search || data.receiverName.toLowerCase().includes(this.search.toLowerCase())
              || data.address.toLowerCase().includes(this.search.toLowerCase())
              || data.PCD.toLowerCase().includes(this.search.toLowerCase())
              || data.telephone.toLowerCase().includes(this.search.toLowerCase())
      )
    },
    add() {
      this.$refs.c.reset()
      this.$refs.c.dialogVisible = true;
    },
    edit(row) {
      this.$refs.c.dialogVisible = true;
      this.$refs.c.reset();
      this.$refs.c.tableData.receiverName = row.receiverName;
      this.$refs.c.tableData.telephone = row.telephone;
      this.$refs.c.tableData.address = row.address;
      this.$refs.c.tableData.PCD[0] = row.province;
      this.$refs.c.tableData.PCD[1] = row.city;
      this.$refs.c.tableData.PCD[2] = row.district;
      this.$refs.c.tableData.id = row.id;

    },
    confirm() {
      setTimeout(() => {
        this.allContacts.length = 0
        this.getContacts()
      }, 500)
      this.search = null;
    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/delete-contact', {id: row.id})
      setTimeout(() => {
        this.allContacts.length = 0
        this.getContacts()
      }, 500)
      this.search = null;
    }
  },
}

</script>

<style scoped>
.nameness {
  margin: 0;
  display: flex;
  vertical-align: center;
}

#table1 {
  width: 100%;
  border: #E5E5E5 2px solid;
  border-radius: 15px;
}

.addPeople {
  float: right;
  border: #FF8200 1px solid;
  background-color: white;
}

.addPeople1 {
  color: #FF8200;
}

.addPeople:focus, .addPeople:hover {
  background-image: linear-gradient(to right, #ff9000 0, #ff5000 100%);
  border: #FF8200;
}

.addPeople:focus .addPeople1 {
  color: white;
  font-weight: bolder;
}

.addPeople:hover .addPeople1 {
  color: white;
  font-weight: bolder;
}
</style>