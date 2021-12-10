const routes = [
    {
        path: 'home',
        component: ()=>import('@/view/admin/home'),
    },
    {
        path: 'chunk',
        component:()=>import('@/view/admin/chunk'),
    },
    {
        path: 'order',
        component: ()=>import('@/view/admin/order')
    },
    {
        path: 'staff',
        component: ()=>import('@/view/admin/staff')
    },
    {
        path: 'user',
        component: ()=>import('@/view/admin/user')
    }
]
export default routes