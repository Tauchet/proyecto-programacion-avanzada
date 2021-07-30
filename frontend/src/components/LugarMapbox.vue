<template>
<div class="mapbox">
    <div id="map">

    </div>
</div>
</template>

<script>

import mapboxgl from "mapbox-gl";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";

export default {
    name: "Mapbox",
    props: {
        lugar: Object
    },
    methods: {
        async createMap() {

            mapboxgl.accessToken = "pk.eyJ1IjoidGF1Y2hldCIsImEiOiJja3Jtd3h0d2M2MGdwMnBwZGRyNGFlaWptIn0.vFy_VTRRTWpDT5tvMwb6Ng";

            const LOCATION = [this.lugar.longitud, this.lugar.latitud];

            const map = new mapboxgl.Map({
                container: "map",
                style: "mapbox://styles/mapbox/streets-v11",
                center: LOCATION,
                zoom: 15,
            });

            const marker = new mapboxgl.Marker({});
            marker.setLngLat(LOCATION);
            marker.addTo(map);

        }
    },
    mounted() {
        this.createMap();
    }
}
</script>

<style scoped>
#map {
    height: 300px;
}
</style>