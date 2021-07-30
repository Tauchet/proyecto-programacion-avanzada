<template>
    <div class="app-form-input" :class="{'focus': focus, 'label': label, 'read': readOnly}">
        <label class="app-form-input__name" v-if="label">{{ label }}</label>
        <template v-if="extraType === 'input'">
            <input class="app-form-input__value"
                   :value="value"
                   :type="type"
                   @keyup.prevent="onInput"
                   @focus="onChangeFocus"
                   @blur="onChangeFocus">
        </template>
        <template v-else>
            <textarea @input.capture="onInput"
                      class="app-form-input__value"
                      name="" id=""
                      cols="30"
                      rows="3"
                      v-model="value"
                      @focus="onChangeFocus"
                      @blur="onChangeFocus"></textarea>
        </template>
    </div>
</template>

<script>
export default {
    name: "AppFormInput",
    props: {
        label: String,
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
        onChangeFocus() {

            if (this.readOnly) {
                return;
            }

            this.focus = !this.focus;

        }
    }
}
</script>
