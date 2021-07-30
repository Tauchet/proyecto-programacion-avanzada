<template>
<div class="app-title" :class="{space: space}">
    <h2><slot/></h2>
</div>
</template>

<script>
export default {
    name: "AppTitle",
    props: {
        space: {
            type: Boolean,
            default: false
        }
    }
}
</script>

<style scoped>
.app-title {
    border-bottom: 1px solid #DFDFDF;
}

.app-title.space {
    margin-bottom: 1rem;
}
</style>