<template>
<div class="auth-page">
    <div class="auth-page__container">
        <div class="auth-page__logo">
            <h1><i class="fas fa-map"></i> Lugares</h1>
        </div>
        <slot/>
    </div>
</div>
</template>

<script>
export default {
    name: "AuthPage"
}
</script>

<style>
h1 {
    margin: 0;
}

.auth-page__logo {
    font-size: 1.3rem;
    text-align: center;
    margin-bottom: 2rem;
}
.auth-page__options {
    margin-top: 2rem;
    text-align: center;
}
.auth-page {
    background: #131A26;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
}
.auth-page__container {
    background: white;
    padding: 3rem;
    border-radius: 3rem;
    width: 30%;
    min-width: 700px;
    margin: auto;
}
</style>