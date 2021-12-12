<template>
  <el-container class="nameness">
  <el-main>
    <el-table :data="
      tableData.filter(
        (data) =>
          !search || data.owner_id.toLowerCase().includes(search.toLowerCase())
          || data.PCD.toLowerCase().includes(search.toLowerCase())
          || data.telephone.toLowerCase().includes(search.toLowerCase())
          || data.address.toLowerCase().includes(search.toLowerCase()))" height="250"
              style="width: 100%">
      <el-table-column label="Name" prop="owner_id" sortable width="180"/>
      <el-table-column label="province/city/distract" prop="PCD" sortable width="180"/>
      <el-table-column label="Telephone" prop="telephone" sortable/>
      <el-table-column label="Address" prop="address" sortable/>
      <el-table-column label="created_time" prop="create_at" sortable/>
      <el-table-column prop="operations">
        <template #header>
          <el-input v-model="search" placeholder="Type to search" size="mini"/>
        </template>
        <template #default="scope">
          <el-button @click="edit(scope.$index,scope.row)">修改</el-button>
          |
          <el-button @click="console.log(scope.$index,scope.row)">删除</el-button>
        </template>
        <!--      deleted(scope.id)-->
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
      tableData: [
        {
          owner_id: '1',
          PCD: 'sbhisx',
          telephone: '11111111111',
          address: '111',
          create_at: "2021-12-10",
        },
        {
          owner_id: '2',
          PCD: 'sbcdcc',
          telephone: '12222222222',
          address: '10101',
          create_at: "2021-12-12",
        }
      ],
      search: '',
    }
    },
    created() {
      this.$axios.get('https://mc.rainspace.cn:4443/add-directory').then(() => {
        //this.tableData = res.data.tableData;
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
        this.$refs.c.tableData.owner_id = row.owner_id;
        this.$refs.c.tableData.telephone = row.telephone;
        this.$refs.c.tableData.address = row.address;
        this.$refs.c.rowid = id;
        this.$refs.c.dialogVisible = true;
      },
      deleted(id) {
        this.$axios.post('https://mc.rainspace.cn:4443/delete-directory', id)
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