<template>
  <el-dialog v-model="dialogVisible"
             title="编辑用户"
             top="5vh" width="40%">
    <div class="space1">姓名</div>
    <div>
      <el-input v-model="tableData.username" clearable placeholder="姓名" type="text"/>
    </div>
    <div class="space1">邮箱</div>
    <div>
      <el-input v-model="tableData.email" clearable placeholder="邮箱" type="email"/>
    </div>
    <div class="space1">密码</div>
    <div>
      <el-input v-model="tableData.password" clearable placeholder="密码" type="password"/>
    </div>
    <div class="space1">类别</div>
    <el-select
        v-model="tableData.groupId"
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
        <el-button type="primary" @click="addUser()"
        >确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>

export default {
  emits: ['confirm'],
  data() {
    return {
      dialogVisible: "",
      tableData: {
        id: null,
        username: '',
        email: '',
        password: '',
        groupId: 0,
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
    addUser: function () {
      if (this.tableData.username != 0 && this.tableData.username != null
          && this.tableData.password != 0 && this.tableData.password != null
          && this.tableData.email != 0 && this.tableData.email != null) {
        if (this.tableData.id == null) {
          this.$axios.post('https://mc.rainspace.cn:4443/admin/add-user', this.tableData).then(() => {
            this.$emit('confirm')
          })
        } else {
          this.$axios.post('https://mc.rainspace.cn:4443/admin/edit-user', this.tableData).then(() => {
            this.$emit('confirm')
          })
        }
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