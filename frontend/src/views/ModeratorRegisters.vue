<template>
<div id="moderator-registers">
    <div class="container">
        <template v-if="info.ESPERANDO.length > 0">
            <AppTitle space><i class="far fa-map"></i> Lugares en espera</AppTitle>
            <LugarLista :list="info.ESPERANDO" user force-clicked/>
        </template>
        <template v-if="info.DESAPROBADO.length > 0">
            <AppTitle space><i class="fas fa-map-marked-alt"></i> Lugares desaprobados por ti</AppTitle>
            <LugarLista :list="info.DESAPROBADO" user force-clicked/>
        </template>
    </div>
</div>
</template>

<script>
import AppTitle from "../components/AppTitle";
import LugarLista from "./MyLugaresPage/LugarLista";
export default {
    name: "ModeratorRegisters",
    components: {LugarLista, AppTitle},
    data() {
        return {
            info: {
                ESPERANDO: [],
                DESAPROBADO: []
            }
        }
    },
    methods: {
        async load() {
            const newInfo = {
                ESPERANDO: [],
                DESAPROBADO: []
            };
            const { data } = await this.$axios.get('moderador/lugares');
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