import Vue from 'vue'
import VueRouter from 'vue-router'
import CreateLugarPage from "../views/CreateLugarPage";
import RegisterPage from "../views/RegisterPage";
import LugarPage from "../views/LugarPage";
import MyLugaresPage from "../views/MyLugaresPage";
import LoginPage from "../views/LoginPage";

Vue.use(VueRouter);

const routes = [
  {
    path: '/mis-lugares',
    component: MyLugaresPage
  },
  {
    path: '/lugares/crear',
    component: CreateLugarPage
  },
  {
    path: '/lugares/:lugarId',
    component: LugarPage
  },
  {
    path: '/registrarme',
    component: RegisterPage,
    meta: {
      disableHeader: true
    }
  },
  {
    path: '/entrar',
    component: LoginPage,
    meta: {
      disableHeader: true
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
