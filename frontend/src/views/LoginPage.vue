<template>
    <div class="login-page">
         <p><strong>Notificación:</strong> {{ result }}</p>
        <form action="#" @submit.prevent="execute">

            <label for="user">Correo Electronico o Nombre de Usuario</label>
            <input type="text" name="user" v-model="form.user">

            <label for="password">Contraseña</label>
            <input type="password" name="password" v-model="form.password">

            <button>Iniciar Sesión</button>
        </form>
    </div>
</template>

<script>
export default {
  components: {  },
    data() {
        return {
            result: "",
            form: {
                user: "",
                password: ""
            }
        }
    },
    methods: {
        execute() {

            const request = this.$axios.post("conectar", this.form);
            request.then(() => {

                this.result = "Si encontramos su usuario.";

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