<template>
    <AuthPage>
        <form action="#" class="auth-page__form">
            <AppAlert v-if="error">
                <p>{{error}}</p>
            </AppAlert>
            <AppFormInput label="Nombre de Usuario o Correo Electronico" v-model="form.user" />
            <AppFormInput label="Contraseña" type="password" v-model="form.password" />
            <div class="auth-page__options">
                <AppButton type="success" @click="sendForm">Iniciar Sesión</AppButton>
                <p>¿Has olvido tu contraseña? <router-link to="/entrar-olvidada">Click aquí para recuperarla</router-link></p>
                <p>¿No tienes una cuenta? <router-link to="/registrarme">Registrarme</router-link></p>
            </div>
        </form>
    </AuthPage>
</template>

<script>
import AppFormInput from "../components/form/AppFormInput";
import AppButton from "../components/AppButton";
import AppAlert from "../components/AppAlert";
import AuthPage from "./AuthPage";
export default {
    name: "LoginPage",
    components: {AuthPage, AppAlert, AppButton, AppFormInput},
    data() {
        return {
            form: {
                user: '',
                password: ''
            },
            error: null
        }
    },
    methods: {
        sendForm() {

            this.error = null;

            const request = this.$axios.post('registro', this.form);
            request.then(() => {
                // NOTAS: Redireccionar al registrarse.
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