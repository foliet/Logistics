import {createRouter, createWebHistory} from 'vue-router'
import user from './user'
import admin from './admin'

const logout = {
    created(){
        this.$axios.get("https://mc.rainspace.cn:4443/logout")
        this.$router.replace('/login')
    }
}

const routes = [
    {
        path: '/login',
        name: 'login',
        component: ()=>import('@/view/login')
    },
    {
        path: '/logout',
        component: logout
    },
    {
      path: '/admin',
      component: ()=>import('@/view/admin/index'),
        redirect: '/admin/home',
        children: admin,
    },
    {
        // 页面逻辑
        path: '/',
        component: () => import('@/view/index'),
        redirect: '/home',
        children: user
    },
]


const history = createWebHistory()
const router = createRouter({
    history, // 路由模式
    routes,
})
export default router