import Session from "@/models/Session";
import { Router } from "vue-router";

const applyAuthMiddleware = (router: Router): void => {
  router.beforeEach((to, from, next) => {
    // TODO I think we should access the session through the store instead of
    // directly from localStorage, but I was getting `undefined` from `useStore()`
    // const store = useStore()
    // const isLoggedIn = store.getters.session?.isValid() ?? false

    const isLoggedIn = Session.fromToken()?.isValid() ?? false;
    const routeRequiresAuth = to.matched.some((record) => !record.meta.public);

    if (routeRequiresAuth && !isLoggedIn) {
      next("/login");
      return;
    }

    next();
  });
};

export default applyAuthMiddleware;
