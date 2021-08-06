<template>
    <div id="home-page">
        <div class="home-page__kms">
            <div class="tags">
                <button class="tag" @click="() => moveDistance(-1)" :class="{active: distanceKM === -1}">TODA</button>
                <button class="tag" @click="() => moveDistance(1)" :class="{active: distanceKM === 1}">1 Km</button>
                <button class="tag" @click="() => moveDistance(2)" :class="{active: distanceKM === 2}">2 Km</button>
                <button class="tag" @click="() => moveDistance(4)" :class="{active: distanceKM === 4}">4 Km</button>
                <button class="tag" @click="() => moveDistance(6)" :class="{active: distanceKM === 6}">6 Km</button>
                <button class="tag" @click="() => moveDistance(8)" :class="{active: distanceKM === 8}">8 Km</button>
                <button class="tag" @click="() => moveDistance(10)" :class="{active: distanceKM === 10}">10 Km</button>
            </div>
            <div class="info" v-if="geoRoute">
                <p>Minutos: {{ geoRoute.minutes }}</p>
                <p>Distancia: {{ geoRoute.km }} KM</p>
            </div>
        </div>
        <GeneralMapbox @map-route="(data) => this.geoRoute = data" @map-load="filterInDistance" ref="map" />
        <div class="home-page__container" id="lugares">
            <div class="container">
                <div class="title-general">
                    <h1><i class="fas fa-globe"></i> EXPLORAR</h1>
                </div>
                <div class="tags">
                    <button class="tag" @click="() => moveCategoria(-1)" :class="{active: categoria === -1}">Todos</button>
                    <button class="tag" v-for="item in categorias" :class="{active: categoria === item.id}" @click="() => moveCategoria(item.id)" :key="item.id">{{ item.nombre }}</button>
                </div>
                <div class="search">
                    <AppFormInput @enter="load" label="Búsqueda" v-model="form" />
                    <div class="search-button">
                        <AppButton @click="load">Buscar</AppButton>
                    </div>
                </div>
                <transition name="fade" mode="out-in">
                    <div class="lugares">
                        <div class="lugar" v-for="lugar in list" :key="lugar.id">
                            <div class="lugar-info">
                                <div class="lugar-title">
                                    <h3>{{ lugar.nombre }}</h3>
                                    <p>{{ lugar.categoria.nombre }}</p>
                                </div>
                                <div class="lugar-date">
                                    <p><strong>Creación:</strong> {{lugar.fechaCreacion}}</p>
                                    <p><strong>Ciudad:</strong> {{lugar.ciudad.nombre}}</p>
                                </div>
                                <div class="lugar-options">
                                    <AppButton inline small @click="() => viewInMap(lugar)">Ver en el Mapa</AppButton>
                                    <AppButton @click="() => openInformation(lugar)" type="purple" inline small>Más Información</AppButton>
                                    <AppButton v-if="isGeoLocation" @click="() => locationMap(lugar)" type="success" inline small>Ver Ruta</AppButton>
                                </div>
                            </div>
                        </div>
                    </div>
                </transition>
            </div>
        </div>

    </div>
</template>

<script>

import AppButton from "../components/AppButton";
import GeneralMapbox from "../components/GeneralMapbox";
import AppFormInput from "../components/form/AppFormInput";
import mapboxgl from "mapbox-gl";
import { distance } from '@turf/turf';

