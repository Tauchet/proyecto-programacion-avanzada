<template>
<div class="administrator-list-page">
    <div class="container">
        <AppAlert v-if="error">
            <p>{{error}}</p>
        </AppAlert>
        <AppFormInput :errors="errors" name="nombre" v-model="form.nombre" :label="'Nombre de ' + title" />
        <div class="box-center">
            <AppButton @click="sendForm">Agregar {{title}}</AppButton>
        </div>
        <div class="list">
            <template v-for="item in list">
                <AppCard :key="item.id">
                    <p>{{ item.nombre }}</p>
                </AppCard>
            </template>
        </div>
    </div>
</div>
</template>

<script>
import AppFormInput from "../components/form/AppFormInput";
import AppButton from "../components/AppButton";
import AppAlert from "../components/AppAlert";
import AppCard from "../components/AppCard";
import ValidationUtil from "../libs/ValidationUtil";
export default {
    name: "AdministratorListPage",
    components: {AppCard, AppAlert, AppButton, AppFormInput},
    props: {
        title: String,
        urlMethod: String
    },
    data() {
        return {
            list: [],
            form: {
                nombre: ""
            },
            errors: {},
            error: null
        }
    },
    created() {
        this.load();
    },
    methods: {
        async load() {
            const { data } = await this.$axios.get('administrador/' + this.urlMethod);
            this.list = data;
        },
        sendForm() {

            this.error = null;

            // Validaciones previas --- inicio
            this.errors = {};
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'nombre');
            if (Object.keys(this.errors).length > 0) {
                return;
            }
            // Validaciones previas --- final

            const request = this.$axios.post('administrador/' + this.urlMethod, this.form);
            request.then(({ data }) => {
                this.list.push(data);
            });
            request.catch(({ response }) => {

                const { data } = response;

                // Problemas de validación
                if (data.status === 2000) {
                    this.error = data.message;
                }

            });
        }
    }
}
</script>

<style scoped>
.list {
    margin-top: 1rem;
}
</style>