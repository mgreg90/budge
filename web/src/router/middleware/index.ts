import { Router } from "vue-router";
import authMiddleware from "./authMiddleware";

const middlewares: ((router: Router) => void)[] = [
  authMiddleware,
]

const applyMiddleware = (router: Router) => {
  middlewares.forEach(mdw => mdw(router))
}

export default applyMiddleware