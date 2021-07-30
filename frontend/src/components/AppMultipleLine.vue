<template>
<div class="app-multiple-line" :style="{gridTemplateColumns: 'repeat(' + columns + ', 1fr)'}">
    <slot/>
</div>
</template>

<script>
export default {
    props: {
        columns: {
            default: 1
        }
    },
    name: "AppMultipleLine"
}
</script>

<style scoped>
.app-multiple-line {
    display: grid;
    gap: 1rem;
}
</style>