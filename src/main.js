import {createApp} from 'vue'
import App from './App'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router/index'
import axios from 'axios'
import * as echarts from 'echarts'

axios.defaults.withCredentials = true;
const instance = axios.create({
    baseURL:"https://mc.rainspace.cn:20443/logistics"
})
const app = createApp(App)
app.config.unwrapInjectedRef = true
app.use(router)
app.use(ElementPlus)
app.config.globalProperties.$axios = instance
app.config.globalProperties.$echarts = echarts
app.mount('#app')