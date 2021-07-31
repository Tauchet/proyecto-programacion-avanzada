<template>
    <div class="telefonos">
        <div class="telefonos__header">
            <AppFormInput @enter="add" :errors="errors" name="texto" v-model.number="form.texto" type="number" label="NÚMERO DE TELEFONO" />
            <div class="box-center">
                <AppButton @click="add">AGREGAR</AppButton>
            </div>
        </div>
        <div class="telefonos__list">
            <template v-for="(phone, index) in value">
                <AppCard :key="phone" @remove="() => remove(index)" remove>
                    {{phone}}
                </AppCard>
            </template>
        </div>
    </div>
</template>

<script>

import AppButton from "../../components/AppButton";
import AppFormInput from "../../components/form/AppFormInput";
import AppCard from "../../components/AppCard";
import ValidationUtil from "../../libs/ValidationUtil";

export default {
    name: "Telefonos",
    components: {AppCard, AppButton, AppFormInput},
    props: {
        value: {
            type: Array,
            default() {
                return []
            }
        }
    },
    data() {
        return {
            form: {
                texto: ''
            },
            errors: {}
        }
    },
    methods: {
        add() {

            // Validaciones previas --- inicio
            this.errors = {};
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'texto');
            ValidationUtil.validateIfDigits(this.errors, this.form, 'texto', 7, 10);
            if (Object.keys(this.errors).length > 0) {
                return;
            }
            // Validaciones previas --- final

            if (!this.value.includes(this.form.texto)) {
                const list = [...this.value];
                list.push(this.form.texto);
                this.form.texto = "";
                this.$emit("input", list);
            }

        },
        remove(index) {
            const newArray = [...this.value];
            newArray.splice(index, 1);
            this.$emit("input", newArray);
        }
    }
}
</script>

<style scoped>
.telefonos__list {
    margin-top: 1rem;
}
</style>