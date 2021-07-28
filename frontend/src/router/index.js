import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

import LoginPage from '../views/LoginPage.vue';
import RegisterPage from '../views/RegisterPage.vue';

// Página de Administración
import AdminCiudadesPage from '../views/admin/AdminCiudadesPage.vue';
import AdminCategoriasPage from '../views/admin/AdminCategoriasPage.vue';

// Página de Usuario
import CreateLugaresPage from '../views/app/CreateLugaresPage.vue';

// Página de Moderador
import ModeratorLugarPage from '../views/moderator/ModeratorLugarPage.vue';
import ModeratorLugaresPage from '../views/moderator/ModeratorLugaresPage.vue';

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/registro',
    name: 'Register',
    component: RegisterPage
  },
  {
    path: '/conectar',
    name: 'Login',
    component: LoginPage
  },
  {
    path: '/lugares/crear',
    name: 'CrearLugar',
    component: CreateLugaresPage
  },
  {
    path: '/admin/ciudades',
    name: 'AdminCiudades',
    component: AdminCiudadesPage
  },
  {
    path: '/admin/categorias',
    name: 'AdminCategorias',
    component: AdminCategoriasPage
  },
  {
    path: '/moderador/lugares',
    name: 'ModeradorLugares',
    component: ModeratorLugaresPage
  },
  {
    path: '/moderador/lugares/:lugarId',
    name: 'ModeratorLugarPage',
    component: ModeratorLugarPage
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
