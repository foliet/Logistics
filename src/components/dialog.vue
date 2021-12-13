<template>
  <el-dialog v-model="dialogVisible"
             title="添加订单"
             width="40%" top="5vh"
             :before-close="handleClose">
    <div class="space1">收件人地址</div>
    <div>
      <el-select
          v-model="orderData.contactId"
          placeholder="选择收件人地址"
          style="width:90%">
        <el-option
            v-for="contact in contacts"
            :key="contact.ownerId"
            :label="contact.ownerName"
            :value="contact.telephone"
        >
          <span>联系人：{{ contact.ownerName }}</span>
          <el-popover
              :width="200"
              placement="right"
              style="height: 10%"
              trigger="hover"
          >
            <div>电话：{{ contact.telephone }}</div>
            <div>省市区：{{ contact.PCD }}</div>
            <div>地址：{{ contact.address }}</div>
            <template #reference>
              <span style="float: right">...</span>
            </template>
          </el-popover>
        </el-option>
      </el-select>
      <el-button @click="this.$refs.d.dialogVisible=true">
        <el-icon>
          <plus/>
        </el-icon>
      </el-button>
    </div>
    <div class="space1">货物名称</div>
    <el-input type="text" placeholder="货物名称" v-model="orderData.title" clearable/>
    <div class="space1">货物介绍</div>
    <el-input v-model="orderData.remark" :autosize="{minRows:4,maxRows:7}" clearable placeholder="货物介绍"
              type="textarea"/>
    <div class="space1">
      <span style="padding: 0 30% 0 0">体积</span><span style="padding: 0 28% 0 0">重量</span><span>价值</span>
    </div>
    <div>
      <el-input class="space2" type="tel" placeholder="体积" min="0" v-model="orderData.volume"
                oninput="value=value.replace(/[^\d]/g,'')">
        <template #append>m³</template>
      </el-input>
      <el-input class="space2" type="tel" placeholder="重量" min="0" v-model="orderData.weight"
                oninput="value=value.replace(/[^\d]/g,'')">
        <template #append>KG</template>
      </el-input>
      <el-input class="space2" type="tel" placeholder="价值" min="0" v-model="orderData.value"
                oninput="value=value.replace(/[^\d]/g,'')">
        <template #append>CNY</template>
      </el-input>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button @click="reset()">Reset</el-button>
        <el-button type="primary" @click="addOrder()"
        >Confirm</el-button>
      </span>
    </template>
  </el-dialog>
  <dia ref="d"></dia>
</template>

<script>
import {Plus} from '@element-plus/icons'
import dia from '../components/dia'

export default {
  components: {
    Plus,
    dia
  },
  data() {
    return {
      dialogVisible: "",
      handleClose: "",
      orderData:
          {
            contactId: "",
            title: "",
            weight: "",
            value: "",
            volume: "",
            remark: "",
          },
      contacts: [],
    }
  },
  created() {
    this.dialogVisible = false
    this.getoptions()
  },
  methods:{
    addOrder:function ()
    {
      if (this.orderData.contactId != null && this.orderData.title != 0 && this.orderData.title != null && Number(this.orderData.weight) > 0 && Number(this.orderData.volume) > 0 && Number(this.orderData.value > 0)) {
        this.$axios.post('https://mc.rainspace.cn:4443/add-order', this.orderData).then(() => {
          this.$emit("confirm")
        })
        this.dialogVisible = false;
      } else {
        this.$message.error('输入信息有误！')
      }
    },
    reset:function ()
    {
      for(const key in this.orderData){
        this.orderData[key]=null;
      }
    },
    getoptions() {
      this.$axios.get('https://mc.rainspace.cn:4443/get_contacts').then(res => {
            this.contacts = res.data.contacts;
          }
      )
    },
  }
}
</script>

<style>
.space1{
  padding:10px 0;
}
.space2{
  width:33%;
}


</style>
