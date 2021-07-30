<template>
<div class="lugar-lista">
    <div class="lugar" v-for="lugar in list" :key="lugar.id"
         :class="[{clicked: lugar.estado === 'APROBADO'}, lugar.estado.toLowerCase()]"
         @click="() => open(lugar)">
        <div class="lugar-info">
            <h3>{{ lugar.nombre }}</h3>
            <p class="lugar-label">{{ lugar.categoria.nombre ? lugar.categoria.nombre : lugar.categoria }}</p>
        </div>
        <template v-if="lugar.estado === 'APROBADO'">
            <div class="lugar-state">
                <p v-if="lugar.comentariosSinRespuesta > 0">{{ lugar.comentariosSinRespuesta }} COMENTARIOS SIN RESPUESTA</p>
            </div>
            <div class="lugar-date">
                <p class="lugar-label">APROBADO DESDE</p>
                <p>{{lugar.fechaAprobacion}}</p>
            </div>
            <div class="lugar-footer">
                <p class="lugar-icon"><i class="fas fa-chevron-right fa-3x"></i></p>
            </div>
        </template>
        <template v-else-if="lugar.estado === 'ESPERANDO'">
            <div class="lugar-state">
                <p>ESPERANDO VERIFICACIÓN</p>
            </div>
            <div class="lugar-date">
                <p class="lugar-label">ESPERANDO DESDE</p>
                <p>{{lugar.fechaCreacion}}</p>
            </div>
        </template>
        <template v-else-if="lugar.estado === 'DESAPROBADO'">
            <div class="lugar-state">
                <p>DESAPROBADO</p>
            </div>
            <div class="lugar-date">
                <p class="lugar-label">FECHA DE DESAPROBACIÓN</p>
                <p>{{lugar.fechaAprobacion}}</p>
            </div>
        </template>
    </div>
</div>
</template>

<style scoped>

.lugar-lista {
    margin: 1rem 0;
}

.lugar {
    font-weight: bold;
    padding: 1rem;
    background: #F6F6F6;
    border-radius: 0.5rem;
    border: 1px solid transparent;
    display: flex;
    align-items: center;
    gap: 1rem;
    transition: 0.4s;
}

.lugar.esperando {
    border-color: #88C1FF;
    background: #FBFDFF;
    color: #5E9FE1;
}

.lugar.esperando .lugar-state p {
    color: #559CE8;
    background: #C1DFFF;
    padding: 0.7rem 0.8rem;
    border-radius: 0.5rem;
}

.lugar.desaprobado {
    border-color: #FAAB00;
    background: #FFFDF9;
    color: #E19D5E;
}

.lugar.desaprobado .lugar-state p {
    color: #E19D5E;
    background: #FFEDC1;
    padding: 0.7rem 0.8rem;
    border-radius: 0.5rem;
}

.lugar.clicked {
    cursor: pointer;
}

.lugar.clicked:hover {
    transform: scale(1.01);
}

.lugar-date {
    text-align: right;
}

.lugar-state {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
}

.lugar-icon {
    color: rgba(0,0,0,0.2);
}

.lugar h3 {
    font-size: 1.5rem;
}

.lugar-label {
    color: rgba(0,0,0,0.6);
    text-transform: uppercase;
}

.lugar p, .lugar h3 {
    margin: 0;
}
</style>

<script>
export default {
    name: "LugarLista",
    methods: {
        open(lugar) {

            if (lugar.estado !== 'APROBADO') {
                return;
            }

            this.$router.push('/lugares/' + lugar.id);
        }
    },
    props: {
        list: Array
    }
}
</script>

