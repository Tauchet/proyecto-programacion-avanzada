<template>
<AppFormSelector placeholder="Seleccionar Ciudad"
                 label="Ciudad"
                 :value="value"
                 @input="onChangeValue" :list="list"
                 key-type="nombre"
                 :name="name"
                 :errors="errors"
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
        },
        name: {
            default: null
        },
        errors: {
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
            return this.$store.getters.ciudades;
        }
    }
}
</script>

<style scoped>

</style>