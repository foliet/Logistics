const routes = [
    {
        path: 'home',
        component: ()=>import('@/view/admin/home'),
    },
    {
        path: 'chunks',
        component:()=>import('@/view/admin/chunks'),
    },
    {
        path: 'orders',
        component: ()=>import('@/view/admin/orders')
    },
    {
        path: 'staff',
        component: ()=>import('@/view/admin/staff')
    }
]
export default routes