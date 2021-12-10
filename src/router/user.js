const routes = [
    {
        path: 'home',
        component: ()=>import('@/view/home'),
    },
    {
        path: 'orders/:type',
        component:()=>import('@/view/order'),
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
export default routes