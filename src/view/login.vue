<template>
  <div class="page-body">
    <div id="login-box" class="container">
      <div class="form-container sign-up-container">
        <form @submit.prevent="register">
          <h1>注册</h1>
          <div class="txtb">
            <input id="label1" v-model="registerData.username" type="text">
            <label data-placeholder="Username" for="label1"></label>
          </div>
          <div class="txtb">
            <input id="label2" v-model="registerData.email" type="email">
            <label data-placeholder="Email" for="label2"></label>
          </div>
          <div class="txtb">
            <input id="label3" v-model="registerData.password" type="password">
            <label data-placeholder="Password" for="label3"></label>
          </div>
          <div class="txtb">
            <input id="label4" v-model="registerData.password1" type="password">
            <label data-placeholder="Confirm Password" for="label4"></label>
          </div>
          <div>
            <div class="txtb" style="width:45%;display: inline-block">
              <input id="label5" v-model="registerData.verifyCode" type="text"/>
              <label data-placeholder="验证码" for="label5"></label>
            </div>
            <button style="margin:0 auto" type="button" @click="checkEmail()">发送验证码</button>
          </div>
          <button>注册</button>
        </form>
      </div>
      <div class="form-container sign-in-container">
        <form @submit.prevent="login">
          <h1>登录</h1>
          <div class="txtb">
            <input id="label6" v-model="loginData.email" type="email">
            <label data-placeholder="Email" for="label6"></label>
          </div>
          <div class="txtb">
            <input id="label7" v-model="loginData.password" type="password">
            <label data-placeholder="Password" for="label7"></label>
          </div>
          <router-link to="/retrieve">忘记密码？</router-link>
          <button>登录</button>
        </form>
      </div>

      <div class="overlay-container">
        <div class="overlay">
          <div class="overlay-panel overlay-left">
            <h1>已有账号？</h1>
            <p>请使用您的账号进行登录</p>
            <button id="signIn" class="ghost">登录</button>
          </div>
          <div class="overlay-panel overlay-right">
            <h1>没有账号?</h1>
            <p>立即注册加入我们，和我们一起开始旅程吧</p>
            <button id="signUp" class="ghost">注册</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from "jquery"

$(function () {
  $("#signUp").click(function () {
    $("#login-box").addClass('right-panel-active')
  })
})

$(function () {
  $("#signIn").click(function () {
    $("#login-box").removeClass('right-panel-active')
  })
})

$(function () {
  $(".txtb input").on("focus", function () {
    $(this).addClass("focus")
  })
})

$(function () {
  $(".txtb input").on("blur", function () {
    if ($(this).val() == '')
      $(this).removeClass("focus")
  })
})

