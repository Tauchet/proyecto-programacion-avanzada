<template>
    <div class="app-card">
        <div class="app-card-content">
            <slot/>
        </div>
        <div class="app-card-remove-content" v-if="remove">
            <button @click.prevent="onRemove" class="app-card-remove"><i class="fas fa-trash"></i></button>
        </div>
    </div>
</template>

<script>
export default {
    name: "AppCard",
    props: {
        remove: {
            type: Boolean,
            default: false
        }
    },
    methods: {
        onRemove(event) {
            this.$emit('remove', event);
        }
    }
}
</script>

<style lang="scss" scoped>

.app-card {

    gap: 1rem;
    display: flex;
    align-items: center;
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    background: #eee;

    &:not(:last-child) {
        margin-bottom: 0.5rem;
    }

    .app-card-content {
        flex: 1;
    }

    .app-card-remove {
        border-radius: 0.5rem;
        border: none;
        background: #ddd;
        padding: 0.5rem 0.8rem;
        color: #666;
        font-size: 1.2rem;
        cursor: pointer;
        transition: 0.4s;
    }

    .app-card-remove:hover {
        transform: scale(1.05);
        color: #333;
    }

}

</style>