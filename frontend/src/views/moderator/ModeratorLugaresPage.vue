<template>
    <div id="moderator-lugares-page">
        <h2>Lugares por Aprobar</h2>
        <div class="lugar" v-for="lugar in list" :key="lugar.id">
            <router-link :to="{ path: '/moderador/lugares/' + lugar.id }">
                <p>{{ lugar.nombre }}</p>
            </router-link>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            list: []
        }
    },
    methods: {
        async load() {
            const { data } = await this.$axios.get("moderador/lugares");
            this.list = data;
        }
    },
    created() {
        this.load();
    }
}
</script>