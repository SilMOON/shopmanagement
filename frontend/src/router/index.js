import Vue from 'vue'
import VueRouter from 'vue-router'
import GoodsList from '../views/GoodsList.vue'
import Login from '../views/Login.vue'

Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'Login',
        component: Login,
        meta: {
            title: 'Shop Management System'
        }
    },
    {
        path: '/goodslist',
        name: 'GoodsList',
        component: GoodsList,
        meta: {
            title: 'Shop Management System'
        }
    },
    {
        path: '/about',
        name: 'About',
        meta: {
            title: 'Shop Management System'
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
            import ( /* webpackChunkName: "about" */ '../views/About.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router