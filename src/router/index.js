import {createRouter, createWebHistory} from 'vue-router'
import user from './user'
import admin from './admin'

const routes = [
    {
        path: '/login',
        name: 'login',
        component: ()=>import('@/view/login')
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
    {
        // 页面逻辑
        path: '/1111',
        component: () => import('@/view/test'),
    },

]

const history = createWebHistory()
const router = createRouter({
    history, // 路由模式
    routes,
})
export default router