<template>
  <el-dialog v-model="dialogVisible"
             title="添加用户"
             top="5vh" width="40%">
    <div class="space1">姓名</div>
    <div>
      <el-input v-model="tableData.ownerId" clearable placeholder="name" type="text"/>
    </div>
    <div class="space1">邮箱</div>
    <div>
      <el-input v-model="tableData.email" clearable placeholder="email" type="email"/>
    </div>
    <div class="space1">密码</div>
    <div>
      <el-input v-model="tableData.password" clearable placeholder="password" type="password"/>
    </div>
    <el-select
        v-model="orderData.contactId"
        placeholder="选择收件人地址"
        style="width:90%">
      <el-option
          v-for="typer in types"
          :key="typer"
          :label="typer"
          :value="typer"
      >
        <span>{{ typer }}</span>
      </el-option>
    </el-select>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button @click="reset()">Reset</el-button>
        <el-button type="primary" @click="addUser()"
        >Confirm</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      dialogVisible: "",
      handleClose: "",
      rowid: "",
      tableData:
          {
            ownerId: '',

          },
      types: [{
        value: '用户',
        label: '用户',
      }, {
        value: '管理员',
        label: '管理员',
      },]
    }
  },
  created() {
    this.dialogVisible = false
    this.rowid = null
  },
  methods: {
    $message: undefined,
    addUser: function () {
      if (this.tableData.owner_id != 0 && this.tableData.owner_id != null && Number(this.tableData.telephone) < 20000000000 && Number(this.tableData.telephone) >= 10000000000
          && this.tableData.PCD != 0 && this.tableData.PCD != null && this.tableData.address != 0 && this.tableData.address != null) {
        if (this.rowid == null) {
          axios.post('https://mc.rainspace.cn:4443/add-contact', this.tableData)
        } else {
          axios.post('https://mc.rainspace.cn:4443/edit-contact', {tableData: this.tableData, id: this.rowid})
        }
        this.dialogVisible = false;
        this.rowid = null;
      } else {
        this.$message.error('输入信息有误！')
      }
    },
    reset: function () {
      for (const key in this.tableData) {
        this.tableData[key] = null;
      }
    }
  }
}
</script>

<style scoped>

</style>