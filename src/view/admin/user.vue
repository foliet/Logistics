<template>
  <el-container style="margin: 0 0 0 0">
    <el-container class="nameness">
      <el-main >
        <div style="height: 95%">
        <el-table :data="users.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                  height="100%" style="width: 100%;">
          <el-table-column prop="id" label="Id"/>
          <el-table-column prop="username" label="用户名"/>
          <el-table-column prop="email" label="Email"/>
          <el-table-column prop="groupId" label="用户组"/>
          <el-table-column prop="operations">
            <template #header>
              <el-input v-model="search" placeholder="Type to search" size="mini"/>
            </template>
            <template #default="scope">
              <el-button round style="color: #FFB500; border: 1px #FFB500 solid" @click="edit(scope.row)">
                修改
              </el-button>
              |
              <el-button round style="color: #FF3D00;border: 1px #FF3D00 solid" @click="deleted(scope.row)">删除
              </el-button>
            </template>
            <!-- console.log(scope.$index,scope.row)    -->
          </el-table-column>
        </el-table>
        </div>
        <dialog3 ref="d" @confirm="getUsers"></dialog3>
      </el-main>
    </el-container>
    <el-footer>
      <el-pagination :current-page="currentPage" :page-size="pageSize" :total="users.length" background
                     layout="prev, pager, next, jumper" style="width: 40%;float: left" @current-change="currentChange">
      </el-pagination>
      <el-button size="mini" style="width:20%;float:right"
                 @click="this.$refs.d.reset();this.$refs.d.dialogVisible=true">
        <el-icon size="14">
          <circle-plus/>
        </el-icon>
        新建
      </el-button>
    </el-footer>
  </el-container>
</template>

<script>
import dialog3 from '/src/components/dialog3'

export default {
  components: {
    dialog3
  },
  data() {
    return {
      search: null,
      pageSize: 7,
      currentPage: 1,
      users: [],
      allUsers: [],
    }
  },
  watch: {
    search() {
      this.users.length = 0;
      this.users = this.allUsers.filter(
          (data) =>
              !this.search || data.username.toLowerCase().includes(this.search.toLowerCase())
              || data.email.toLowerCase().includes(this.search.toLowerCase())
      )
    }
  },
  created() {
    this.getUsers()
  },
  methods: {
    currentChange(index) {
      this.currentPage = index
    },
    getUsers() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-users').then(res => {
        this.allUsers = res.data.users
        this.search = ''
      })
    },
    addUser() {
      this.$refs.d.reset()
      this.$refs.d.dialogVisible = true;
    },
    edit(row) {
      this.$refs.d.tableData.username = row.username;
      this.$refs.d.tableData.email = row.email;
      this.$refs.d.tableData.password = row.password;
      this.$refs.d.tableData.id = row.id;
      this.$refs.d.tableData.groupId = row.groupId;
      this.$refs.d.dialogVisible = true;
    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/delete-user', {id: row.id}).then(()=>{
        this.getUsers()
      })
    }
  }
}
</script>

<style scoped>
.nameness{
  margin: 0;
  display: flex;
  vertical-align: center;
}
</style>