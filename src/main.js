import { createApp } from 'vue'
import App from './App'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router/index'
import axios from 'axios'

const app = createApp(App)
app.config.unwrapInjectedRef = true
app.use(router)
app.use(ElementPlus)
axios.defaults.withCredentials = true;
app.config.globalProperties.$axios = axios
app.mount('#app')