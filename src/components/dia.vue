<template>
  <el-dialog v-model="dialogVisible"
             title="添加联系人"
             top="5vh" width="40%">
    <div class="space1">name</div>
    <div>
      <el-input v-model="tableData.owner_id" clearable placeholder="name" type="text"/>
    </div>
    <div class="space1">province/city/distract</div>
    <el-cascader
        v-model="tableData.PCD"
        :options="options"
        style="width: 100%"
    ></el-cascader>
    <div class="space1">telephone</div>
    <el-input v-model="tableData.telephone" maxlength="11" oninput="value=value.replace(/[^\d]/g,'')"
              placeholder="telephone" type="text"/>
    <div class="space1">address</div>
    <div>
      <el-input v-model="tableData.address" clearable placeholder="address" type="text"/>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button @click="reset()">Reset</el-button>
        <el-button type="primary" @click="addContact()"
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
            owner_id: "",
            PCD: "",
            telephone: "",
            address: "",
            create_at: "",

          },
      options: [{
        value: '上海市',
        label: '上海市',
        children: [{
          value: '上海市',
          label: '上海市',
          children: [{
            value: '普陀区',
            label: '普陀区',
          }]
        }
        ]
      }]
    }
  },
  created() {
    this.dialogVisible = false
    this.rowid = null
  },
  methods: {
    addContact: function () {
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
        alert("填入信息有误！")
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