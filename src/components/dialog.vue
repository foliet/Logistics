<template>
  <el-dialog v-model="dialogVisible"
      title="添加订单"
      width="40%" top="5vh"
      :before-close="handleClose">
    <div class="space1">发件人地址</div>
    <div><el-input type="text" placeholder="发件人地址" v-model="orderData.address1" clearable/></div>
    <div class="space1">收件人地址</div>
    <div><el-input type="text" placeholder="收件人地址" v-model="orderData.address2" clearable/></div>
    <div class="space1">发件人联系方式</div>
    <el-input type="text" placeholder="发件人联系方式" maxlength="11" oninput = "value=value.replace(/[^\d]/g,'')" v-model="orderData.phone1"/>
    <div class="space1">收件人联系方式</div>
    <el-input type="text" placeholder="收件人联系方式" maxlength="11" oninput = "value=value.replace(/[^\d]/g,'')" v-model="orderData.phone2"/>
    <div class="space1">货物名称</div>
    <el-input type="text" placeholder="货物名称" v-model="orderData.title" clearable/>
    <div class="space1">
      <span style="padding: 0 30% 0 0">体积</span><span style="padding: 0 28% 0 0">重量</span><span>价值</span>
    </div>
    <div>
      <el-input class="space2" type="tel" placeholder="体积" min="0" v-model="orderData.volume" oninput = "value=value.replace(/[^\d]/g,'')"><template #append>m³</template></el-input>
      <el-input class="space2" type="tel" placeholder="重量" min="0" v-model="orderData.weight" oninput = "value=value.replace(/[^\d]/g,'')"><template #append>KG</template></el-input>
      <el-input class="space2" type="tel" placeholder="价值" min="0" v-model="orderData.value" oninput = "value=value.replace(/[^\d]/g,'')"><template #append>CNY</template></el-input>
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
</template>

<script>
import { ElMessageBox } from 'element-plus'

export default{
  data() {
      return {
        dialogVisible:"",
        handleClose:"",
        orderData:
            {
              address1:"",
              address2:"",
              title:"",
              weight:"",
              value:"",
              phone1:"",
              phone2:"",
              volume:"",
            }
      }
  },
  created() {
    this.dialogVisible = false
    this.handleClose = (done) => {
      ElMessageBox.confirm('Are you sure to close this dialog?')
          .then(() => {
            done()
          })
          .catch(() => {
            // catch error
          })
    }
  },
  methods:{
    addOrder:function ()
    {
      if(this.orderData.address1!=0&&this.orderData.address1!=null&&this.orderData.address2!=0&&this.orderData.address2!=null&&Number(this.orderData.phone1)<20000000000&&Number(this.orderData.phone1)>=10000000000&&Number(this.orderData.phone2)<20000000000&&Number(this.orderData.phone2)>=10000000000
          &&this.orderData.title!=0&&this.orderData.title!=null &&Number(this.orderData.weight)>0&&Number(this.orderData.volume)>0&&Number(this.orderData.value>0)) {
        this.$axios.post('https://mc.rainspace.cn:4443/add-order',this.orderData).then(()=>{
          this.$emit("confirm")
        })
        this.dialogVisible=false;
      }
      else{
        alert("填入信息有误！")
      }
    },
    reset:function ()
    {
      for(const key in this.orderData){
        this.orderData[key]=null;
      }
    }
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
