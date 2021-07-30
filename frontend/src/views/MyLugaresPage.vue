<template>
<div id="my-lugares-page">
    <div class="container">
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
    </div>
</div>
</template>

<script>
import AppTitle from "../components/AppTitle";
import LugarLista from "./MyLugaresPage/LugarLista";
export default {
    name: "MyLugaresPage",
    components: {LugarLista, AppTitle},
    data() {
        return {
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

</style>