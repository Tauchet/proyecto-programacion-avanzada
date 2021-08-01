<template>
<div id="my-lugares-page">
    <div class="container">
        <template v-if="info.length === 0">
            <div class="jumbotron">
                <p><i class="fas fa-star-half-alt fa-5x"></i></p>
                <h2>¡No has hecho favorito aún algun lugar!</h2>
                <p>¿Quieres explocar un poco? Dale <router-link to="/">Click aquí</router-link> o dirigete al apartado de <strong>EXPLORAR</strong> en el menú de navegación.</p>
            </div>
        </template>
        <template v-else>
            <AppTitle space><i class="fas fa-heart"></i> Lugares Favoritos</AppTitle>
            <LugarLista :list="info" />
        </template>
    </div>
</div>
</template>

<script>

import AppTitle from "../components/AppTitle";
import LugarLista from "./MyLugaresPage/LugarLista";

export default {
    name: "LugaresFavoritos",
    components: {LugarLista, AppTitle},
    data() {
        return {
            info: []
        }
    },
    methods: {
        async load() {
            const { data } = await this.$axios.get('lugares/mis-favoritos');
            this.info = data;
        }
    },
    created() {
        this.load();
    }
}
</script>

<style scoped>
.jumbotron {
    text-align: center;
    color: #444;
    padding: 2rem 0;
}
</style>