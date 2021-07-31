<template>
<AuthPage>
    <div class="auth-page__form">
        <AppFormInput :errors="errors" name="nombre"  label="Nombre" v-model="form.nombre" />
        <AppFormInput :errors="errors" name="email"  label="Correo Eletronico" type="email" v-model="form.email" />
        <AppFormInput :errors="errors" name="username"  label="Nombre de Usuario" v-model="form.username" />
        <AppFormInput :errors="errors" name="password"  label="Contraseña" type="password" v-model="form.password" />
        <AppFormInput :errors="errors" name="passwordConfirm"  label="Contraseña de Confirmación" type="password" v-model="form.passwordConfirm" />
        <AppCiudadInput :errors="errors" name="ciudadId"  v-model="form.ciudadId" />
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
import ValidationUtil from "../libs/ValidationUtil";
export default {
    name: "RegisterPage",
    components: {AuthPage, AppAlert, AppButton, AppCiudadInput, AppFormInput},
    data() {
        return {
            errors: {},
            form: {
                nombre: '',
                email: '',
                username: '',
                password: '',
                passwordConfirm: '',
                ciudadId: null
            },
            error: null
        }
    },
    methods: {
        sendForm() {

            this.error = null;

            // Validaciones previas --- inicio
            this.errors = {};
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'nombre');
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'email');
            ValidationUtil.validateIfNotContainsSpaces(this.errors, this.form, 'email');
            ValidationUtil.validateIfEmail(this.errors, this.form, 'email');
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'username');
            ValidationUtil.validateIfNotContainsSpaces(this.errors, this.form, 'username');
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'password');
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'passwordConfirm');
            ValidationUtil.validateIfPasswordsEquals(this.errors, this.form, 'password', 'passwordConfirm');
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'ciudadId');
            if (Object.keys(this.errors).length > 0) {
                return;
            }
            // Validaciones previas --- final

            // Imagen aleatoria.
            this.form.avatarUrl = "https://api.hello-avatar.com/adorables/120/" + (Math.random() + 1).toString(36).substring(7);

            const request = this.$axios.post('registro', this.form);
            request.then(() => {
                this.$router.push('/entrar?success=¡Has registrado correctamente tu cuenta! Inicia sesión desde aquí.');
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

</style>