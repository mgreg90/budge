import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Home from "../views/Home.vue";
import applyMiddleware from "./middleware";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/home"
  },
  {
    path: "/home",
    name: "Home",
    component: () => import(/* webpackChunkName: "home" */ "../views/Home.vue"),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/signup",
    name: "SignUp",
    component: () => import(/* webpackChunkName: "signup" */ "../views/SignUp.vue"),
  },
  {
    path: "/login",
    name: "LogIn",
    component: () => import(/* webpackChunkName: "login" */ "../views/Login.vue"),
  },
  {
    path: "/:path(.*)*",
    name: "Not Found",
    component: () => import(/* webpackChunkName: "notfound" */ "../views/NotFound.vue")
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

applyMiddleware(router)

export default router;
