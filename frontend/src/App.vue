<template>
    <div id="app">
        <header v-if="isHeader">
            <div class="container">
                <div class="header-logo">
                    <h1><i class="fas fa-map"></i> Lugares</h1>
                </div>
                <div class="header-nav">
                    <nav v-if="isLogged">
                        <ul>
                            <li class="header-nav__item" v-for="item in getNavegator"
                                :class="{active: item.path === currentPath}" :key="item.path">
                                <router-link :to="item.path">{{ item.title }}</router-link>
                            </li>

                        </ul>
                    </nav>
                </div>
                <div class="header-options">
                    <template v-if="isLogged">
                        <AppButton @click="logout">Cerrar Sesión</AppButton>
                        <div class="header-views" v-if="getRolNavegator.length > 0">
                            <router-link class="header-view__vista" v-for="item in getRolNavegator" :key="item" :to="getRolHomePage(item)">{{ item }}</router-link>
                        </div>
                    </template>
                    <template v-else>
                        <AppButton type="success" redirect="/entrar">Iniciar Sesión</AppButton>
                    </template>
                </div>
            </div>
        </header>
        <router-view/>
    </div>
</template>

<script>

import routes from './routes/routes';
import AppButton from "./components/AppButton";

export default {
    components: {AppButton},
    methods: {

        async loadGeneral() {
            const {data} = await this.$axios.get('general');
            this.$store.commit('loadGeneral', data);
        },

        logout() {
            this.$store.dispatch('logout').then(() => {
                this.$router.push('/entrar?success=¡Has cerrado correctamente tu sesión!');
            });
        },

        getRolHomePage(rol) {
            const navigator = routes.navegator[rol] || routes.navegator.USUARIO;
            return navigator[0].path;
        }

    },

    computed: {
        isHeader() {
            return this.$route.meta && !this.$route.meta.disableHeader;
        },
        isLogged() {
            return this.$store.state.user !== null;
        },
        currentRol() {
            return this.$route.meta ? (this.$route.meta.rol || "USUARIO") : "USUARIO";
        },
        getUserRol() {
            return this.$store.state.rol || "USUARIO";
        },
        getRolNavegator() {
            const array = routes.roles.slice(0, routes.roles.indexOf(this.getUserRol) + 1);
            array.splice(array.indexOf(this.currentRol), 1);
            return array;
        },
        getNavegator() {
            return routes.navegator[this.currentRol] || routes.navegator.USUARIO;
        },
        currentPath() {
            return this.$route.path;
        }
    },

    created() {
        this.loadGeneral();
    }

}
</script>

<style lang="scss">

@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css');
@import url('https://cdn.jsdelivr.net/npm/normalize.css@8.0.1/normalize.css');
@import url('https://fonts.googleapis.com/css2?family=Nunito:wght@400;700&display=swap');

* {
    font-family: 'Nunito', sans-serif;
    box-sizing: border-box;
}

#app {
    display: flex;
    flex-direction: column;
}

.header-view__vista {
    text-decoration: none;
    color: #ccc;
    transition: 0.4s;
}

.header-view__vista:not(:last-child) {
    padding-right: 0.5rem;
    border-right: 2px solid #ccc;
}

.header-view__vista:hover {
    transform: scale(0.95);
    color: white;
}

.header-views {
    margin-top: 0.5rem;
    display: flex;
    gap: 0.5rem;
}

.lugar-popup {
    min-width: 150px;
    max-width: 150px;
}

.lugar-popup__title {
    margin-bottom: 0.5rem;
}

.lugar-popup__title p:nth-child(2) {
    font-size: 0.8rem;
    text-transform: uppercase;
    color: #666;
    font-weight: bold;
}

.lugar-popup p, .lugar-popup a {
    margin: 0;
    font-size: 1rem;
}

.lugar-popup p:first-child {
    font-weight: bold;
}

html, body, #app {
    width: 100%;
    height: 100%;
}

main {
    width: 100%;
}

header {
    background: #131A26;
    color: white;

    h1, p {
        margin: 0;
    }

    .container {
        display: flex;
        align-items: center;

    }

    nav ul {
        display: flex;
        list-style: none;
    }

    .header-nav {
        flex: 1;
    }

    .header-nav__item {
        display: block;
    }

    .header-nav__item a {
        display: block;
        color: #B8B8B8;
        font-weight: bold;
        text-decoration: none;
        text-transform: uppercase;
        transition: 0.3s;
        border: 1px solid transparent;
        padding: 0.7rem 2rem;
        text-align: center;
    }

    .header-nav__item a:hover {
        transform: scale(1.03);
        color: #d7d6d6;
    }

    .header-nav__item.active a {
        border-radius: 0.5rem;
        border-color: #007AFF;
        background: #2D3544;
        color: #5AA8FF;
    }

}

.app-form-input {

    text-align: left;

    &:not(:first-child:last-child) {
        margin-bottom: 1rem;
    }

    .app-form-input__name {
        font-weight: bold;
        margin-bottom: 1rem;
        text-transform: uppercase;
        color: #A5A5A5;
        transition: 0.4s;
    }

    .app-form-input__value {
        resize: vertical;
        display: block;
        width: 100%;
        padding: 0.5rem;
        border-radius: 0.5rem;
        border: 1px solid #C1C1C1;
        outline: none;
        transition: 0.4s;
    }


}

.app-form-input.read {
    .app-form-input__value:disabled {
        background: white;
    }
}

.app-form-input__error {
    margin-top: 0.3rem;
    color: #ff5252;
    font-weight: bold;
}

.app-form-input__error p {
    margin: 0;
    font-size: 15px;
}

.app-form-input.focus {

    .app-form-input__name {
        color: #1976DB;
    }

    .app-form-input__value {
        border-color: #1976DB;
    }

}

.app-form-input.error {

    .app-form-input__name {
        color: #ff5252;
    }

    .app-form-input__value {
        border-color: #ff5252;
    }

}

.app-form-input.label .app-form-input__value {
    margin-top: 0.3rem;
}

.container {
    max-width: 80%;
    min-width: 80%;
    margin: auto;
    padding: 1rem 0;
}

.box-center {
    display: flex;
    align-items: center;
    justify-content: center;
}

</style>
