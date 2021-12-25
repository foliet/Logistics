<template>
  <el-container>
    <el-container>
      <el-main>
        <div style="padding:10px 0 0 0">
          <el-icon style="font-size: 20px;margin-right: 1em"><Search /></el-icon>
          <el-input v-model="search" placeholder="输入关键字搜索" style="width: 95%" type="text"></el-input>
        </div>
        <br />
        <div style="height: 79%">
        <el-table :data="staffs.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="100%" id="table1">
          <el-table-column prop="id" label="Id"/>
          <el-table-column prop="name" label="姓名"/>
          <el-table-column prop="gender" label="性别"/>
          <el-table-column prop="statusName" label="状态"/>
          <el-table-column prop="operations">
            <template #header>
              <el-button style="width: 50%;float: right;margin-right: 25%" @click="reset();this.dialogVisible=true">
                新增</el-button>
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
      <div style="padding-left: 35%">
      <el-pagination :current-page="currentPage" :page-size="pageSize" :total="staffs.length" background
                     layout="prev, pager, next, jumper" style="width: 40%;float: left" @current-change="currentChange">
      </el-pagination>
      </div>
    </el-footer>
  </el-container>
</template>
<script>
import {
  Search
} from "@element-plus/icons";

export default {
  components: {
    Search
  },
  data() {
    return {
      currentPage:1,
      pageSize:4,
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
    currentChange(index) {
      this.currentPage = index
    },
    getStaffs() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-staffs').then(res => {
        this.allStaffs.length=0
        this.search = null
        this.search = ''
        for(const staff of res.data.staffs){
          if(staff.status===0){
            staff.statusName='空闲中'
          }else{
            staff.statusName='工作中'
          }
          this.allStaffs.push(staff)
        }
      })
      this.staffs.filter(
          (data) =>!this.search || data.name.toLowerCase().includes(this.search.toLowerCase())
              || data.gender.toLowerCase().includes(this.search.toLowerCase()))
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
#table1{
  width: 100%;
  border: #E5E5E5 2px solid;
  border-radius: 15px;
}
</style>