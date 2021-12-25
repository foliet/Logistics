<template>
  <el-container>
    <el-container>
      <el-main>
        <div style="height: 95%">
          <el-table :data="staffs.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%;height:100%">
            <el-table-column prop="id" label="Id"/>
            <el-table-column prop="name" label="姓名"/>
            <el-table-column prop="gender" label="性别"/>
            <el-table-column prop="status" label="状态"/>
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
          </el-table-column>
        </el-table>
        </div>
      </el-main>
    </el-container>
    <el-dialog v-model="dialogVisible"
               title="编辑员工"
               top="5vh" width="40%">
      <div class="space1">姓名</div>
      <div>
        <el-input v-model="staff.name" clearable placeholder="name" type="text"/>
      </div>
      <div class="space1">性别</div>
      <div>
        <el-input v-model="staff.gender" clearable placeholder="gender" type="text"/>
      </div>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button @click="reset()">Reset</el-button>
        <el-button type="primary" @click="addStaff()"
        >Confirm</el-button>
      </span>
      </template>
    </el-dialog>
    <el-footer>
      <el-footer>
        <el-pagination :current-page="currentPage" :page-size="pageSize" :total="staffs.length" background
                       layout="prev, pager, next, jumper" style="width: 40%;float: left"
                       @current-change="currentChange">
        </el-pagination>
        <el-button size="mini" style="width:20%;float:right" @click="reset();this.dialogVisible=true">
          <el-icon size="14">
            <circle-plus/>
          </el-icon>
          新建
        </el-button>
      </el-footer>
    </el-footer>
  </el-container>
</template>
<script>

export default {
  data() {
    return {
      search: null,
      pageSize: 7,
      currentPage: 1,
      staffs: [],
      allStaffs: [],
      staff: {
        id: null,
        gender: '',
        name: '',
        status: null,
      },
      dialogVisible: '',
    }
  },
  watch: {
    search() {
      this.staffs.length = 0;
      this.staffs = this.allStaffs.filter(
          (data) =>
              !this.search || data.name.toLowerCase().includes(this.search.toLowerCase())
              || data.gender.toLowerCase().includes(this.search.toLowerCase())
      )
    }
  },
  created() {
    this.getStaffs()
    this.dialogVisible = false
  },
  mounted() {
    document.title = "员工管理"
  },
  methods: {
    currentChange(index) {
      this.currentPage = index
    },
    getStaffs() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-staffs').then(res => {
        this.allStaffs = res.data.staffs
        this.search = null
        this.search = ''
      })

    },
    reset() {
      for (const key in this.staff) {
        this.staff[key] = null;
      }
    },
    addStaff() {
      if (this.staff.id == null) {
        this.$axios.post('https://mc.rainspace.cn:4443/admin/add-staff', this.staff).then(() => {
          this.allStaffs.length = 0
          this.getStaffs()
        })

      } else {
        this.$axios.post('https://mc.rainspace.cn:4443/admin/edit-staff', this.staff).then(() => {
          this.allStaffs.length = 0
          this.getStaffs()
        })
      }
      this.dialogVisible = false;
      this.$emit('confirm');
      this.search = null;
    },
    edit(row) {
      this.staff.gender = row.gender;
      this.staff.name = row.name;
      this.staff.id = row.id;
      this.dialogVisible = true;
    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/delete-staff', {id: row.id}).then(()=>{
        this.staffs.length = 0
        this.getStaffs()
      })
    }
  }
}
</script>

<style scoped>
</style>