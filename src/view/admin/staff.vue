<template>
  <el-container>
    <el-container>
      <el-main>
        <el-table :data="staffs.filter(
            (data) =>!search || data.name.toLowerCase().includes(search.toLowerCase())
    || data.gender.toLowerCase().includes(search.toLowerCase()))" style="width: 100%;height:100%">
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
      <el-button style="width: 100%" @click="reset();this.dialogVisible=true">新增</el-button>
    </el-footer>
  </el-container>
</template>
<script>

export default {
  data() {
    return {
      staffs: [],
      search: '',
      staff: {
        id: null,
        gender: '',
        name: '',
        status: null,
      },
      dialogVisible: '',
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
    getStaffs() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-staffs').then(res => {
        this.staffs = res.data.staffs
      })
    },
    reset: function () {
      for (const key in this.staff) {
        this.staff[key] = null;
      }
    },
    addStaff: function () {
      if (this.staff.id == null) {
        this.$axios.post('https://mc.rainspace.cn:4443/admin/add-staff', this.staff).then(() => {
          this.staffs.length = 0
          this.getStaffs()
        })

      } else {
        this.$axios.post('https://mc.rainspace.cn:4443/admin/edit-staff', this.staff).then(() => {
          this.staffs.length = 0
          this.getStaffs()
        })
      }
      this.dialogVisible = false;
      this.$emit('confirm')
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