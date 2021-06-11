import { Router } from "vue-router"

const applyAuthMiddleware = (router: Router) => {
  router.beforeEach((to, from, next) => {
    const isLoggedIn = localStorage.getItem('currentUser')
    const routeRequiresAuth = to.matched.some(record => record.meta.requiresAuth)

    if (routeRequiresAuth && !isLoggedIn) {
      next('/login')
      return
    }
    
    next()
  })
}

export default applyAuthMiddleware