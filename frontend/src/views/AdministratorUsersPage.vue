<template>
<div id="administrator-users">
    <div class="container">
        <template v-for="(user, index) in list" >
            <div class="user" :key="user.id" v-if="isNotCurrentUser(user.id)">
                <div class="user__content">
                    <UsuarioBlock :usuario="user" />
                </div>
                <div class="user__options">
                    <div class="user__options-title">
                        <p>Convertir en:</p>
                    </div>
                   <div class="user__buttons">
                       <AppButton v-for="rol in otherRoles(user.rol)" @click="changeUser(index, user.id, rol)" :key="rol" inline>{{ rol }}</AppButton>
                   </div>
                </div>
            </div>
        </template>
    </div>
</div>
</template>

<style scoped>
.user {
    padding: 1rem;
    border-radius: 0.5rem;
    background: #eee;
    display: flex;
    align-items: center;
}
.user__content {
    flex: 1;
}
.user:not(:last-child) {
    margin-bottom: 0.5rem;
}
</style>

<script>

import routes from "../routes/routes"
import UsuarioBlock from "../components/UsuarioBlock";
import AppButton from "../components/AppButton";

export default {
    name: "AdministratorUsersPage",
    components: {AppButton, UsuarioBlock},
    data() {
        return {
            list: [],
            routes
        }
    },
    methods: {
        isNotCurrentUser(userId) {
            return this.$store.state.userId != userId;
        },
        otherRoles(rol) {
            const newArray = [...routes.roles];
            newArray.splice(newArray.indexOf(rol), 1);
            return newArray;
        },
        async load() {
            const { data } = await this.$axios.get('administrador/usuarios');
            this.list = data;
        },
        changeUser(index, userId, rol) {
            this.$axios.put('administrador/usuarios', {
                usuarioId: userId,
                rol
            }).then(() => {
                const newArray = [...this.list];
                newArray[index].rol = rol;
                this.list = newArray;
            });
        }
    },
    created() {
        this.load();
    }
}
</script>

