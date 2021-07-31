<template>
<div id="my-lugares-page">
    <div class="container">
        <template v-if="info.APROBADO.length == 0 && info.ESPERANDO.length == 0 && info.DESAPROBADO.length == 0">
            <div class="jumbotron">
                <p><i class="fas fa-star-half-alt fa-5x"></i></p>
                <h2>¡No has publicado aún algun lugar!</h2>
                <p>¿Quieres crear uno? Dale <router-link to="/lugares/crear">Click aquí</router-link> o dirigete al apartado de <strong>CREAR LUGAR</strong> en el menú de navegación.</p>
            </div>
        </template>
        <template v-else>
            <AppAlert type="success" v-if="redirect">
                <p>{{ redirect }}</p>
            </AppAlert>
            <template v-if="info.APROBADO.length > 0">
                <AppTitle space><i class="fas fa-map-marked-alt"></i> Lugares publicados</AppTitle>
                <LugarLista :list="info.APROBADO" />
            </template>
            <template v-if="info.ESPERANDO.length > 0">
                <AppTitle space><i class="far fa-map"></i> Lugares en verificación</AppTitle>
                <LugarLista :list="info.ESPERANDO" />
            </template>
            <template v-if="info.DESAPROBADO.length > 0">
                <AppTitle space><i class="fas fa-atlas"></i> Lugares desaprobados</AppTitle>
                <LugarLista :list="info.DESAPROBADO" />
            </template>
        </template>
    </div>
</div>
</template>

<script>
import AppTitle from "../components/AppTitle";
import LugarLista from "./MyLugaresPage/LugarLista";
import AppAlert from "../components/AppAlert";
export default {
    name: "MyLugaresPage",
    components: {AppAlert, LugarLista, AppTitle},
    data() {
        return {
            redirect: this.$route.query.redirect || null,
            info: {
                APROBADO: [],
                ESPERANDO: [],
                DESAPROBADO: []
            }
        }
    },
    methods: {
        async load() {
            const newInfo = {
                APROBADO: [],
                ESPERANDO: [],
                DESAPROBADO: []
            };
            const { data } = await this.$axios.get('lugares/mios');
            for (let place of data) {
                newInfo[place.estado].push(place);
            }
            this.info = newInfo;
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