<template>
  <el-header>
    <el-button @click="addUser">新增</el-button>
  </el-header>
  <el-table :data="users.filter(
        (data) =>
          !search || data.email.toLowerCase().includes(search.toLowerCase())
          || data.telephone.toLowerCase().includes(search.toLowerCase())
          )" style="width: 100%">
    <el-table-column prop="id" label="Id"/>
    <el-table-column prop="username" label="用户名"/>
    <el-table-column prop="email" label="Email"/>
    <el-table-column prop="groupId" label="用户组"/>
    <el-table-column prop="operations">
      <template #header>
        <el-input v-model="search" placeholder="Type to search" size="mini"/>
      </template>
      <template #default="scope">
        <el-button @click="edit(scope.row)">修改</el-button>
        |
        <el-button @click="deleted(scope.row)">删除</el-button>
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
      users: [],
      search: '',
    }
  },
  created() {
    this.$axios.get('https://mc.rainspace.cn:4443/admin/get-user').then(res => {
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
    edit(row) {
      this.$refs.d.tableData.username = row.username;
      this.$refs.d.tableData.ownerId = row.ownerId;
      this.$refs.d.tableData.email = row.email;
      this.$refs.d.tableData.password = row.password;
      this.$refs.d.userId = row.userId;
      this.$refs.d.dialogVisible = true;
    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/delete-user', row.userId)
    }
  }
}
</script>

<style scoped>

</style>