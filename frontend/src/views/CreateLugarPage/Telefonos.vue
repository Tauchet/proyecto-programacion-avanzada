<template>
    <div class="telefonos">
        <div class="telefonos__header">
            <AppFormInput v-model.number="form" type="number" label="NÚMERO DE TELEFONO" />
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
            form: 0
        }
    },
    methods: {
        add() {
            if (!this.value.includes(this.form)) {
                const list = [...this.value];
                list.push(this.form);
                this.form = "";
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