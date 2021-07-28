<template>
    <div id="moderator-lugar-page">
        <p>{{ estado }}</p>
        {{ lugar }}
        <button @click="approve">Aprobar</button>
        <button @click="disapprove">Desaprobar</button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            estado: "Esperando...",
            lugar: {}
        }
    },
    methods: {
        async approve() {
            await this.$axios.put("moderador/lugares/" + this.lugar.id + "/aprobar", {});
            this.estado = "Aprobado!";
        },
        async disapprove() {
            await this.$axios.put("moderador/lugares/" + this.lugar.id + "/desaprobar", {});
            this.estado = "Desaprobado!";
        },
        async load() {
            const { data } = await this.$axios.get("moderador/lugares/" + this.$route.params.lugarId);
            this.lugar = data;
        }
    },
    created() {
        this.load();
    }
}
</script>