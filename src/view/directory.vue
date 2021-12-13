<template>
  <el-container class="nameness">
  <el-main>
    <el-table :data="contacts.filter(
        (data) =>
          !search || data.receiverName.toLowerCase().includes(search.toLowerCase())
          || data.address.toLowerCase().includes(search.toLowerCase())
          || data.PCD.toLowerCase().includes(search.toLowerCase())
          || data.telephone.toLowerCase().includes(search.toLowerCase())
          )" height="550" style="width: 100%">
      <el-table-column label="Name" prop="receiverName" sortable width="180"/>
      <el-table-column label="province/city/distract" prop="PCD" sortable width="180"/>
      <el-table-column label="Telephone" prop="telephone" sortable/>
      <el-table-column label="Address" prop="address" sortable/>
      <el-table-column prop="operations">
        <template #header>
          <el-input v-model="search" placeholder="Type to search" size="mini"/>
        </template>
        <template #default="scope">
          <el-button @click="edit(scope.row)">修改</el-button>
          |
          <el-button @click="deleted(scope.row)">删除</el-button>
        </template>
        <!--      -->
      </el-table-column>
    </el-table>
    <dia ref="c"></dia>
  </el-main>
  <el-footer height="40px" class="nameless" @click="showDialog">
    <div class="align">
      <el-icon size="23"><circle-plus/></el-icon>
      添加联系人
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
      search: '',
      contacts: [],
    }
  },
  created() {
    this.$axios.get('https://mc.rainspace.cn:4443/get-contacts?type=others').then(res => {
      if(res.data.status<10){
        for(const contact of res.data.contacts){
          contact.PCD=contact.province+contact.city+contact.district
          this.contacts.push(contact)
        }
      }else{
        this.$message.error(res.data.msg)
      }
    })
  },
  components:
      {
        CirclePlus,
        dia
      },
  methods: {
    showDialog() {
      this.$refs.c.dialogVisible = true;
    },
    edit(id, row) {
      this.$refs.c.tableData.receiverName = row.receiverName;
      this.$refs.c.tableData.telephone = row.telephone;
      this.$refs.c.tableData.address = row.address;
      this.$refs.c.rowid = id;
      this.$refs.c.dialogVisible = true;
    },
    deleted(id) {
      this.$axios.post('https://mc.rainspace.cn:4443/delete-contact', id)
    }
  },
}

</script>

<style scoped>
.align{
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: justify;
  border-style:solid solid solid none;
  border-width: 2px;
  color:black;
  height:37px;
}

.nameless{
  padding:0;

}
.nameness{
  margin: 0;
  display: flex;
  vertical-align: center;
  border-style:none none none solid;
  border-width: 3px;
  color:black;
}
</style>