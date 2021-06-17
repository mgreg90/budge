import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import applyMiddleware from "./middleware";

enum RouteEnum {
  Data = "/data",
  Login = "/login",
  Register = "/register",
  Transactions = "/transactions",
}

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: RouteEnum.Transactions,
  },
  {
    path: RouteEnum.Transactions,
    name: "Transactions",
    component: () => import(/* webpackChunkName: "transactions" */ "../views/Transactions.vue"),
  },
  {
    path: RouteEnum.Register,
    name: "Sign Up",
    component: () =>
      import(/* webpackChunkName: "signup" */ "../views/SignUp.vue"),
    meta: {
      public: true,
    },
  },
  {
    path: RouteEnum.Login,
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

const goTo = (route: RouteEnum) => router.push(route)

export { RouteEnum, goTo }

export default router;
