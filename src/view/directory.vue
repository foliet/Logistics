<template>
  <el-container class="nameness">
  <el-main>
  <el-table :data="tableData" height="250" style="width: 100%">
    <el-table-column prop="owner_id" label="Name" width="180"/>
    <el-table-column label="province/city/distract" prop="PCD" width="180"/>
    <el-table-column prop="telephone" label="Telephone"/>
    <el-table-column prop="address" label="Address"/>
    <el-table-column prop="create_at" label="created_time"/>
    <el-table-column label="operations" prop="operations">
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
      tableData: [{
        owner_id: 1,
        PCD: 'sbhisx',
        telephone: 11111111111,
        address: 111,
        create_at: "2021-12-10",
      }],
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