<template>
  <el-container>
    <el-container class="nameness">
      <el-header>
        <div style="padding:10px 0 0 0">
          <el-input v-model="search" placeholder="输入关键字" style="width: 100%" type="text"></el-input>
        </div>
      </el-header>
    <el-main >
    <el-table :data="contacts.slice((currentPage-1)*pageSize,currentPage*pageSize)"
              height="100%" id="table1">
      <el-table-column label="姓名" prop="receiverName" sortable width="190"/>
      <el-table-column label="省份/城市/地区" prop="PCD" sortable width="300"/>
      <el-table-column label="电话" prop="telephone" sortable width="250"/>
      <el-table-column label="地址" prop="address" sortable/>
      <el-table-column prop="operations">
        <template #header>
          <el-button @click="add" class="addPeople" size="medium" round>
            <el-icon size="17" class="addPeople1"><circle-plus/></el-icon>
            <span class="addPeople1">添加联系人</span>
          </el-button>
        </template>
        <template #default="scope">
          <el-button @click="edit(scope.row)">修改</el-button>
          |
          <el-button @click="deleted(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <dia ref="c" @confirm="confirm"></dia>
  </el-main>
    </el-container>
  <el-footer>
    <div style="text-align: center">
    <el-pagination :current-page="currentPage" @current-change="currentChange"
       background layout="prev, pager, next, jumper" :total="contacts.length" :page-size="pageSize">
    </el-pagination>
    </div>
  </el-footer>
  </el-container>
</template>

<script>

import {CirclePlus} from "@element-plus/icons";
import dia from '../components/dia'

export default {
  data() {
    return {
      pageSize: 10,
      currentPage: 1,
      search: null,
      contacts: [],
      allContacts:[],
    }
  },
  watch:{
    search(){
      this.contacts=this.allContacts.filter(
          (data) =>
              !this.search || data.receiverName.toLowerCase().includes(this.search.toLowerCase())
              || data.address.toLowerCase().includes(this.search.toLowerCase())
              || data.PCD.toLowerCase().includes(this.search.toLowerCase())
              || data.telephone.toLowerCase().includes(this.search.toLowerCase())
      )
    }
  },
  created() {
    this.getContacts()
  },
  components:
      {
        CirclePlus,
        dia
      },
  methods: {
    currentChange(index){
      this.currentPage=index
    },
    getContacts(){
      this.$axios.get('https://mc.rainspace.cn:4443/get-contacts?type=others').then(res => {
        if(res.data.status<10){
          for(const contact of res.data.contacts){
            contact.PCD=contact.province+contact.city+contact.district
            this.allContacts.push(contact)
          }
          this.search=null
          this.search=''
        }else{
          this.$message.error(res.data.msg)
        }
      })
    },
    add() {
      this.$refs.c.reset()
      this.$refs.c.dialogVisible = true;
    },
    edit(row) {
      this.$refs.c.tableData.receiverName = row.receiverName;
      this.$refs.c.tableData.telephone = row.telephone;
      this.$refs.c.tableData.address = row.address;
      this.$refs.c.tableData.PCD[0] = row.province;
      this.$refs.c.tableData.PCD[1] = row.city;
      this.$refs.c.tableData.PCD[2] = row.district;
      this.$refs.c.tableData.id = row.id;
      this.$refs.c.dialogVisible = true;
    },
    confirm() {
      setTimeout(() => {
        this.contacts.length = 0
        this.getContacts()
      }, 500)
    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/delete-contact', {id: row.id})
      setTimeout(() => {
        this.contacts.length = 0
        this.getContacts()
      }, 500)
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
#table1{
  width: 100%;
  border: #E5E5E5 2px solid;
  border-radius: 15px;
}
.addPeople{
  float: right;
  border: #FF8200 1px solid;
  background-color: white;
}
.addPeople1{
  color: #FF8200;
}
.addPeople:focus , .addPeople:hover{
  background-image: linear-gradient(to right,#ff9000 0,#ff5000 100%);
  border: #FF8200;
}
.addPeople:focus .addPeople1{
  color: white;
  font-weight: bolder;
}
.addPeople:hover .addPeople1{
  color: white;
  font-weight: bolder;
}
</style>