export default {
    name: 'HomePage',
    components: {AppFormInput, GeneralMapbox, AppButton},
    data() {
        return {
            list: [],
            categoria: -1,
            form: '',
            markers: {},
            distanceKM: -1,
            geoRoute: null
        }
    },
    beforeRouteUpdate(to, from, next) {
        next(vm => {
            vm.checkedQuery();
        });
    },
    computed: {
        categorias() {
            return this.$store.getters.categorias;
        },
        isGeoLocation() {
            return this.$refs.map && this.$refs.map.positionInitial != null;
        }
    },
    methods: {
        moveDistance(dist) {
            if (this.distanceKM !== dist) {
                this.distanceKM = dist;
                this.load();
            }
        },
        filterInDistance() {

            if (this.$refs.map.positionInitial && this.distanceKM > 0) {
                const array = [];
                for (const index in this.list) {
                    const lugar = this.list[index];
                    const to = this.$refs.map.positionInitial;
                    const from = [lugar.longitud, lugar.latitud];
                    const distanceKM = distance(to, from, {units: 'kilometers'});
                    if (distanceKM > this.distanceKM) {
                        const marker = this.markers[lugar.id];
                        marker.remove();
                        delete this.markers[lugar.id];
                    } else {
                        array.push(lugar);
                    }
                }
                this.list = array;
            }
        },
        locationMap(lugar, longitud = null, latitud = null) {

            if (longitud == null && lugar != null) {
                longitud = lugar.longitud;
            }

            if (latitud == null && lugar != null) {
                latitud = lugar.latitud;
            }

            window.scrollTo(0,0);
            const middle = this.$refs.map.travel(longitud, latitud);
            this.$refs.map.handle.flyTo({
                zoom: 15,
                center: middle,
                essential: true
            });

        },
        checkedQuery() {
            if (this.$route &&
                this.$route.query &&
                this.$route.query.lugar_ruta &&
                this.$route.query.lat &&
                this.$route.query.lng) {
                const longitude = parseFloat(this.$route.query.lng);
                const latitude = parseFloat(this.$route.query.lat);
                setTimeout(() => {
                    this.locationMap(null, longitude, latitude);
                }, 2000);
            }
        },
        openInformation(lugar) {
            this.$router.push('lugares/' + lugar.id);
        },
        async load(first = false) {

            const params = {};
            if (this.categoria !== -1) {
                params.categoriaId = this.categoria;
            }

            if (this.form !== null && this.form.length > 0) {
                params.texto = this.form;
            }

            const { data } = await this.$axios.get('lugares', { params });
            this.list = data;

            for (const index in this.markers) {
                const marker = this.markers[index];
                marker.remove();
            }
            this.markers = {};

            for (let lugar of data) {



                const marker = new mapboxgl.Marker({});
                marker.setPopup(new mapboxgl.Popup().setHTML("" +
                    "<div class='lugar-popup'>" +
                    "<div class='lugar-popup__title'>" +
                    "<p>" + lugar.nombre + "</p>" +
                    "<p>" + lugar.categoria.nombre + "</p>" +
                    "</div>" +
                    "<p>" + lugar.descripcion + "</p>" +
                    "<a target='_blank' href='/lugares/" + lugar.id + "'>Más Información</a>" +
                    "</div>"))
                marker.setLngLat([lugar.longitud, lugar.latitud]);
                marker.addTo(this.$refs.map.handle);
                marker.togglePopup();

                this.markers[lugar.id] = marker;

            }

            // Creamos un delay
            this.filterInDistance();

            if (first) {
                this.checkedQuery();

            }

        },
        moveCategoria(categoria) {
            this.categoria = categoria;
            this.load();
        },
        viewInMap(lugar) {
            window.scrollTo(0,0);
            this.$refs.map.handle.flyTo({
                zoom: 16,
                center: [lugar.longitud, lugar.latitud],
                essential: true
            });
            const marker = this.markers[lugar.id];
            if (!marker.getPopup().isOpen()) {
                marker.togglePopup();
            }
        }
    },
    created() {
        this.load(true);
    }
}
</script>

<style scoped>

.home-page__kms {
    padding: 1rem;
    display: flex;
    align-items: center;
}

.home-page__kms .tags {
    margin: 0;
    flex: 1;
}

.search {
    margin-bottom: 2rem;
}

.tags {
    display: flex;
    gap: 0.5rem;
    margin-bottom: 2rem;
}

.tag {
    font-weight: bold;
    padding: 0.6rem 1rem;
    border: none;
    border-radius: 0.7rem;
    cursor: pointer;
    transition: 0.4s;
    text-transform: uppercase;
}


.tag.active {
    cursor: default;
    background: #131A26;
    color: whitesmoke;
}

.tag:not(.active):hover {
    transform: scale(0.85);
}

.lugar-options {
    text-align: center;
}

.lugar-info {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.lugar-extra {
    margin: 1rem 0;
    font-size: 1rem;
}

.lugar-title {
    flex: 1;
}

.home-page__container {
    border-radius: 2rem;
    padding: 1rem;
    overflow-y: auto;
    box-sizing: content-box;
    position: relative;
}

.title-general {
    margin: 1rem 0;
    text-align: center;
    border-bottom: 1px solid #ddd;
}

.lugar {
    background: #eee;
    padding: 1rem;
    border-radius: 0.7rem;
    align-items: center;
    gap: 1rem;
}
.lugar h3,
.lugar p {
    margin: 0;
}
.lugar .lugar-info {
    flex: 1;
}
.lugar:not(:last-child) {
    margin-bottom: 1rem;
}



</style>


