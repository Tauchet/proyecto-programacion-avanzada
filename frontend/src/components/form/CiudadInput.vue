<template>
    <div class="ciudad-input">
        <select name="ciudadId" id="" @change="onChange">
            <option value="-1">Seleccionar Ciudad</option>
            <option :value="ciudad.id" v-for="ciudad in list" :key="ciudad.id">
                {{ciudad.nombre}}
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
            return this.$store.getters.allCiudades;
        }
    }
}
</script>