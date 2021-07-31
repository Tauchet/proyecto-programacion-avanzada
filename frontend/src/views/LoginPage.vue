<template>
    <AuthPage>
        <div class="auth-page__form">
            <AppAlert :type="alertType" v-if="alertMessage">
                <p>{{ alertMessage }}</p>
            </AppAlert>
            <AppFormInput :errors="errors" name="user" label="Nombre de Usuario o Correo Electronico" v-model="form.user" />
            <AppFormInput :errors="errors" name="password" label="Contraseña" type="password" v-model="form.password" />
            <div class="auth-page__options">
                <AppButton type="success" @click="sendForm">Iniciar Sesión</AppButton>
                <p>¿Has olvido tu contraseña? <router-link to="/entrar-olvidada">Click aquí para recuperarla</router-link></p>
                <p>¿No tienes una cuenta? <router-link to="/registrarme">Registrarme</router-link></p>
            </div>
        </div>
    </AuthPage>
</template>

<script>

import AppFormInput from "../components/form/AppFormInput";
import AppButton from "../components/AppButton";
import AppAlert from "../components/AppAlert";
import AuthPage from "./AuthPage";
import routes from '../routes/routes';

import ValidationUtil from '../libs/ValidationUtil';

export default {
    name: "LoginPage",
    components: {AuthPage, AppAlert, AppButton, AppFormInput},
    data() {
        return {
            form: {
                user: '',
                password: ''
            },
            errors: {},
            alertMessage: this.$route.query.error ? this.$route.query.error : this.$route.query.success,
            alertType: this.$route.query.error ? 'danger' : (this.$route.query.success ? 'success' : 'danger')
        }
    },
    methods: {
        sendForm() {

            this.alertMessage = null;
            this.alertType = 'danger';

            // Validaciones previas --- inicio
            this.errors = {};
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'user');
            ValidationUtil.validateIfNotContainsSpaces(this.errors, this.form, 'user');
            ValidationUtil.validateIfExistsEmail(this.errors, this.form, 'user');
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'password');
            if (Object.keys(this.errors).length > 0) {
                return;
            }
            // Validaciones previas --- final

            const request = this.$axios.post('conectar', this.form);
            request.then(({ data }) => {
                this.$store.dispatch('signup', data).then(() => {
                    const rol = data.rol;
                    const navigator = routes.navegator[rol] || routes.navegator.USUARIO;
                    this.$router.push(navigator[0].path);
                })
            });

            request.catch(({ response }) => {

                const { data } = response;

                // Problemas de validación
                if (data.status === 2000) {
                    this.alertMessage = data.message;
                }

            });

        }
    }
}
</script>

<style scoped>

</style>