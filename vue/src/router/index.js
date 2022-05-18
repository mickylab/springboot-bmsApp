import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UserView from '../views/User.vue'
import RoleView from '../views/Role.vue'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: HomeView,
    redirect: '/home',
    children: [
      {path: 'home', name: 'Home', component: () => import('../views/Home.vue')},
      {path: 'user', name: 'User', component: UserView},
      {path: 'role', name: 'Role', component: RoleView},
      {path: 'profile', name: 'Profile', component: () => import('../views/Profile.vue')},
      {path: 'file', name: 'File', component: () => import('../views/File.vue')},
      {path: '/im', name: 'Im', component: () => import('../views/Im.vue')}
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/signup',
    name: 'Signup',
    component: () => import('../views/Signup.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name) // 设置当前的路由名称, 为了在Header组件中去使用
  store.commit("setPath") // 触发store数据更新
  next() // 放行
})

export default router
