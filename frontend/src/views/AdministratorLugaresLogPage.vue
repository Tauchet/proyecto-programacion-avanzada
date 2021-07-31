<template>
<div id="administrator-lugares-log-page">
    <div class="container">
        <div class="lugar-log" v-for="lugar in list" @click="() => open(lugar.id)" :key="lugar.id">
            <div class="lugar-log__executor">
                <UsuarioBlock :usuario="lugar.moderador" />
            </div>
            <div class="lugar-log__text">
                <p>Ha <strong>{{lugar.estado}}</strong> el lugar <strong>{{ lugar.nombre }}</strong> de {{ lugar.categoria.nombre }} en la ciudad de {{ lugar.ciudad.nombre }} el {{ lugar.fechaAprobacion }}.</p>
            </div>
            <div class="lugar-log__creator">
                <UsuarioBlock :usuario="lugar.usuario" />
            </div>
        </div>
    </div>
</div>
</template>

<style scoped>
.lugar-log {
    display: flex;
    align-items: center;
    background: #eee;
    border-radius: 0.5rem;
    padding: 0.5rem 2rem;
    cursor: pointer;
    transition: 0.3s;
}
.lugar-log:hover {
    transform: scale(0.98);
    background: #ddd;
}
.lugar-log:not(:last-child) {
    margin-bottom: 0.5rem;
}
.lugar-log__text {
    flex: 1;
    text-align: center;
    font-size: 1.1rem;
}
</style>

<script>
import UsuarioBlock from "../components/UsuarioBlock";
export default {
    name: "AdministratorLugaresLogPage",
    components: {UsuarioBlock},
    data() {
        return {
            list: []
        }
    },
    methods: {
        open(lugarId) {
            this.$router.push('/lugares/' + lugarId);
        },
        async load() {
            const { data } = await this.$axios.get('administrador/lugares');
            this.list = data;
        }
    },
    created() {
        this.load();
    }
}
</script>

