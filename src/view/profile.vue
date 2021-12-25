<template>
  <el-container class="nameness">
    <el-header style="background: #F8F8F8;line-height: 60px;">
      <span style="width:100%">
        <span style="color: #666666">用户名：</span>
        <span style="color: #FE8C00">{{ user.username }}</span>
      </span>
      <span style="width:100%;margin-left: 20%">
        <span style="color: #666666">邮箱：</span>
        <span>{{ user.email }}</span>
      </span>
      <el-button round style="float: right;margin-top: 0.7em" @click="this.visible2=true">修改密码</el-button>
    </el-header>
    <el-main>
      <div style="height: 95%">
        <el-table :data="contacts.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                  height="100%" style="width: 100%;">
          <el-table-column label="省市区" prop="PCD" sortable width="180"/>
          <el-table-column label="电话" prop="telephone" sortable/>
          <el-table-column label="地址" prop="address" sortable/>
          <el-table-column prop="operations">
            <template #header>
              <el-input v-model="search" placeholder="Type to search" size="mini"/>
            </template>
            <template #default="scope">
              <el-button @click="edit(scope.row)">修改</el-button>
              |
              <el-button @click="deleted(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
    <el-footer>
      <div style="padding-left: 35%">
      <el-pagination :current-page="currentPage" :page-size="pageSize" :total="contacts.length" background
                     layout="prev, pager, next, jumper" style="width: 40%;float: left" @current-change="currentChange">
      </el-pagination>
      <el-button size="mini" style="width:20%;float:right" @click="add">
        <el-icon size="14">
          <circle-plus/>
        </el-icon>
        新建我的地址
      </el-button>
      </div>
    </el-footer>
  </el-container>

  <dia2 ref="f"></dia2>
  <el-dialog v-model="visible2" title="修改密码">
    <div class="space1">旧密码：</div>
    <el-input v-model="oldpsd" :type="pwdtype"></el-input>
    <div class="space1">新密码：</div>
    <el-input v-model="newpsd" :type="pwdtype"></el-input>
    <div class="space1">确认新密码：</div>
    <el-input v-model="newpsd1" :type="pwdtype">
    </el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="changetype()">
      <el-icon>
        <View/>
      </el-icon>
    </el-button>
        <el-button @click="visible2 = false">取消</el-button>
        <el-button type="primary" @click="editpsd"
        >确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import dia2 from '/src/components/dia2'
import {CirclePlus, View} from '@element-plus/icons'

export default {
  components: {
    CirclePlus,
    dia2,
    View,
  },
  data() {
    return {
      search: null,
      pageSize: 7,
      currentPage: 1,
      imageUrl: '',
      user: {
        password: '',
        username: '',
        email: '',
      },
      contacts: [],
      allContacts: [],
      psd: '',
      visible: '',
      visible2: '',
      pwdtype: '',
      oldpsd: '',
      newpsd1: '',
      newpsd: '',
    }
  },
  created() {
    this.getinfo()
    this.visible = false;
    this.visible2 = false;
    this.pwdtype = 'password'
  },
  mounted() {
    document.title = "基本信息"
  },
  watch: {
    search() {
      this.contacts = this.allContacts.filter(
          (data) =>
              !this.search || data.receiverName.toLowerCase().includes(this.search.toLowerCase())
              || data.address.toLowerCase().includes(this.search.toLowerCase())
              || data.PCD.toLowerCase().includes(this.search.toLowerCase())
              || data.telephone.toLowerCase().includes(this.search.toLowerCase())
      )
    }
  },
  methods: {
    currentChange(index) {
      this.currentPage = index
    },
    getinfo() {
      this.$axios.get('https://mc.rainspace.cn:4443/get-user').then(res => {
        this.user = res.data.user
      });
      this.$axios.get('https://mc.rainspace.cn:4443/get-contacts?type=mine').then(res => {
        for(const contact of res.data.contacts){
          contact.PCD=contact.province+contact.city+contact.district
          this.allContacts.push(contact)
        }
        this.search=null
        this.search=''
      })
    },
    changetype() {
      this.pwdtype = (this.pwdtype === 'password' ? 'text' : 'password');
    },
    editpsd() {
      if (this.newpsd !== this.newpsd1)
        this.$message.error("两次输入密码不一致！")
      else {
        if (this.newpsd == null || this.newpsd == 0)
          this.$message.error("密码不能为空！")
        else {
          this.$axios.post('https://mc.rainspace.cn:4443/edit-password', {
            oldpsd: this.oldpsd,
            newpsd: this.newpsd
          }).then(res=>{
            if(res.data.status<10){
              this.$router.push('/logout')
            }else{
              this.$message.error(res.data.msg)
            }
          });
          /*this.visible2 = false;*/
        }
      }
    },
    edit(id, row) {
      this.$refs.f.reset();
      this.$refs.f.tableData.receiverName = row.receiverName;
      this.$refs.f.tableData.telephone = row.telephone;
      this.$refs.f.tableData.address = row.address;
      this.$refs.f.rowid = id;
      this.$refs.f.dialogVisible = true;
    },
    deleted(id) {
      this.$axios.post('https://mc.rainspace.cn:4443/delete-contact', id)
    },
    add() {
      this.$refs.f.reset();
      this.$refs.f.type = true;
      this.$refs.f.tableData.receiverName = this.user.username;
      this.$refs.f.dialogVisible = true;
    }
  },
}

</script>

<style scoped>
.nameness {
  margin: 0;
  display: flex;
  vertical-align: center;
}

.avatar-uploader-icon svg {
  margin-top: 74px; /* (178px - 28px) / 2 - 1px */
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>