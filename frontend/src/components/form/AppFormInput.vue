<template>
    <div class="app-form-input" :class="{'focus': focus, 'label': label, 'read': readOnly, 'error': error}">
        <label class="app-form-input__name" v-if="label">{{ label }}</label>
        <template v-if="extraType === 'input'">
            <input class="app-form-input__value"
                   :value="value"
                   :type="type"
                   @keyup.prevent="onInput"
                   @focus="onChangeFocus"
                   @blur="onChangeFocus"
                   v-on:keyup.enter="onSubmit"
                   :disabled="readOnly"
            >
        </template>
        <template v-else>
            <textarea @input.capture="onInput"
                      class="app-form-input__value"
                      name="" id=""
                      cols="30"
                      rows="3"
                      v-model="value"
                      @focus="onChangeFocus"
                      @blur="onChangeFocus"
                      v-on:keyup.enter="onSubmit"
                      :disabled="readOnly"></textarea>
        </template>
        <div class="app-form-input__error" v-if="error">
            <p><i class="fab fa-fort-awesome-alt"></i> {{ error }}</p>
        </div>
    </div>
</template>


<script>
export default {
    name: "AppFormInput",
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
        extraType: {
            type: String,
            default: 'input'
        },
        value: {
            default: ''
        },
        readOnly: {
            type: Boolean,
            default: false
        },
        type: {
            type: String,
            default: 'text'
        }
    },
    data() {
        return {
            focus: false
        }
    },
    methods: {
        onInput(event) {

            if (this.readOnly) {
                return;
            }

            this.$emit("input", event.target.value);

        },
        onSubmit() {
            this.$emit("enter");
        },
        onChangeFocus() {

            if (this.readOnly) {
                return;
            }

            this.focus = !this.focus;

        }
    }
}
</script>
