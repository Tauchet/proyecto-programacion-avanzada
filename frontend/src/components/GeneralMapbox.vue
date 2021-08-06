<template>
    <div id="map">

    </div>
</template>

<script>

import mapboxgl from "mapbox-gl";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";
import MapboxDirections from '@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions';

export default {
    name: "GeneralMapbox",
    props: {},
    data() {
        return {
            positionInitial: null,
            map: null,
            controlDirections: null
        }
    },
    methods: {
        travel(longitud, latitud) {

            this.controlDirections.setOrigin(this.positionInitial);
            this.controlDirections.setDestination([longitud, latitud]);

            const x = (longitud - this.positionInitial[0]) / 2;
            const y = (latitud - this.positionInitial[1]) / 2;

            return [this.positionInitial[0] + x, this.positionInitial[1] + y];

        },
        async createMap() {

            mapboxgl.accessToken = "pk.eyJ1IjoidGF1Y2hldCIsImEiOiJja3Jtd3h0d2M2MGdwMnBwZGRyNGFlaWptIn0.vFy_VTRRTWpDT5tvMwb6Ng";

            const map = this.map  = new mapboxgl.Map({
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

            const $self = this;

            map.on('load', function() {
                if ("geolocation" in navigator) {
                    navigator.geolocation.getCurrentPosition(position => {

                        const positionInitial = $self.positionInitial = [position.coords.longitude, position.coords.latitude];

                        const directions = $self.controlDirections = new MapboxDirections({
                            accessToken: mapboxgl.accessToken,
                            unit: 'metric',
                            interactive: false
                        });

                        directions.setOrigin(positionInitial);

                        directions.on('route', e => {
                            if (e.route && e.route.length > 0) {
                                const route = e.route[0];
                                $self.$emit('map-route', {
                                    minutes: (route.duration / 60).toFixed(),
                                    km: (route.distance / 1000).toFixed(1)
                                });
                            } else {
                                $self.$emit('map-route', undefined);
                            }
                        })

                        map.addControl(
                            directions,
                            'top-left'
                        );

                        map.flyTo({
                            center: positionInitial,
                            esential: true,
                            zoom: 14
                        });

                        $self.$emit('map-load', map);

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
    height: 75vh;
    width: 100%;
}
</style>