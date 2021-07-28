<template>
    <div class="register-page">
         <p><strong>Notificación:</strong> {{ result }}</p>
        <form action="#" @submit.prevent="executeRegister">

            <label for="nombre">Nombre</label>
            <input type="text" name="nombre" v-model="form.nombre">

            <label for="email">Correo Electronico</label>
            <input type="email" name="email" v-model="form.email">

            <label for="username">Nombre de Usuario</label>
            <input type="text" name="username" v-model="form.username">

            <label for="password">Contraseña</label>
            <input type="password" name="password" v-model="form.password">

            <label for="passwordConfirm">Confirmar Contraseña</label>
            <input type="password" name="passwordConfirm" v-model="form.passwordConfirm">

            <label for="avatarUrl">Avatar URL</label>
            <input type="text" name="avatarUrl" v-model="form.avatarUrl">

            <CiudadInput v-model="form.ciudadId"/>

            <button>Reigstrarse</button>
        </form>
    </div>
</template>

<script>
import CiudadInput from '../components/form/CiudadInput.vue';
export default {
  components: { CiudadInput },
    data() {
        return {
            result: "",
            form: {
                nombre: "",
                email: "",
                username: "",
                password: "",
                passwordConfirm: "",
                avatarUrl: "",
                ciudadId: -1
            }
        }
    },
    methods: {
        executeRegister() {

            const request = this.$axios.post("registro", this.form);
            request.then(() => {

                // Redirigir a login
                

            }).catch(({ response }) => {
                
                // Dependencias mínimas
                const { data } = response;
                if (data.status == 1000) {
                    this.result = data.message;
                }

            });

        }
    }
}
</script>