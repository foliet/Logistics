<template>
  <el-container>
    <el-container>
      <el-main>
        <div style="padding:10px 0 0 0">
          <el-icon style="font-size: 20px;margin-right: 1em">
            <Search/>
          </el-icon>
          <el-input v-model="search" placeholder="输入关键字搜索" style="width: 95%" type="text"></el-input>
        </div>
        <br/>
        <div style="height: 79%">
          <el-table :data="staffs.slice((currentPage-1)*pageSize,currentPage*pageSize)" id="table1" height="100%">
            <el-table-column sortable label="Id" prop="id"/>
            <el-table-column sortable label="姓名" prop="name"/>
            <el-table-column sortable label="性别" prop="gender"/>
            <el-table-column sortable label="状态" prop="statusName"/>
            <el-table-column>
              <template #header>
                <el-button style="width: 50%;float: right;margin-right: 25%" @click="reset();this.dialogVisible=true">
                  新增
                </el-button>
              </template>
              <template #default="scope">
                <el-button round style="color: #FFB500; border: 1px #FFB500 solid" @click="edit(scope.row)">
                  修改
                </el-button>
                |
                <el-button :class="choose(check(scope.row.statusName))" :disabled="check(scope.row.statusName)" round
                           @click="scope.row.visible = true">删除
                </el-button>
                <el-dialog v-model="scope.row.visible">确定要删除吗？
                  <template #footer>
      <span class="dialog-footer">
        <el-button @click="scope.row.visible = false">取消</el-button>
        <el-button type="primary" @click="deleted(scope.row)"
        >确认</el-button>
      </span>
                  </template>
                </el-dialog>
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
      <!--      <div>
              <el-input v-model="staff.gender" clearable placeholder="gender" type="text"/>
            </div>-->
      <el-select
          v-model="staff.gender"
          placeholder="类型"
          style="width:90%">
        <el-option
            v-for="typer in types"
            :key="typer"
            :label="typer.label"
            :value="typer.value"
        >
          <span>{{ typer.label }}</span>
        </el-option>
      </el-select>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addStaff()"
        >确定</el-button>
      </span>
      </template>
    </el-dialog>
    <el-footer>
      <div style="padding-left: 35%">
        <el-pagination :current-page="currentPage" :page-size="pageSize" :total="staffs.length" background
                       layout="prev, pager, next, jumper" style="width: 40%;float: left"
                       @current-change="currentChange">
        </el-pagination>
        <a>选择每页展示数：</a>
        <el-select v-model="pageSize" size="mini" style="width: 10%;">
          <el-option
              v-for="typer in typeses"
              :key="typer"
              :value="typer.value">
            {{ typer.value }}
          </el-option>
        </el-select>
      </div>
    </el-footer>
  </el-container>
</template>
<script>
import {Search} from "@element-plus/icons";

export default {
  components: {
    Search
  },
  data() {
    return {
      search: null,
      pageSize: 5,
      currentPage: 1,
      staffs: [],
      allStaffs: [],
      staff: {
        id: null,
        gender: '',
        name: '',
        status: null,
      },
      dialogVisible: false,
      types: [{
        value: '男',
        label: '男',
      }, {
        value: '女',
        label: '女',
      },
        {
          value: '隐藏',
          label: '隐藏',
        },],
      typeses: [{value: 1,},
        {value: 2,},
        {value: 3,},
        {value: 4,},
        {value: 5,},
        {value: 6,},
        {value: 7,},
        {value: 8,},
        {value: 9,},
        {value: 10,},
        {value: 11,},
        {value: 12,},
        {value: 13,},
      ]
    }
  },
  watch: {
    search() {
      this.filter()
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
    check(param) {
      return param !== '空闲中';
    },
    choose(param) {
      if (param == true) return 'disable'
      else return 'able'
    },
    currentChange(index) {
      this.currentPage = index
    },
    getStaffs() {
      this.$axios.get('https://mc.rainspace.cn:4443/admin/get-staffs').then(res => {
        const currentPage = this.currentPage
        this.allStaffs.length = 0
        for (const staff of res.data.staffs) {
          staff.visible = false
          if (staff.status === 0) {
            staff.statusName = '空闲中'
          } else {
            staff.statusName = '工作中'
          }
          this.allStaffs.push(staff)
        }
        this.filter()
        this.currentPage = currentPage
      })
    },
    filter() {
      this.staffs.length = 0;
      this.staffs = this.allStaffs.filter(
          (data) =>
              !this.search || data.name.toLowerCase().includes(this.search.toLowerCase())
              || data.gender.toLowerCase().includes(this.search.toLowerCase())
      )
    },
    reset() {
      for (const key in this.staff) {
        this.staff[key] = null;
      }
    },
    addStaff() {
      if(!this.staff.gender||!this.staff.name){
        this.$message.error('信息不可为空！')
        return
      }
      if (this.staff.id == null) {
        this.$axios.post('https://mc.rainspace.cn:4443/admin/add-staff', this.staff).then(() => {
          this.getStaffs()
        })

      } else {
        this.$axios.post('https://mc.rainspace.cn:4443/admin/edit-staff', this.staff).then(() => {
          this.getStaffs()
        })
      }
      this.dialogVisible = false;
    },
    edit(row) {
      this.staff.status = row.status
      this.staff.gender = row.gender;
      this.staff.name = row.name;
      this.staff.id = row.id;
      this.dialogVisible = true;
    },
    deleted(row) {
      this.$axios.post('https://mc.rainspace.cn:4443/admin/delete-staff', {id: row.id}).then(() => {
        this.getStaffs()
      })
    }
  }
}
</script>

<style scoped>
#table1 {
  width: 100%;
  border: #E5E5E5 2px solid;
  border-radius: 15px;
}

.able {
  color: #FF3D00;
  border: 1px #FF3D00 solid
}

.disable {
  color: gray;
  border: 1px gray solid
}
</style>