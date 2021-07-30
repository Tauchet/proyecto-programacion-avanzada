<template>
<div class="app-categoria-input">
    <label for="">Categoria</label>
    <select @change="onChangeValue">
        <option selected disabled>Seleccionar Categoria</option>
        <option v-for="item in list" :key="item.id" :value="item.id">
            {{ item.nombre }}
        </option>
    </select>
</div>
</template>

<script>
export default {
    name: "AppCategoriaInput",
    methods: {
        onChangeValue(event) {
            this.$emit('input', event.target.value);
        }
    },
    computed: {
        list() {
            return this.$store.getters.categorias;
        }
    }
}
</script>

<style scoped>

</style>