<template>
  <el-header style="text-align: right; font-size: 12px">
    <el-dropdown>
      <el-icon style="margin-right: 15px"><Setting /></el-icon>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>View</el-dropdown-item>
          <el-dropdown-item>Add</el-dropdown-item>
          <el-dropdown-item>Delete</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </el-header>

  <el-main>
    <el-table :data="tableData">
      <el-table-column prop="date" label="Date" width="140">
      </el-table-column>
      <el-table-column prop="name" label="Name" width="120">
      </el-table-column>
      <el-table-column prop="address" label="Address"> </el-table-column>
    </el-table>
  </el-main>
</template>

<script>
import {Setting} from "@element-plus/icons";

export default {
  title: "user",
  data() {
    return{
      tableData:Array(20).fill({
        date: '2016-05-02',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
      })
    }
  },
  components:{
    Setting
  }
}
</script>

<style scoped>

</style>