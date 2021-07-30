<template>
    <div id="create-lugar-page">
        <div class="container">
            <div id="map"></div>
            <form action="#">
                <section class="form-section">
                    <div class="form-section__title">
                        <AppTitle>Información General</AppTitle>
                    </div>
                    <div class="form-section__content">
                        <AppFormInput v-model="form.nombre" label="Nombre" />
                        <AppFormInput v-model="form.descripcion" extra-type="textarea" label="Descripción" />
                        <AppMultipleLine columns="2">
                            <AppCiudadInput v-model="form.ciudadId"/>
                            <AppCategoriaInput v-model="form.categoriaId"/>
                        </AppMultipleLine>
                    </div>
                </section>
                <section class="form-section">
                    <div class="form-section__title">
                        <AppTitle>Telefonos</AppTitle>
                    </div>
                    <div class="form-section__content">
                        <Telefonos v-model="form.telefonos"/>
                    </div>
                </section>
                <section class="form-section">
                    <div class="form-section__title">
                        <AppTitle>Horarios</AppTitle>
                    </div>
                    <div class="form-section__content">
                        <Horarios v-model="form.horarios"/>
                    </div>
                </section>
                <section class="form-section">
                    <div class="form-section__title">
                        <AppTitle>Información Extra</AppTitle>
                    </div>
                    <div class="form-section__content">
                        <AppMultipleLine columns="2">
                            <AppFormInput v-model="form.latitud" label="Latitud" read-only/>
                            <AppFormInput v-model="form.longitud" label="Longitud" read-only/>
                        </AppMultipleLine>
                    </div>
                </section>
                <AppAlert v-if="error">
                    <p>{{error}}</p>
                </AppAlert>
                <div class="box-center">
                    <AppButton @click="sendForm" type="success" big>CREAR LUGAR</AppButton>
                </div>
            </form>
        </div>
    </div>
</template>

<script>

import mapboxgl from "mapbox-gl";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";

import AppTitle from "../components/AppTitle";
import AppFormInput from "../components/form/AppFormInput";
import AppMultipleLine from "../components/AppMultipleLine";
import Telefonos from "./CreateLugarPage/Telefonos";
import Horarios from "./CreateLugarPage/Horarios";
import AppButton from "../components/AppButton";
import AppAlert from "../components/AppAlert";
import AppCiudadInput from "../components/form/AppCiudadInput";
import AppCategoriaInput from "../components/form/AppCategoriaInput";

export default {
    name: "CreateLugarPage",
    components: {
        AppCategoriaInput,
        AppCiudadInput, AppAlert, AppButton, Horarios, Telefonos, AppMultipleLine, AppFormInput, AppTitle},
    data() {
        return {
            form: {
                longitud: -75.67207158369241,
                latitud: 4.537165268535674,
                nombre: '',
                descripcion: '',
                telefonos: [],
                horarios: []
            },
            error: null
        }
    },
    methods: {
        sendForm() {

            // Reiniciar formulario
            this.error = null;

            const request = this.$axios.post('lugares', this.form);
            request.then(({ data }) => {

                // NOTAS: Reederigir a la página de notas.
                console.log("Resultado", data);

            });

            request.catch(({ response }) => {

               const { data } = response;

               // Problemas de validación
               if (data.status === 1000) {
                   this.error = data.message;
               }

            });

        },
        async createMap() {
            mapboxgl.accessToken = "pk.eyJ1IjoidGF1Y2hldCIsImEiOiJja3Jtd3h0d2M2MGdwMnBwZGRyNGFlaWptIn0.vFy_VTRRTWpDT5tvMwb6Ng";
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
                const location = marker.getLngLat();
                this.form.longitud = location.lng;
                this.form.latitud = location.lat;
            });
        }
    },
    mounted() {
        this.createMap();
    }
}
</script>

<style lang="scss" scoped>
#create-lugar-page {

    .container {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 4rem;
    }

    #map {
        max-height: 100%;
        border-radius: 2rem;
    }

}
</style>