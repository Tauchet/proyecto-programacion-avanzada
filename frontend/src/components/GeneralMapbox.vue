<template>
    <div id="map">

    </div>
</template>

<script>

import mapboxgl from "mapbox-gl";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";

export default {
    name: "GeneralMapbox",
    props: {},
    methods: {
        async createMap() {

            mapboxgl.accessToken = "pk.eyJ1IjoidGF1Y2hldCIsImEiOiJja3Jtd3h0d2M2MGdwMnBwZGRyNGFlaWptIn0.vFy_VTRRTWpDT5tvMwb6Ng";

            const map  = new mapboxgl.Map({
                container: "map",
                style: "mapbox://styles/mapbox/streets-v11",
                center: [-75.27986747330804,4.966951000736728],
                zoom: 6,
            });

            map.addControl(new mapboxgl.GeolocateControl({
                positionOptions: {
                    enableHighAccuracy: true
                },
                trackUserLocation: true
            }));

            map.addControl(new mapboxgl.NavigationControl());

            this.handle = map;

            map.on('load', function() {
                if ("geolocation" in navigator) {
                    navigator.geolocation.getCurrentPosition(position => {
                        map.flyTo({
                            center: [position.coords.longitude, position.coords.latitude],
                            esential: true,
                            zoom: 14
                        });
                    });
                }
            });

        }
    },
    mounted() {
        this.createMap();
    }
}
</script>

<style scoped>
#map {
    height: 100%;
    width: 100%;
}
</style>