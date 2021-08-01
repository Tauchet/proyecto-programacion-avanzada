<template>
    <button @click.prevent="emitEvent" class="app-button" :class="[type, {big: big, small: small, inline: inline}]">
        <slot/>
    </button>
</template>

<script>
export default {
    name: "AppButton",
    props: {
        big: {
            type: Boolean,
            default: false
        },
        small: {
            type: Boolean,
            default: false
        },
        inline: {
            type: Boolean,
            default: false
        },
        type: {
            type: String,
            default: 'info'
        },
        redirect: {
            type: String,
            default: null
        }
    },
    methods: {
        emitEvent(event) {

            if (this.redirect !== null) {
                this.$router.push(this.redirect);
                return;
            }

            this.$emit(event.type, event);
        }
    }
}
</script>

<style lang="scss" scoped>
.app-button {
    background: #1976DB;
    border: none;
    color: white;
    cursor: pointer;
    font-weight: bold;
    border-radius: 0.3rem;
    padding: 0.6rem 1.2rem;
    text-transform: uppercase;
}
.app-button.big {
    padding: 1rem 1.5rem;
}

.app-button.small {
    padding: 0.4rem 1rem;
    font-size: 0.95rem;
}

.app-button.success {
    background: #59B55D;
}
.app-button.danger {
    background: #E96565;
}
.app-button.purple {
    background: #9B59B6;
}
.app-button.orange {
    background: #f39c12;
}
.app-button.inline:not(:last-child) {
    margin-right: 0.3rem;
}
</style>