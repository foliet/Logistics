<template>
  <el-container style="height: 100vh; ">
    <el-header id="head">
      <span class="title">RainSpace 物流</span>
    </el-header>
    <el-main style="margin: 0 25em 0 25em">
      <el-form :model="ruleForm"  ref="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="ruleForm.email" type="e-mail"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password" :rules="rules.password">
          <el-input v-model="ruleForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="password1" :rules="rules.password">
          <el-input v-model="ruleForm.password1" type="password"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="verifyCode">
          <el-input v-model="ruleForm.verifyCode" style="width: 50%"></el-input>
          <el-button style="margin-left: 1em" @click="verify" type="warning" plain>发送验证码</el-button>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button type="success" @click="submitForm" plain>提交</el-button>
          <el-button @click="resetForm('ruleForm')" type="danger" plain>重置</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>
<script>
export default {
  created() {
    document.title="找回密码"
  },
  data() {
    return {
      ruleForm: {
        email: '',
        password:'',
        password1: '',
        verifyCode:''
      },
      rules: {
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        password: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
        ],
        password1: [
          { required: true, message: '请输入确认密码', trigger: 'blur' }
        ],
        verifyCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    verify: function (){
      if(this.ruleForm.email===""){
        this.$message.error('请先输入邮箱')
        return
      }
      this.$axios.get('https://mc.rainspace.cn:4443/check-email',{
        params: {
          email: this.ruleForm.email
        }
      }).then(res=>{
        if(res.data.status!==0){
          this.$message.error(res.data.msg)
        }
      })
    },
    submitForm: function () {
      if(this.ruleForm.password !== this.ruleForm.password1){
        this.$message.error('两次输入密码不一致!');
        return;
      }
      this.$axios.post("https://mc.rainspace.cn:4443/retrieve",{password: this.password,verifyCode:this.verifyCode})
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
#head{
  background-color: #3f51b5;
  color: #fff;
  line-height: 60px;
  box-shadow:0 0 7px 3px #aaa;
  z-index: 1;
}
.title{
  font-size: 1.2em;
  font-weight: 500;
}
</style>