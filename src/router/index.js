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
                component: ()=>import('@/view/home'),
            },
            {
                path: 'orders/:type',
                component:()=>import('@/view/orders'),
                props: true
            },
            {
                path: 'profile',
                component: ()=>import('@/view/profile')
            },
            {
                path: 'directory',
                component: ()=>import('@/view/directory')
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