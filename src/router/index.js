import { createWebHistory, createRouter } from 'vue-router'

const routes = [
    {
        path: '/login',
        name: 'login',
        component: ()=>import('@/view/login')
    },
    {
        // 页面逻辑
        path: '/',
        redirect: '/home',
        component: ()=>import('@/view/index'),
        children:[
            {
                path: 'home',
                name: 'home',
                component: ()=>import('@/components/home1.vue'),
            },
        ]
    },

]

const history = createWebHistory()
const router = createRouter({
    history, // 路由模式
    routes,
})
export default router