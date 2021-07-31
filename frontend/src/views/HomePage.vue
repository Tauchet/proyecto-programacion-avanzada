<template>
    <div id="home-page">
        <div class="home-page__container">
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
                    <AppButton @click="load" small>Buscar</AppButton>
                </div>
            </div>
            <div class="lugares">
                <div class="lugar" v-for="lugar in list" :key="lugar.id">
                    <div class="lugar-info">
                        <div class="lugar-title">
                            <h3>{{ lugar.nombre }}</h3>
                            <p>{{ lugar.categoria.nombre }}</p>
                        </div>
                        <div class="lugar-date">
                            <p>{{lugar.fechaCreacion}}</p>
                        </div>
                    </div>
                    <div class="lugar-extra">
                        <p><strong>Ciudad:</strong> {{lugar.ciudad.nombre}}</p>
                    </div>
                    <div class="lugar-options">
                        <AppButton inline small @click="() => viewInMap(lugar)">Ver en el Mapa</AppButton>
                        <AppButton type="purple" inline small>Más Información</AppButton>
                    </div>
                </div>
            </div>
        </div>
        <GeneralMapbox ref="map" />
    </div>
</template>

<script>

import AppButton from "../components/AppButton";
import GeneralMapbox from "../components/GeneralMapbox";
import AppFormInput from "../components/form/AppFormInput";
import mapboxgl from "mapbox-gl";
export default {
    name: 'HomePage',
    components: {AppFormInput, GeneralMapbox, AppButton},
    data() {
        return {
            list: [],
            categoria: -1,
            form: '',
            markers: {}
        }
    },
    methods: {
        async load() {

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


        },
        moveCategoria(categoria) {
            this.categoria = categoria;
            this.load();
        },
        viewInMap(lugar) {
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
    computed: {
        categorias() {
            return this.$store.getters.categorias;
        }
    },
    created() {
        this.load();
    }
}
</script>

<style scoped>

.search {
    margin-bottom: 2rem;
}

.tags {
    display: grid;
    gap: 0.5rem;
    grid-template-columns: repeat(3, 1fr);
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

#home-page {
    overflow: auto;
    flex: 1;
    display: grid;
    grid-template-columns: 30% 1fr;
}

</style>


