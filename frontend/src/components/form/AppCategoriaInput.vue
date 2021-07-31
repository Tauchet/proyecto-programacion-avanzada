<template>
    <AppFormSelector placeholder="Seleccionar Categoria"
                     label="Categoria"
                     :value="value"
                     @input="onChangeValue" :list="list"
                     key-type="nombre"
                     value-type="id" />
</template>

<script>
import AppFormSelector from "./AppFormSelector";
export default {
    name: "AppCiudadInput",
    components: {AppFormSelector},
    props: {
        value: {
            default: null
        }
    },
    methods: {
        onChangeValue(newValue) {
            this.$emit('input', newValue);
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