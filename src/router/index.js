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
        component: ()=>import('@/view/index'),
        redirect: '/home',
        children:[
            {
                path: 'home',
                name: 'home',
                component: ()=>import('@/components/home.vue'),
            },
            {
                path: 'orders',
                component:()=>import('@/view/orders')
            }
        ]
    },

]

const history = createWebHistory()
const router = createRouter({
    history, // 路由模式
    routes,
})
export default router