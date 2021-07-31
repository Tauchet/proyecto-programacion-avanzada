<template>
<div id="not-found-page">
    <div class="container">
        <p class="icon"><i class="fas fa-fire-extinguisher fa-10x"></i></p>
        <h1>¡Esta página no se encuentra!</h1>
        <p>Nuestro mundo es tan grande que hasta el mismisimo <strong>Loki</strong> es capaz de escaparse de un avión.</p>
    </div>
</div>
</template>

<script>
export default {
    name: "NotFoundPage",
    components: {},
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
.icon {
    font-size: 2rem;
}
#not-found-page {
    margin-top: 1rem;
    text-align: center;
}
</style>