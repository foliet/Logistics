<template>
  <div>用户名：{{ user.name }}</div>
  <div>邮箱：{{ user.email }}</div>
  <div>我的地址库：</div>
  <el-table :data="contacts.filter(
        (data) =>
          !search || data.receiverName.toLowerCase().includes(search.toLowerCase())
          || data.address.toLowerCase().includes(search.toLowerCase())
          || data.PCD.toLowerCase().includes(search.toLowerCase())
          || data.telephone.toLowerCase().includes(search.toLowerCase())
          )" height="70%" style="width: 100%">
    <el-table-column label="Name" prop="receiverName" sortable width="180"/>
    <el-table-column label="province/city/distract" prop="PCD" sortable width="180"/>
    <el-table-column label="Telephone" prop="telephone" sortable/>
    <el-table-column label="Address" prop="address" sortable/>
    <el-table-column prop="operations">
      <template #header>
        <el-input v-model="search" placeholder="Type to search" size="mini"/>
      </template>
      <template #default="scope">
        <el-button @click="edit(scope.row)">修改</el-button>
        |
        <el-button @click="deleted(scope.row)">删除</el-button>
      </template>
      <!--      -->
    </el-table-column>
  </el-table>
  <dia ref="f"></dia>
  <el-button @click="comfirmpsd">修改个人信息</el-button>
  <el-dialog v-model="visible" title="请输入密码：">
    <el-input v-model="psd" clearable type="password"></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="editinfo()"
        >Confirm</el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="visible2" title="修改个人信息">
    <div class="space1">新用户名：</div>
    <el-input v-model="newname" clearable type="text"></el-input>
    <div class="space1">新邮箱：</div>
    <el-input v-model="newemail" clearable type="email"></el-input>
    <div class="space1">新密码：</div>
    <el-input v-model="newpsd" clearable type="pwdtype">
      <el-button @click="changetype">
        <el-icon style="float: right">
          <view/>
        </el-icon>
      </el-button>
    </el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="changeinfo()"
        >Confirm</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      user: [],
      psd: '',
      visible: '',
      visible2: '',
      pwdtype: 'password',
      newname: '',
      newemail: '',
      newpsd: '',
    }
  },
  created() {
    this.getinfo()
    this.visible = false;
  },
  mounted() {
    document.title = "基本信息"
  },
  methods: {
    getinfo() {
      this.axios.get('https://mc.rainspace.cn:4443/admin/get-user').then(res => {
        this.user = res.data.user
      });
      this.axios.get('https://mc.rainspace.cn:4443/admin/get-contact?type=mine').then(res => {
        this.user = res.data.user
      })

    },
    comfirmpsd() {
      this.visible = true;
    },
    editinfo() {
      if (this.psd == this.user.psd) {
        this.visible = false;
        this.visible2 = true;
      } else this.$message.error("密码错误！")
    },
    changetype() {
      this.pwdtype = this.pwdtype === 'password' ? 'text' : 'password';
    },
    changeinfo() {
      if (this.newemail == null || this.newemail == 0 || this.newpsd == null || this.newpsd == 0 || this.newname == null || this.newname == 0)
        this.$message.alert("你没有做出任何修改！")
      else {
        if (this.newemail != null && this.newemail != 0) this.axios.post('https://mc.rainspace.cn:4443/admin/edit-user', this.newemail);
        if (this.newpsd != null && this.newpsd != 0) this.axios.post('https://mc.rainspace.cn:4443/admin/edit-user', this.newpsd);
        if (this.newname != null && this.newname != 0) this.axios.post('https://mc.rainspace.cn:4443/admin/edit-user', this.newname);
        this.$message.success("修改成功！")
        this.getinfo();
        this.visible2 = false;
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
      this.$axios.post('https://mc.rainspace.cn:4443/delete-contact?type=mine', id)
    }
  }

}
</script>

<style scoped>

</style>