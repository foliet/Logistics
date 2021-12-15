<template>
  <el-dialog v-model="dialogVisible"
             title="添加用户"
             top="5vh" width="40%">
    <div class="space1">姓名</div>
    <div>
      <el-input v-model="tableData.username" clearable placeholder="name" type="text"/>
    </div>
    <div class="space1">邮箱</div>
    <div>
      <el-input v-model="tableData.email" clearable placeholder="email" type="email"/>
    </div>
    <div class="space1">密码</div>
    <div>
      <el-input v-model="tableData.password" clearable placeholder="password" type="password"/>
    </div>
    <div class="space1">类别</div>
    <el-select
        v-model="tableData.typing"
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
      tableData: {
        username: '',
        email: '',
        password: '',
        typing: '',
        userId: ''
      },
      types: [{
        value: 0,
        label: '用户',
      }, {
        value: 1,
        label: '管理员',
      },]
    }
  },
  created() {
    this.dialogVisible = false
    this.userId = null;
  },
  methods: {
    $message: undefined,
    addUser: function () {
      if (this.tableData.username != 0 && this.tableData.username != null
          && this.tableData.password != 0 && this.tableData.password != null
          && this.tableData.email != 0 && this.tableData.email != null) {
        if (this.userId == null) {
          axios.post('https://mc.rainspace.cn:4443/add-user', this.tableData)
        } else {
          axios.post('https://mc.rainspace.cn:4443/edit-user', {tableData: this.tableData, id: this.userId})
        }
        this.userId = null;
        this.dialogVisible = false;
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