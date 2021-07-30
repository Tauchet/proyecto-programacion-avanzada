<template>
<div class="app-ciudad-input">
    <label for="">Ciudad</label>
    <select @change="onChangeValue">
        <option selected disabled>Seleccionar Ciudad</option>
        <option v-for="item in list" :key="item.id" :value="item.id">
            {{ item.nombre }}
        </option>
    </select>
</div>
</template>

<script>
export default {
    name: "AppCiudadInput",
    methods: {
        onChangeValue(event) {
            this.$emit('input', event.target.value);
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