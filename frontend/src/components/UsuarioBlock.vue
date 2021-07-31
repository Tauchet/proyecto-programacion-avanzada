<template>
<div class="usuario-block" :class="{divider: divider}">
    <div class="usuario-block__avatar">
        <img :src="usuario.avatarUrl" width="64px" height="64px" alt="">
    </div>
    <div class="usuario-block__title">
        <h3>{{ usuario.nombre }}</h3>
        <p>@{{ usuario.username }}</p>
    </div>
    <slot/>
</div>
</template>

<style scoped>
h3, p {
    margin: 0;
}
.usuario-block {
    display: flex;
    align-items: center;
    gap: 0.7rem;
}
.usuario-block__avatar {
    border-radius: 50%;
    background: #ddd;
}
.usuario-block__avatar img {
    border-radius: 50%;
    padding: 0.4rem;
}

.usuario-block__title {
    flex: 1;
}

.usuario-block__title p {
    color: #979797;
    font-weight: bold;
}


.usuario-block.divider {
    padding-bottom: 1rem;
    border-bottom: 1px solid #DFDFDF;
}
</style>

<script>
export default {
    name: "UsuarioBlock",
    props: {
        usuario: {
            type: Object
        },
        divider: {
            type: Boolean,
            default: false
        }
    }
}
</script>
