import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/signup',
    name: 'Signup',
    component: () => import('../views/Signup.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 重置路由的方法
export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}

// 设置动态路由, 刷新页面会导致页面路由重置
export const setRoutes = () => {
  const storedMenus = localStorage.getItem("menus")
  if (storedMenus) {
    // 拼装动态路由
    const homeViewRoute = {
      path: '/',
      name: 'HomeView',
      component: () => import('../views/HomeView.vue'),
      redirect: '/home',
      children: [
        { path: 'profile', name: 'Profile', component: () => import('../views/Profile') }
      ]
    }
    const menus = JSON.parse(storedMenus)
    menus.forEach(item => {
      if (item.path) { // 当且仅当path不为空是才设置路由
        let itemMenu =  {
          path: item.path.replace('/', ''),
          name: item.name,
          component: () => import('../views/' + item.pagePath + '.vue')
        }
        homeViewRoute.children.push(itemMenu)
      } else if (item.children.length) {
        item.children.forEach(item => {
          if (item.path) {
            let itemMenu =  {
              path: item.path.replace('/', ''),
              name: item.name,
              component: () => import('../views/' + item.pagePath + '.vue')
            }
            homeViewRoute.children.push(itemMenu)
          }
        })
      }
    })

    const currentRouteNames = router.getRoutes().map(v => v.name)
    if (!currentRouteNames.includes('HomeView')) {
      // 动态添加到当前路由
      router.addRoute(homeViewRoute)
    }
  }
}

// 重置我就在set一下路由
setRoutes();

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name) // 设置当前的路由名称, 为了在Header组件中去使用
  store.commit("setPath") // 触发store数据更新
  // 未找到路由的情况
  if (!to.matched.length) {
    const storedMenus = localStorage.getItem("menus")
    if (storedMenus) {
      next("/404")
    } else {
      next("/login")
    }
  }
  // 其他情况
  next() // 放行
})

export default router
