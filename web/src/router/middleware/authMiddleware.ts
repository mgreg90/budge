import { LOCAL_STORAGE_SESSION_KEY } from "@/types/domain.types";
import { Router } from "vue-router";

const applyAuthMiddleware = (router: Router): void => {
  router.beforeEach((to, from, next) => {
    const isLoggedIn = localStorage.getItem(LOCAL_STORAGE_SESSION_KEY);
    const routeRequiresAuth = to.matched.some((record) => !record.meta.public);

    if (routeRequiresAuth && !isLoggedIn) {
      next("/login");
      return;
    }

    next();
  });
};

export default applyAuthMiddleware;