export default {
  title: 'login',
  data() {
    return {
      registerData: {
        email: "",
        username: "",
        password: "",
        password1: "",
        verifyCode: ""
      },
      loginData: {
        email: "",
        password: ""
      },
    }
  },
  mounted() {
    document.title = "欢迎登录"
  },
  methods: {
    checkEmail: function () {
      if (this.registerData.email === "") {
        this.$message.error('请先输入邮箱')
        return
      }
      this.$axios.get('/check-email', {
        params: {
          email: this.registerData.email
        }
      }).then(res => {
        if (res.data.status !== 0) {
          this.$message.error(res.data.msg)
        }
      })
    },

    login: function () {
      this.$axios.post('/login', this.loginData).then(res => {
        if (res.data.status === 0) {
          this.$router.push('/home')
        } else {
          this.$message.error(res.data.msg)
        }
      })
    },

    register: function () {
      if (this.registerData.password !== this.registerData.password1) {
        this.$message.error('两次密码不一致')
        return
      }
      this.$axios.post('/register', this.registerData).then(res => {
        if (res.data.status === 0) {
          this.$router.push('/home')
        } else {
          this.$message.error(res.data.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
* {
  box-sizing: border-box;
}

h1 {
  font-weight: bold;
  margin: 0;
}

p {
  font-size: 14px;
  line-height: 20px;
  letter-spacing: .5px;
  margin: 20px 0 30px;
}

span {
  font-size: 12px;
}

a {
  color: #333;
  font-size: 14px;
  text-decoration: none;
  margin: 15px 0;
}

.page-body {
  font-family: 'Montserrat', sans-serif;
  background-image: linear-gradient(120deg, #3498db, #8e44ad);;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
}

.container {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, .25), 0 10px 10px rgba(0, 0, 0, .22);
  position: relative;
  overflow: hidden;
  width: 768px;
  max-width: 100%;
  min-height: 480px;

}

.form-container form {
  background: #fff;
  display: flex;
  flex-direction: column;
  padding: 0 50px;
  height: 100%;
  justify-content: center;
  text-align: center;
}

.social-container {
  margin: 20px 0;
}

.social-container a {
  border: 1px solid #ddd;
  border-radius: 50%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin: 0 5px;
  height: 40px;
  width: 40px;
}

.social-container a:hover {
  background-color: #eee;

}

.txtb {
  border-bottom: 2px solid #adadad;
  position: relative;
  margin: 10px 0;
  width: 100%;
}

.txtb input {
  font-size: 15px;
  color: #333;
  border: none;
  width: 100%;
  outline: none;
  background: none;
  padding: 0 3px;
  height: 35px;
}

.txtb label::before {
  content: attr(data-placeholder);
  position: absolute;
  top: 50%;
  left: 5px;
  color: #adadad;
  transform: translateY(-50%);
  transition: .5s;
}

.txtb label::after {
  content: '';
  position: absolute;
  left: 0;
  top: 100%;
  width: 0;
  height: 2px;
  background-image: linear-gradient(120deg, #3498db, #8e44ad);
  transition: .5s;
}

.focus + label::before {
  top: -5px;
}

.focus + label::after {
  width: 100%;
}

button {
  border-radius: 20px;
  border: 1px solid #ff4b2b;
  background: #ff4b2b;
  color: #fff;
  font-size: 12px;
  font-weight: bold;
  padding: 12px 45px;
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
  cursor: pointer;
}

button:active {
  transform: scale(.95);
}

button:focus {
  outline: none;
}

button.ghost {
  background: transparent;
  border-color: #fff;
}

.form-container {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all .6s ease-in-out;
}

.form-container button {
  background: linear-gradient(120deg, #3498db, #8e44ad);
  border: none;
  background-size: 200%;
  color: #fff;
  transition: .5s;
}

.form-container button:hover {
  background-position: right;
}

.sign-in-container {
  left: 0;
  width: 50%;
  z-index: 2;
}

.sign-in-container form a {
  position: relative;
  left: 100px;
}

.sign-up-container {
  left: 0;
  width: 50%;
  z-index: 1;
  opacity: 0;
}

.sign-up-container button {
  margin-top: 20px;
}

.overlay-container {
  position: absolute;
  top: 0;
  left: 50%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: transform .6s ease-in-out;
  z-index: 100;
}

.overlay {
  background-image: linear-gradient(120deg, #3498db, #8e44ad);
  color: #fff;
  position: relative;
  left: -100%;
  height: 100%;
  width: 200%;
  transform: translateY(0);
  transition: transform .6s ease-in-out;
}

.overlay-panel {
  position: absolute;
  top: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0 40px;
  height: 100%;
  width: 50%;
  text-align: center;
  transform: translateY(0);
  transition: transform .6s ease-in-out;
}

.overlay-right {
  right: 0;
  transform: translateY(0);

}

.overlay-left {
  transform: translateY(-20%);
}

.container.right-panel-active .sign-in-container {
  transform: translateY(100%);
}

.container.container.right-panel-active .overlay-container {
  transform: translateX(-100%);
}

.container.right-panel-active .sign-up-container {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
}

.container.container.right-panel-active .overlay {
  transform: translateX(50%);
}

.container.container.right-panel-active .overlay-left {
  transform: translateY(0);
}

.container.container.right-panel-active .overlay-right {
  transform: translateY(20%);
}

</style>