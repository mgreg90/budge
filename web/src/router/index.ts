import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import applyMiddleware from "./middleware";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/transactions",
  },
  {
    path: "/transactions",
    name: "Transactions",
    component: () => import(/* webpackChunkName: "transactions" */ "../views/Transactions.vue"),
  },
  {
    path: "/register",
    name: "Sign Up",
    component: () =>
      import(/* webpackChunkName: "signup" */ "../views/SignUp.vue"),
    meta: {
      public: true,
    },
  },
  {
    path: "/login",
    name: "Sign In",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/Login.vue"),
    meta: {
      public: true,
    },
  },
  {
    path: "/:path(.*)*",
    name: "Not Found",
    component: () =>
      import(/* webpackChunkName: "notfound" */ "../views/NotFound.vue"),
    meta: {
      public: true,
    },
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

applyMiddleware(router);

export default router;
