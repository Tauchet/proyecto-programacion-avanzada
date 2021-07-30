<template>
<div class="app-alert" :class="[type]">
    <div class="app-alert__icon">
        <p><i :class="typeIcons[type]"></i></p>
    </div>
    <div class="app-alert__content">
        <slot/>
    </div>
</div>
</template>

<style scoped>
.app-alert {
    display: flex;
    border: 1px solid #ff5252;
    border-radius: 0.5rem;
    color: #ff5252 !important;
    caret-color: #ff5252 !important;
    margin: 1rem 0;
    padding: 1rem;
    align-items: center;
    gap: 1rem;
}
.app-alert.success {
    border-color: #42b983;
    color: #42b983 !important;
    caret-color: #42b983 !important;
}
.app-alert p {
    margin: 0;
}
.app-alert__icon {
    font-size: 1.2rem;
}
</style>

<script>

const TYPES_ICONS = {
    'danger': 'fas fa-exclamation-circle',
    'success': 'fas fa-info-circle'
}

export default {
    name: "AppAlert",
    props: {
        type: {
            type: String,
            default: 'danger'
        }
    },
    data() {
        return {
            typeIcons: TYPES_ICONS
        }
    }
}
</script>
