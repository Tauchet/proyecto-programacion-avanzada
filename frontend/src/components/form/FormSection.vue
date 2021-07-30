<template>
    <section class="form-section">
        <div class="form-section__title">
            <AppTitle><slot name="title"/></AppTitle>
        </div>
        <div class="form-section__content">
            <slot name="content"/>
        </div>
    </section>
</template>

<script>
import AppTitle from "../AppTitle";
export default {
    name: "FormSection",
    components: {AppTitle}
}
</script>

<style scoped>
.form-section:not(:first-child) {
    margin: 2rem 0;
}

.form-section__content {
    margin-top: 1rem;
}
</style>