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
        <el-table :data="contacts.filter(
        (data) =>
          !search || data.receiverName.toLowerCase().includes(search.toLowerCase())
          || data.address.toLowerCase().includes(search.toLowerCase())
          || data.PCD.toLowerCase().includes(search.toLowerCase())
          || data.telephone.toLowerCase().includes(search.toLowerCase())
          )" height="100%" style="width: 100%;">
          <el-table-column label="用户名" prop="receiverName" sortable width="180"/>
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
import {View} from '@element-plus/icons'

export default {
  components: {
    dia2,
    View,
  },
  data() {
    return {
      search: '',
      imageUrl: '',
      user: {
        password: '',
        username: '',
        email: '',
      },
      contacts: [],
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
  methods: {
    getinfo() {
      this.$axios.get('https://mc.rainspace.cn:4443/get-user').then(res => {
        this.user = res.data.user
      });
      this.$axios.get('https://mc.rainspace.cn:4443/get-contacts?type=mine').then(res => {
        this.contacts = res.data.contacts
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
          });
          /*this.visible2 = false;*/
        }
      }
    },
    edit(id, row) {
      this.$refs.f.tableData.receiverName = row.receiverName;
      this.$refs.f.tableData.telephone = row.telephone;
      this.$refs.f.tableData.address = row.address;
      this.$refs.f.rowid = id;
      this.$refs.f.dialogVisible = true;
    },
    deleted(id) {
      this.$axios.post('https://mc.rainspace.cn:4443/delete-contact', id)
    },
  },
}

</script>

<style scoped>
.nameness {
  margin: 0;
  display: flex;
  vertical-align: center;
}

.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
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