<template>
    <div class="categoria-input">
        <select name="categoriaId" id="" @change="onChange">
            <option value="-1">Seleccionar Categoria</option>
            <option :value="categoria.id" v-for="categoria in list" :key="categoria.id">
                {{categoria.nombre}}
            </option>
        </select>
    </div>
</template>

<script>
export default {
    methods: {
        onChange(event) {
            this.$emit("input", event.target.value);
        },
    },
    computed: {
        list() {
            return this.$store.getters.allCategorias;
        }
    }
}
</script>