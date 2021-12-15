<template>
  <el-container style="height: 100vh; ">
    <el-header id="head">
      <span class="title">RainSpace 物流</span>
      <el-button style="float: right;margin-top: 0.8em" type="primary" plain>登录</el-button>
      <span style="float: right">已有账号？</span>
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
        <el-form-item label="验证码" prop="verify">
          <el-input v-model="ruleForm.verify" style="width: 50%"></el-input>
          <el-button style="margin-left: 1em" type="warning" plain>发送验证码</el-button>
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
  data() {
    return {
      ruleForm: {
        email: '',
        password:'',
        password1: '',
        verify:''
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
        verify: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm: function () {
      if(this.ruleForm.password !== this.ruleForm.password1){
        this.$message.error('两次输入密码不一致!');
        return;
      }
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