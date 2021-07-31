<template>
<div class="app-error">
    <div class="app-error-content">
        <div class="app-error__logo">
            <p><i class="fas fa-map-marked-alt fa-8x"></i></p>
        </div>
        <div class="app-error__message">
            <h1>{{ message }}</h1>
        </div>
    </div>
</div>
</template>

<style scoped>
.app-error {
    margin-top: 5rem;
    display: flex;
    align-items: center;
    justify-content: center;
}
.app-error-content {
    text-align: center;
}
.app-error__message {
    margin-top: 3rem;
    font-weight: bold;
}
</style>

<script>
export default {
    name: "AppError",
    props: {
        message: String
    }
}
</script>

