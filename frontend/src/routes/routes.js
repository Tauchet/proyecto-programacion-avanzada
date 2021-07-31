export default {
    roles: ['USUARIO', 'MODERADOR', 'ADMINISTRADOR'],
    navegator: {
        USUARIO: [{
            title: 'Explorar',
            path: '/'
        }, {
            title: 'Mis Lugares',
            path: '/mis-lugares'
        }, {
            title: 'Crear Lugar',
            path: '/lugares/crear'
        }],
        MODERADOR: [{
            title: 'Registros',
            path: '/moderador/registros'
        }],
        ADMINISTRADOR: [{
            title: 'Registros de Lugares',
            path: '/administrador/lugares-verificados'
        }, {
            title: 'Administrar Usuarios',
            path: '/administrador/usuarios'
        }, {
            title: 'Administrar Ciudades',
            path: '/administrador/ciudades'
        }, {
            title: 'Administrar Categorias',
            path: '/administrador/categorias'
        }]
    }
}