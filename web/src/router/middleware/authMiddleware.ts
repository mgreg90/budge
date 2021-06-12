import { Router } from "vue-router";

const applyAuthMiddleware = (router: Router): void => {
  router.beforeEach((to, from, next) => {
    const isLoggedIn = localStorage.getItem("currentUser");
    const routeRequiresAuth = to.matched.some((record) => !record.meta.public);

    if (routeRequiresAuth && !isLoggedIn) {
      next("/login");
      return;
    }

    next();
  });
};

export default applyAuthMiddleware;
