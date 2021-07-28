<template>
  <div id="app-create-lugares-page">
    <h3>Crear Lugares {{ form }}</h3>
    <div id="map"></div>
    <form action="#" @submit.prevent="execute">
        
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" v-model="form.nombre">

        <label for="descripcion">Descripción</label>
        <textarea name="descripcion" id="descripcion" cols="30" rows="10" v-model="form.descripcion"></textarea>

        <CiudadInput v-model="form.ciudadId" />
        <CategoriaInput v-model="form.categoriaId" />
        
        <div class="telefonos">
          <label for="#">Número telefonico</label>
          <input type="number" v-model="phoneNumber">
          <button type="button" @click="addPhone">Agregar</button>
          <div v-for="(telefono, index) in form.telefonos" :key="telefono">
            <p>{{ telefono }}</p>
            <button type="button" @click='() => removePhone(index)'>Eliminar Telefono</button>
          </div>
        </div>

        <div class="horarios">
          <button @click="addHorario">Crear Horario</button>
          <div class="horario" v-for="(horario, index) in form.horarios" :key="index">
            <div class="horario-inicio">
              <label for="#">Hora inicial</label>
              <input @input="(event) => changeHorario(index, 'inicioHoras', event.target.value)" type="number" :value="horario.inicioHoras">
              <input @input="(event) => changeHorario(index, 'inicioMinutos', event.target.value)" type="number" :value="horario.inicioMinutos">
            </div>
            <div class="horario-final">
              <label for="#">Hora final</label>
              <input @input="(event) => changeHorario(index, 'finalHoras', event.target.value)" type="number" :value="horario.finalHoras">
              <input @input="(event) => changeHorario(index, 'finalMinutos', event.target.value)" type="number" :value="horario.finalMinutos">
            </div>
            <div class="horario-dias">
              <button @click="(event) => changeHorario(index, 'lunes', !horario.lunes)">Lunes: {{ horario.lunes }}</button>
              <button @click="(event) => changeHorario(index, 'martes', !horario.martes)">Martes: {{ horario.martes }}</button>
              <button @click="(event) => changeHorario(index, 'miercoles', !horario.miercoles)">Miércoles: {{ horario.miercoles }}</button>
              <button @click="(event) => changeHorario(index, 'jueves', !horario.jueves)">Jueves: {{ horario.jueves }}</button>
              <button @click="(event) => changeHorario(index, 'viernes', !horario.viernes)">Viernes: {{ horario.viernes }}</button>
              <button @click="(event) => changeHorario(index, 'sabado', !horario.sabado)">Sábado: {{ horario.sabado }}</button>
              <button @click="(event) => changeHorario(index, 'domingo', !horario.domingo)">Domingo: {{ horario.domingo }}</button>
            </div>
            <div class="horario-opcion">
              <button @click="(event) => removeHorario(index)">Eliminar horario</button>
            </div>
          </div>
        </div>

        <button type="submit">Crear Lugar</button>

    </form>
  </div>
</template>

<script>
import mapboxgl from "mapbox-gl";

import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";
import CiudadInput from '../../components/form/CiudadInput.vue';
import CategoriaInput from '../../components/form/CategoriaInput.vue';

export default {
  components: { CiudadInput, CategoriaInput },
  data() {
    return {
      form: {
        nombre: "",
        categoria: "",
        longitud: -75.67207158369241,
        latitud: 4.537165268535674,
        categoriaId: -1,
        usuarioId: 44,
        ciudadId: -1,
        telefonos: [],
        horarios: []
      },
      phoneNumber: 0,
      access_token:
        "pk.eyJ1IjoidGF1Y2hldCIsImEiOiJja3Jtd3h0d2M2MGdwMnBwZGRyNGFlaWptIn0.vFy_VTRRTWpDT5tvMwb6Ng",
      map: {},
    };
  },
  mounted() {
    this.createMap();
  },
  methods: {
    execute() {
      this.$axios.post('lugares', this.form)
                .then((response) => {
                    console.log("Respuesta", response);
                })
                .catch(({ response }) => {
                    
                    console.log("Error", response);

                });
    },
    removeHorario(index) {
      const horarios = [...this.form.horarios];
      horarios.splice(index, 1);
      this.form.horarios = horarios;
    },
    addHorario() {
      this.form.horarios.push({
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
    },
    changeHorario(index, key, value) {
      const horario = {...this.form.horarios[index]};
      horario[key] = value;
      this.$set(this.form.horarios, index, horario);
    },
    addPhone() {
      if (!this.form.telefonos.includes(this.phoneNumber)) {
        this.form.telefonos.push(this.phoneNumber);
      }
    },
    removePhone(index) {
      var newArray = [...this.form.telefonos];
      newArray.splice(index, 1);
      this.form.telefonos = newArray;
    },
    async createMap() {
      mapboxgl.accessToken = this.access_token;

      const INITIAL_LOCATION = [this.form.longitud, this.form.latitud];

      const map = new mapboxgl.Map({
        container: "map",
        style: "mapbox://styles/mapbox/streets-v11",
        center: INITIAL_LOCATION,
        zoom: 12,
      });

      const marker = new mapboxgl.Marker({
        draggable: true,
      })
        .setLngLat(INITIAL_LOCATION)
        .addTo(map);

      marker.on("dragend", () => {
        var location = marker.getLngLat();
        this.form.longitud = location.lng;
        this.form.latitud = location.lat;
      });
    },
  },
};
</script>