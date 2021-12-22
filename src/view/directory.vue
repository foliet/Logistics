<template>
  <el-container class="nameness">
  <el-main>
    <el-table :data="contacts.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="100%" style="width: 100%">
      <el-table-column label="用户名" prop="receiverName" sortable width="180"/>
      <el-table-column label="省市区" prop="PCD" sortable width="180"/>
      <el-table-column label="电话" prop="telephone" sortable/>
      <el-table-column label="地址" prop="address" sortable/>
      <el-table-column prop="operations">
        <template #header>
          <el-input v-model="search" placeholder="Type to search" size="mini"/>
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
    <el-footer>
      <el-pagination :current-page="currentPage" :page-size="pageSize" :total="contacts.length" background
                     layout="prev, pager, next, jumper" style="width: 40%;float: left" @current-change="currentChange">
      </el-pagination>
      <el-button size="mini" style="width:20%;float:right" @click="add">
        <el-icon size="14">
          <circle-plus/>
        </el-icon>
        新建联系人
      </el-button>
    </el-footer>
  </el-container>
</template>

<script>

import {CirclePlus} from "@element-plus/icons";
import dia from '../components/dia'

export default {
  data() {
    return {
      pageSize: 7,
      currentPage: 1,
      search: null,
      contacts: [],
      allContacts: [],
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
.nameless{
  padding:0;

}
.nameness{
  margin: 0;
  display: flex;
  vertical-align: center;
  border-width: 3px;
  color:black;
}
</style>