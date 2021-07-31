<template>
    <div id="create-lugar-page">
        <div class="container">
            <div id="map"></div>
            <div>
                <section class="form-section">
                    <div class="form-section__title">
                        <AppTitle>Información General</AppTitle>
                    </div>
                    <div class="form-section__content">
                        <AppFormInput :errors="errors" name="nombre" v-model="form.nombre" label="Nombre" />
                        <AppFormInput :errors="errors" name="descripcion" v-model="form.descripcion" extra-type="textarea" label="Descripción" />
                        <AppMultipleLine columns="2">
                            <AppCiudadInput :errors="errors" name="ciudadId" v-model="form.ciudadId"/>
                            <AppCategoriaInput :errors="errors" name="categoriaId" v-model="form.categoriaId"/>
                        </AppMultipleLine>
                    </div>
                </section>
                <section class="form-section">
                    <FormSection :errors="errors" name="telefonos">
                        <template slot="title">Telefonos</template>
                        <Telefonos slot="content" :form-errors="errors" v-model="form.telefonos"/>
                    </FormSection>
                </section>
                <section class="form-section">
                    <FormSection :errors="errors" name="horarios">
                        <template slot="title">Horarios</template>
                        <Horarios slot="content" :form-errors="errors"  v-model="form.horarios"/>
                    </FormSection>
                </section>
                <section class="form-section">
                    <div class="form-section__title">
                        <AppTitle>Información Extra</AppTitle>
                    </div>
                    <div class="form-section__content">
                        <AppMultipleLine columns="2">
                            <AppFormInput v-model="form.longitud" label="Longitud" read-only/>
                            <AppFormInput v-model="form.latitud" label="Latitud" read-only/>
                        </AppMultipleLine>
                    </div>
                </section>
                <AppAlert v-if="error">
                    <p>{{error}}</p>
                </AppAlert>
                <div class="box-center">
                    <AppButton @click="sendForm" type="success" big>CREAR LUGAR</AppButton>
                </div>
            </div>
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
import ValidationUtil from "../libs/ValidationUtil";
import FormSection from "../components/form/FormSection";

export default {
    name: "CreateLugarPage",
    components: {
        FormSection,
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
            error: null,
            errors: {}
        }
    },
    methods: {
        sendForm() {

            // Reiniciar formulario
            this.error = null;

            // Validaciones previas --- inicio
            this.errors = {};
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'nombre');
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'descripcion');
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'ciudadId');
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'categoriaId');
            ValidationUtil.validateIfExistsLength(this.errors, this.form, 'telefonos', 1, 'telefono');
            ValidationUtil.validateIfExistsLength(this.errors, this.form, 'horarios', 1, 'horario');

            // Validación de horarios
            if (!this.errors['horarios']) {
                for (var horario of this.form.horarios) {
                    if (!(horario.lunes || horario.martes || horario.miercoles || horario.jueves || horario.viernes || horario.sabado || horario.domingo)) {
                        this.errors['horarios'] = "¡Hay un horario en el que no hay dias seleccionados!";
                        break;
                    } else if (horario.inicioHoras === horario.finalHoras && horario.inicioMinutos === horario.finalMinutos) {
                        this.errors['horarios'] = "¡Hay un horario sin ingresar los tiempos respectivos!";
                        break;
                    }
                }
            }

            if (Object.keys(this.errors).length > 0) {
                return;
            }
            // Validaciones previas --- final



            const request = this.$axios.post('lugares', this.form);
            request.then(() => {
                this.$router.push('/mis-lugares?redirect=¡El lugar ha sido creado correctamente! Estaremos verificandolo lo más pronto posible...');
            });

            request.catch(({ response }) => {

               const { data } = response;

               // Problemas de validación
               if (data.status === 2000) {
                   this.error = data.message;
               }

            });

        },
        async createMap() {
            mapboxgl.accessToken = "pk.eyJ1IjoidGF1Y2hldCIsImEiOiJja3Jtd3h0d2M2MGdwMnBwZGRyNGFlaWptIn0.vFy_VTRRTWpDT5tvMwb6Ng";
            const map = new mapboxgl.Map({
                container: "map",
                style: "mapbox://styles/mapbox/streets-v11",
                center: [-75.27986747330804,4.966951000736728],
                zoom: 3,
            });
            map.on('click', ({ lngLat }) => {
                this.marker.setLngLat([lngLat.lng, lngLat.lat]);
                this.form.longitud = lngLat.lng;
                this.form.latitud = lngLat.lat;
            });
            map.on('load', function() {
                if ("geolocation" in navigator) {
                    navigator.geolocation.getCurrentPosition(position => {
                        map.flyTo({
                            center: [position.coords.longitude, position.coords.latitude],
                            esential: true,
                            zoom: 13
                        });
                    });
                }
            });
            map.addControl(new mapboxgl.GeolocateControl({
                positionOptions: {
                    enableHighAccuracy: true
                },
                trackUserLocation: true
            }));

            map.addControl(new mapboxgl.NavigationControl());
            this.marker = new mapboxgl.Marker({})
                .setLngLat([this.form.longitud, this.form.latitud])
                .addTo(map);
        }
    },
    mounted() {
        this.createMap();
    }
}
</script>

<style lang="scss" scoped>
#create-lugar-page {

    .form-section:not(:first-child) {
        margin: 2rem 0;
    }

    .form-section__error {
        color: #ff5252;
    }

    .form-section__content {
        margin-top: 1rem;
    }

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