<template>
<div class="app-form-selector app-form-input" :class="{'focus': focus, 'label': label, 'read': readOnly, 'placeholder': value === null, 'error': error}">
    <label class="app-form-input__name" v-if="label">{{ label }}</label>
    <button class="app-form-selector__option app-form-input__value" @click.prevent="toggle">
        <p>{{ value !== null ? displayName(value) : placeholder }}</p>
        <p class="app-form-selector__icon"><span><i class="fas fa-chevron-down"></i></span></p>
    </button>
    <div class="app-form-input__error" v-if="error">
        <p><i class="fab fa-fort-awesome-alt"></i> {{ error }}</p>
    </div>
    <transition name="slide">
        <div class="app-form-selector__list" v-if="focus">
            <ul>
                <li v-for="item in list" :key="item[valueType]">
                    <button class="app-form-selector__item" @click="() => select(item[valueType])" :class="{active: item[valueType] === value}" >
                        {{ item[keyType] }}
                    </button>
                </li>
            </ul>
        </div>
    </transition>
</div>
</template>

<script>
export default {
    name: "AppFormSelector",
    computed: {
        error() {
            if (this.errors !== null) {
                return this.errors[this.name] || undefined;
            }
            return undefined;
        }
    },
    props: {
        label: String,
        name: {
            type: String,
            default: 'General'
        },
        errors: {
            type: Object,
            default() {
                return null;
            }
        },
        placeholder: String,
        extraType: {
            type: String,
            default: 'input'
        },
        keyType: {
            default: 'display'
        },
        valueType: {
            default: 'value'
        },
        value: {
            default: null
        },
        readOnly: {
            type: Boolean,
            default: false
        },
        type: {
            type: String,
            default: 'text'
        },
        list: Array
    },
    data() {
        return {
            focus: false
        }
    },
    methods: {
        displayName(value) {
            for (const item of this.list) {
                if (item[this.valueType] === value) {
                    return item[this.keyType];
                }
            }
            return 'NaN';
        },
        toggle() {
            this.focus = !this.focus;
        },
        select(newValue) {
            this.$emit('input', newValue);
            this.focus = false;
        }
    },
    created() {
        document.addEventListener('click', (event) => {
            if (this.focus && this.$el && event.target && event.target.offsetParent !== this.$el) {
                this.focus = false;
            }
        })
    }
}
</script>

<style scoped>

.app-form-selector {
    position: relative;
}

.app-form-selector__list {
    position: absolute;
    margin-top: 0.1rem;
    background: white;
    border: 1px solid #1976DB;
    border-radius: 0.5rem;
    z-index: 1;
    width: 100%;
}

.app-form-selector__list li:first-child button {
    border-top-left-radius: 0.5rem;
    border-top-right-radius: 0.5rem;
}


.app-form-selector__list li:last-child button {
    border-bottom-left-radius: 0.5rem;
    border-bottom-right-radius: 0.5rem;
}

.app-form-selector__list li:not(:last-child) {
    border-bottom: 1px solid #ddd;
}

.app-form-selector__list ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.app-form-selector__list button {
    display: block;
    width: 100%;
    padding: 0.8rem;
    text-align: left;
    border: none;
    background: white;
    cursor: pointer;
    transition: 0.4s;
}

.app-form-selector__item.active {
    background: #1976DB;
    color: whitesmoke;
    cursor: default;
}

.app-form-selector__list button:not(.active):hover {
    background: #efefef;
}

.app-form-selector.placeholder p {
    color: #777;
}
.app-form-selector.focus p {
    color: #1976DB;
}
.app-form-selector__option {

    cursor: pointer;
    text-align: left;
    background: white;
    display: flex !important;
    align-items: center;
    justify-content: space-between;
}
.app-form-selector__option p {
    margin: 0;
    transition: 0.5s;
}
.app-form-selector__icon {
    font-size: 1.1rem;
    padding-right: 0.5rem;
    transition: 0.5s;
    color: #ccc;
}
.slide-enter-active {
    -moz-transition-duration: 0.3s;
    -webkit-transition-duration: 0.3s;
    -o-transition-duration: 0.3s;
    transition-duration: 0.3s;
    -moz-transition-timing-function: ease-in;
    -webkit-transition-timing-function: ease-in;
    -o-transition-timing-function: ease-in;
    transition-timing-function: ease-in;
}

.slide-leave-active {
    -moz-transition-duration: 0.3s;
    -webkit-transition-duration: 0.3s;
    -o-transition-duration: 0.3s;
    transition-duration: 0.3s;
    -moz-transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
    -webkit-transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
    -o-transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
    transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
}

.slide-enter-to, .slide-leave {
    max-height: 100px;
    overflow: hidden;
}

.slide-enter, .slide-leave-to {
    overflow: hidden;
    max-height: 0;
}
</style>