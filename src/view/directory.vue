<template>
  <el-container class="nameness">
  <el-main>
  <el-table :data="tableData" height="250" style="width: 100%">
    <el-table-column prop="owner_id" label="Name" width="180" />
    <el-table-column prop="province" label="Province" width="180" />
    <el-table-column prop="city" label="City" />
    <el-table-column prop="distract" label="Distract" />
    <el-table-column prop="telephone" label="Telephone" />
    <el-table-column prop="address" label="Address" />
    <el-table-column prop="create_at" label="created_time" />
    <el-table-column prop="operations" label="operations"><el-button></el-button></el-table-column>
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
        tableData: [],
      }
    },
    created() {
      this.$axios.get('https://mc.rainspace.cn:4443/add-directory').then(res => {
        this.tableData = res.data.tableData;
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