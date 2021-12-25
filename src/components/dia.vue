<template>
  <el-dialog v-model="dialogVisible"
             title="添加联系人"
             top="5vh" width="40%">
    <div class="space1">收件人用户名</div>
    <div>
      <el-input v-model="tableData.receiverName" clearable placeholder="姓名" type="text"/>
    </div>
    <div class="space1">省/市/区</div>
    <el-cascader
        v-model="tableData.PCD"
        :options="options"
        style="width: 100%"
        placeholder="地区"
    ></el-cascader>
    <div class="space1">联系电话</div>
    <el-input v-model="tableData.telephone" placeholder="电话" type="text"/>
    <div class="space1">详细地址</div>
    <div>
      <el-input v-model="tableData.address" clearable placeholder="地址" type="text"/>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button @click="reset()">重置</el-button>
        <el-button type="primary" @click="addContact()"
        >确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>

export default {
  emits:['confirm'],
  data() {
    return {
      dialogVisible: "",
      handleClose: "",
      tableData:
          {
            id: null,
            receiverName: "",
            PCD: [],
            telephone: "",
            address: "",
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
          },{
            value: '长宁区',
            label: '长宁区',
          },{
            value: '闵行区',
            label: '闵行区',
          }]
        }
        ]
      },{
        value: '江西省',
        label: '江西省',
        children: [{
          value: '新余市',
          label: '新余市',
          children: [{
            value: '渝水区',
            label: '渝水区',
          },{
            value: '分宜县',
            label: '分宜县',
          }]
        },{
          value: '南昌市',
          label: '南昌市',
          children: [{
            value: '东湖区',
            label: '东湖区',
          },{
            value: '西湖区',
            label: '西湖区',
          }]
        }]
    }]
    }
  },
  created() {
    this.dialogVisible = false
  },
  methods: {
    addContact: function () {
      this.tableData.province = this.tableData.PCD[0]
      this.tableData.city = this.tableData.PCD[1]
      this.tableData.district = this.tableData.PCD[2]
      if (this.tableData.id == null) {
        this.$axios.post('https://mc.rainspace.cn:4443/add-contact?type=others', this.tableData).then(res => {
          if (res.data.status >= 10) {
            this.$message.error(res.data.msg)
          }else{
            this.$emit('confirm')
          }
        })
      } else {
        this.$axios.post('https://mc.rainspace.cn:4443/edit-contact?type=others', this.tableData).then(res => {
          if (res.data.status >= 10) {
            this.$message.error(res.data.msg)
          }else{
            this.$emit('confirm')
          }
        })
      }
      this.dialogVisible = false;
    },
    reset: function () {
      for (const key in this.tableData) {
        this.tableData[key] = null;
        this.tableData.PCD=[]
      }
    }
  }
}
</script>

<style scoped>

</style>