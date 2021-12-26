<template>
  <el-container style="margin: 0 0 0 0">
    <el-container class="nameness">
      <el-main>
        <div style="padding:10px 0 0 0">
          <el-icon style="font-size: 20px;margin-right: 1em">
            <Search/>
          </el-icon>
          <el-input v-model="search" placeholder="输入关键字搜索" style="width: 95%" type="text"></el-input>
        </div>
        <br/>
        <div style="height: 79%">
          <el-table :data="users.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                    height="100%" id="table1">
            <el-table-column prop="id" label="Id"/>
            <el-table-column prop="username" label="用户名"/>
            <el-table-column prop="email" label="Email"/>
            <el-table-column prop="groupName" label="用户组"/>
            <el-table-column prop="operations">
              <template #header>
                <el-button style="width: 50%;float: right;margin-right: 25%" @click="addUser">
                  新增
                </el-button>
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
      <div style="padding-left: 35%">
        <el-pagination :current-page="currentPage" :page-size="pageSize" :total="users.length" background
                       layout="prev, pager, next, jumper" style="width: 40%;float: left"
                       @current-change="currentChange">
        </el-pagination>
      </div>
    </el-footer>
  </el-container>
</template>

<script>
import dialog3 from '/src/components/dialog3'
import {Search} from "@element-plus/icons";

export default {
  components: {
    dialog3,
    Search
  },
  data() {
    return {
      currentPage: 1,
      pageSize: 7,
      users: [],
      allUsers: [],
      search: null,
    }
  },
  watch: {
    search() {
      this.filter()
    }
  },
  mounted() {
    document.title = "用户管理"
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
        const currentPage = this.currentPage
        this.allUsers.length = 0
        for (const user of res.data.users) {
          if (user.groupId === 0) {
            user.groupName = '用户'
          } else {
            user.groupName = '管理员'
          }
          this.allUsers.push(user)
        }
        this.filter()
        this.currentPage = currentPage
      })
      this.users.filter(
          (data) => !this.search || data.email.toLowerCase().includes(this.search.toLowerCase())
              || data.telephone.toLowerCase().includes(this.search.toLowerCase()))
    },
    filter() {
      this.users.length = 0;
      this.users = this.allUsers.filter(
          (data) =>
              !this.search || data.username.toLowerCase().includes(this.search.toLowerCase())
              || data.email.toLowerCase().includes(this.search.toLowerCase())
      )
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
      this.$axios.post('https://mc.rainspace.cn:4443/admin/delete-user', {id: row.id}).then(() => {
        this.getUsers()
      })
    }
  }
}
</script>

<style scoped>
.nameness {
  margin: 0;
  display: flex;
  vertical-align: center;
}

#table1 {
  width: 100%;
  border: #E5E5E5 2px solid;
  border-radius: 15px;
}
</style>