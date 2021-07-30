<template>
    <div class="horarios">
        <div class="horarios__header">
            <div class="box-center">
                <AppButton @click="add">CREAR HORARIO</AppButton>
            </div>
        </div>
        <div class="horarios__list">
            <template v-for="(horario, index) in value">
                <AppCard :key="horario" @remove="() => remove(index)" remove>
                    <div class="horario">
                        <div class="days">
                            <div class="day" :class="{enable: horario[day.key]}" v-for="day in days" :key="day.key">
                                <button @click.prevent="() => onChangeState(index, day.key)">{{ day.display }}</button>
                            </div>
                        </div>
                        <AppFormInput type="number" v-model.number="horario.inicioHoras" label="Horas Inicial" />
                        <AppFormInput type="number" v-model.number="horario.inicioMinutos" label="Minutos Inicial" />
                        <AppFormInput type="number" v-model.number="horario.finalHoras" label="Horas Final" />
                        <AppFormInput type="number" v-model.number="horario.finalMinutos" label="Minutos Final" />
                    </div>
                </AppCard>
            </template>
        </div>
    </div>
</template>

<style scoped>
.horarios__list {
    margin-top: 1rem;
}
.days {
    display: flex;
    gap: 0.4rem;
}
.day button {
    font-weight: bold;
    border-radius: 50%;
    border: none;
    background: #ddd;
    width: 2rem;
    height: 2rem;
    cursor: pointer;
    transition: 0.3s;
}
.day button:hover {
    transform: scale(1.05);
}
.day.enable button {
    background: #1976DB;
    color: whitesmoke;
}
.horario {
    display: flex;
    align-items: center;
    gap: 1rem;
}
</style>

<script>

import AppFormInput from "../../components/form/AppFormInput";
const DAYS = [{
    key: 'lunes',
    display: 'L'
}, {
    key: 'martes',
    display: 'M'
}, {
    key: 'miercoles',
    display: 'M'
}, {
    key: 'jueves',
    display: 'J'
}, {
    key: 'viernes',
    display: 'V'
}, {
    key: 'sabado',
    display: 'S'
}, {
    key: 'domingo',
    display: 'D'
}];

import AppButton from "../../components/AppButton";
import AppCard from "../../components/AppCard";

export default {
    name: "Horarios",
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
            days: DAYS
        }
    },
    components: {
        AppFormInput,
        AppCard,
        AppButton
    },
    methods: {
        onChangeState(index, key) {
            const newArray = [...this.value];
            newArray[index][key] = !newArray[index][key];
            this.$emit('input', newArray);
        },
        add() {
            const newArray = [...this.value];
            newArray.push({
                inicioHoras: 0,
                inicioMinutos: 0,
                finalHoras: 0,
                finalMinutos: 0,
                lunes: false,
                martes: false,
                miercoles: false,
                jueves: false,
                viernes: false,
                sabado: false,
                domingo: false
            });
            this.$emit('input', newArray);
        },
        remove(index) {
            const newArray = [...this.value];
            newArray.splice(index, 1);
            this.$emit("input", newArray);
        }
    }
}
</script>

