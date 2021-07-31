<template>
<div id="not-found-page">
    <div class="container">
        <AppCiudadInput placeholder="Seleccionar Ciudad" v-model="form.ciudad" label="Ciudades"/>
        <AppFormSelector placeholder="Seleccionar Categoria" v-model="form.categoria" label="Categorias"/>
    </div>
</div>
</template>

<script>
import AppFormSelector from "../components/form/AppFormSelector";
import AppCiudadInput from "../components/form/AppCiudadInput";
export default {
    name: "NotFoundPage",
    components: {AppCiudadInput, AppFormSelector},
    data() {
        return {
            form: {
                ciudad: null,
                categoria: null
            }
        }
    }
}
</script>

<style scoped>

</style>