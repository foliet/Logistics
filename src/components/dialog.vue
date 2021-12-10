<template>
  <el-dialog v-model="dialogVisible"
      title="添加订单"
      width="40%" top="5vh"
      :before-close="handleClose">
    <div class="space1">发件人地址</div>
    <div><el-select
        v-model="orderData.address1"
        placeholder="选择发件人地址"
       style="width:90%"
    >
      <el-option
          v-for="add_ress in addresses"
          :key="add_ress"
          :value="add_ress"
      >
      </el-option>
    </el-select>
    <el-button @click="innerVisible=true"><el-icon><plus /></el-icon></el-button></div>
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
  <el-dialog v-model="innerVisible"><div class="space1">新增地址</div>
    <el-input type="text" placeholder="新增地址：" v-model="new_address" clearable/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="innerVisible = false">Cancel</el-button>
        <el-button type="primary" @click="add_address()"
        >Confirm</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons'

export default{
  components:{
    Plus
  },
  data() {
      return {

        new_address:"",
        innerVisible:"",
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
            },
        addresses:[],
      }
  },
  created() {
    this.dialogVisible = false
    this.innerVisible=false
    this.handleClose = (done) => {
      ElMessageBox.confirm('Are you sure to close this dialog?')
          .then(() => {
            done()
          })
          .catch(() => {
            // catch error
          })
    }
    this.getoptions()
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
    },
    getoptions()
    {
      this.$axios.get('https://mc.rainspace.cn:4443/get_addresses').then(res=>{
        this.addresses=res.data.addresses;
      }
      )
    },
    add_address()
    {
      if(this.new_address!=null&&this.new_address!==0)
        this.$axios.post('https://mc.rainspace.cn:4443/add-address',this.new_address);
      this.innerVisible=false;
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
