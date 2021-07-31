<template>
<AuthPage>
    <div class="auth-page__form">
        <AppFormInput label="Nombre" v-model="form.nombre" />
        <AppFormInput label="Correo Eletronico" type="email" v-model="form.email" />
        <AppFormInput label="Nombre de Usuario" v-model="form.username" />
        <AppFormInput label="Contraseña" type="password" v-model="form.password" />
        <AppFormInput label="Contraseña de Confirmación" type="password" v-model="form.passwordConfirm" />
        <AppFormInput label="Avatar URL" v-model="form.avatarUrl" />
        <AppCiudadInput v-model="form.ciudadId" />
        <AppAlert v-if="error">
            <p>{{error}}</p>
        </AppAlert>
        <div class="auth-page__options">
            <AppButton @click="sendForm">Registrarse</AppButton>
            <p>¿Ya tienes una cuenta? <router-link to="/entrar">Iniciar Sesión</router-link></p>
        </div>
    </div>
</AuthPage>
</template>

<script>
import AppFormInput from "../components/form/AppFormInput";
import AppCiudadInput from "../components/form/AppCiudadInput";
import AppButton from "../components/AppButton";
import AppAlert from "../components/AppAlert";
import AuthPage from "./AuthPage";
export default {
    name: "RegisterPage",
    components: {AuthPage, AppAlert, AppButton, AppCiudadInput, AppFormInput},
    data() {
        return {
            form: {
                nombre: '',
                email: '',
                username: '',
                password: '',
                passwordConfirm: '',
                avatarUrl: '',
                ciudadId: null,
                categoriaId: null
            },
            error: null
        }
    },
    methods: {
        sendForm() {

            this.error = null;

            const request = this.$axios.post('registro', this.form);
            request.then(() => {
                this.$router.push('/entrar?success=¡Has registrado correctamente tu cuenta! Inicia sesión desde aquí.');
            });

            request.catch(({ response }) => {

                const { data } = response;

                // Problemas de validación
                if (data.status === 1000) {
                    this.error = data.message;
                }

            });

        }
    }
}
</script>

<style scoped>

</style>