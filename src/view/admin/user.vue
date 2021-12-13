<template>
  <el-header>
    <el-button @click="addUser">新增</el-button>
  </el-header>
  <el-table :data="users" style="width: 100%">
    <el-table-column prop="id" label="Id"/>
    <el-table-column prop="username" label="用户名"/>
    <el-table-column prop="email" label="Email"/>
    <el-table-column prop="groupId" label="用户组"/>
    <el-table-column prop="operations">
      <template #header>
        <el-input v-model="search" placeholder="Type to search" size="mini"/>
      </template>
      <template #default="scope">
        <el-button @click="edit(scope.$index,scope.row)">修改</el-button>
        |
        <el-button @click="deleted(scope.id)">删除</el-button>
      </template>
      <!-- console.log(scope.$index,scope.row)    -->
    </el-table-column>
  </el-table>
  <dialog3 ref="d"></dialog3>
</template>

<script>
import dialog3 from '/src/components/dialog3'

export default {
  components: {
    dialog3
  },
  data() {
    return {
      users: []
    }
  },
  created() {
    this.$axios.get('https://mc.rainspace.cn:4443/admin/get-users').then(res => {
      if (res.data.status < 10) {
        this.users = res.data.users
      } else {
        this.$message.error(res.data.msg)
      }
    })
  },
  methods: {
    addUser() {
      this.$refs.d.dialogVisible = true;
    },
  }
}
</script>

<style scoped>

</style>