<template>
    <section class="form-section" :class="{error: error}">
        <div class="form-section__title">
            <AppTitle><slot name="title"/></AppTitle>
            <p class="form-section__title" v-if="error"><i class="fab fa-fort-awesome-alt"></i> {{ error }}</p>
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
    components: {AppTitle},
    computed: {
        error() {
            if (this.errors !== null) {
                return this.errors[this.name] || undefined;
            }
            return undefined;
        }
    },
    props: {
        name: {
            type: String,
            default: 'General'
        },
        errors: {
            type: Object,
            default() {
                return null;
            }
        }
    }
}
</script>

<style scoped>
.form-section:not(:first-child) {
    margin: 2rem 0;
}

.form-section__content {
    margin-top: 1rem;
}

.form-section.error .form-section__title {
    color: #ff5252;
}

</style>