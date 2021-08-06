<template>
<div class="mapbox">
    <div id="map">

    </div>
</div>
</template>

<script>

import mapboxgl from "mapbox-gl";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";
import MapboxDirections from '@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions';

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

            map.addControl(new mapboxgl.GeolocateControl({
                positionOptions: {
                    enableHighAccuracy: true
                },
                trackUserLocation: true
            }));

            map.addControl(new mapboxgl.NavigationControl());

            const marker = new mapboxgl.Marker({});
            marker.setLngLat(LOCATION);
            marker.addTo(map);

            const $self = this;

            map.on('load', function() {
                if ("geolocation" in navigator) {
                    navigator.geolocation.getCurrentPosition(position => {

                        const positionInitial = [position.coords.longitude, position.coords.latitude];
                        const directions = new MapboxDirections({
                            accessToken: mapboxgl.accessToken,
                            unit: 'metric',
                            interactive: false
                        });

                        directions.setOrigin(positionInitial);
                        directions.setDestination([$self.lugar.longitud, $self.lugar.latitud]);

                        map.addControl(
                            directions,
                            'top-left'
                        );

                        map.flyTo({
                            center: positionInitial,
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
    height: 300px;
}
</style>