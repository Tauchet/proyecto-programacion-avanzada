import Vue from 'vue'
import VueRouter from 'vue-router'
import CreateLugarPage from "../views/CreateLugarPage";
import RegisterPage from "../views/RegisterPage";
import LugarPage from "../views/LugarPage";
import MyLugaresPage from "../views/MyLugaresPage";
import LoginPage from "../views/LoginPage";

import store from '../store';
import NotFoundPage from "../views/NotFoundPage";
import ModeratorRegisters from "../views/ModeratorRegisters";
import AdministratorLugaresLogPage from "../views/AdministratorLugaresLogPage";
import AdministratorUsers from "../views/AdministratorUsersPage";
import AdministratorCiudadesPage from "../views/AdministratorCiudadesPage";
import AdministratorCategoriasPage from "../views/AdministratorCategoriasPage";
import HomePage from "../views/HomePage";

import realRoutes from '../routes/routes';

Vue.use(VueRouter);

const routes = [
    {
        path: '/mis-lugares',
        component: MyLugaresPage,
        meta: {
            requireUser: true
        }
    },
    {
        path: '/lugares/crear',
        component: CreateLugarPage,
        meta: {
            requireUser: true
        }
    },
    {
        path: '/moderador/registros',
        component: ModeratorRegisters,
        meta: {
            requireUser: true,
            rol: "MODERADOR"
        }
    },
    {
        path: '/administrador/lugares-verificados',
        component: AdministratorLugaresLogPage,
        meta: {
            requireUser: true,
            rol: "ADMINISTRADOR"
        }
    },
    {
        path: '/administrador/usuarios',
        component: AdministratorUsers,
        meta: {
            requireUser: true,
            rol: "ADMINISTRADOR"
        }
    },
    {
        path: '/administrador/ciudades',
        component: AdministratorCiudadesPage,
        meta: {
            requireUser: true,
            rol: "ADMINISTRADOR"
        }
    },
    {
        path: '/administrador/categorias',
        component: AdministratorCategoriasPage,
        meta: {
            requireUser: true,
            rol: "ADMINISTRADOR"
        }
    },
    {
        path: '/lugares/:lugarId',
        component: LugarPage
    },
    {
        path: '/registrarme',
        component: RegisterPage,
        meta: {
            requireVisitor: true,
            disableHeader: true
        }
    },
    {
        path: '/entrar',
        component: LoginPage,
        meta: {
            requireVisitor: true,
            disableHeader: true
        }
    },
    {
        path: '/',
        component: HomePage
    },
    {
        path: '/404',
        component: NotFoundPage
    },
    {
        path: '*',
        redirect: '/404'
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});


router.beforeEach((to, from, next) => {

    const requireVisitor = to.matched.some(record => record.meta.requireVisitor);


    if (requireVisitor && store.state.user !== null) {
        const navigator = routes.navegator[store.state.rol] || routes.navegator.USUARIO;
        next(navigator[0].path);
    } else {
        const requireUser = to.matched.some(record => record.meta.requireUser);
        if (requireUser && store.state.user === null) {
            next('/entrar');
        } else {
            const rol = to.matched[to.matched.length - 1].meta.rol || "USUARIO";
            const currentRol = store.state.rol || "USUARIO";
            if (realRoutes.roles.indexOf(currentRol) >= realRoutes.roles.indexOf(rol)) {
                next();
            } else {
                next('/404');
            }
        }
    }

})

export default router;
