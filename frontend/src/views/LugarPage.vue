<template>
    <div id="lugar-page">
        <AppError v-if="notification" :message="notification" />
        <template v-if="info.lugar !== null">
            <div class="lugar-page__header">
                <div class="container">
                    <AppAlert :type="alertType" v-if="alertMessage">
                        <p>{{ alertMessage }}</p>
                    </AppAlert>
                    <AppAlert v-if="info.lugar.estado === 'DESAPROBADO'">
                        <p>¡Este lugar fue <strong>desaprobado</strong> el <strong>{{info.lugar.fechaAprobacion}}</strong>! Podrás ver la información solamente.</p>
                    </AppAlert>
                    <div class="lugar-page__header-container">
                        <div class="lugar-page__header-title">
                            <h1>{{ info.lugar.nombre }}</h1>
                            <p class="lugar-page__header-category">{{ info.lugar.categoria.nombre }}</p>
                        </div>
                        <div class="lugar-page__header-extra">
                            <p>{{ info.lugar.fechaCreacion }}</p>
                            <div class="lugar-page__header-options" v-if="info.lugar.estado === 'ESPERANDO' && isModerator">
                                <AppButton type="success" @click="approve" inline>APROBAR</AppButton>
                                <AppButton type="danger" @click="disapprove" inline>DESAPROBAR</AppButton>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="lugar-page__map" v-if="this.info.lugar">
                <Mapbox :lugar="this.info.lugar" />
            </div>
            <div class="lugar-page__info">
                <div class="container">
                    <AppMultipleLine columns="2">
                        <div class="lugar-page__descripcion">
                            <UsuarioBlock :usuario="this.info.lugar.usuario" divider/>
                            <p>{{ this.info.lugar.descripcion }}</p>
                        </div>
                        <div class="lugar-page__images"></div>
                    </AppMultipleLine>
                </div>
            </div>
            <div class="lugar-page__comments" v-if="this.info.lugar.estado === 'APROBADO'">
                <div class="container">
                    <AppTitle v-if="isLogged || info.lugar.comentarios.length > 0" space><i class="fas fa-comments"></i> Comentarios</AppTitle>
                    <div v-if="isLogged && !info.comentario">
                        <AppFormInput @enter="sendComment" v-model="comment.texto" extra-type="textarea" label="Ingresa tu comentario aquí." />
                        <div class="box-center">
                            <AppButton @click="sendComment">CREAR COMENTARIO</AppButton>
                        </div>
                    </div>
                    <div class="comments">
                        <Comment v-for="comment in info.lugar.comentarios" :key="comment.id" :lugar="info.lugar" :info="comment" />
                    </div>
                </div>
            </div>
        </template>
    </div>
</template>

<style lang="scss" scoped>
.lugar-page__images {
    background: #eee;
    border-radius: 0.7rem;
    height: 20rem;
}

.lugar-page__header {
    position: sticky;
    z-index: 10;
    top: 0;
    background: white;
    border-bottom: 1px solid #ddd;
}

.lugar-page__header .lugar-page__header-container {

    display: flex;
    align-items: center;
    padding: 1rem 0;

    p, h1, h2, h3 {
        margin: 0;
    }

}


.lugar-page__header-title {
    flex: 1;
    text-transform: uppercase;
}

.lugar-page__header-extra {
    text-align: right;
}

.lugar-page__header-options {
    margin-top: 0.4rem;
}


.lugar-page__header-category {
    color: #A5A5A5;
    font-weight: bold;
}

</style>

<script>

import AppButton from "../components/AppButton";
import Mapbox from "../components/LugarMapbox";
import AppMultipleLine from "../components/AppMultipleLine";
import UsuarioBlock from "../components/UsuarioBlock";
import AppAlert from "../components/AppAlert";
import AppTitle from "../components/AppTitle";
import AppFormInput from "../components/form/AppFormInput";
import Comment from "./LugarPage/Comment";
import AppError from "../components/AppError";

export default {
    name: "LugarPage",
    components: {AppError, Comment, AppFormInput, AppTitle, AppAlert, UsuarioBlock, AppMultipleLine, Mapbox, AppButton},
    data() {
        return {
            notification: null,
            alertType: 'success',
            alertMessage: null,
            info: {
                lugar: null,
                favorito: false,
                comentario: false
            },
            comment: {
                calificacion: 3,
                texto: ''
            }
        }
    },
    computed: {
        isModerator() {
            return this.$store.state.user !== null && this.$store.state.rol === 'MODERADOR';
        },
        isLogged() {
            return this.$store.state.user !== null;
        }
    },
    methods: {
        sendComment() {

            const lugarId = this.$route.params.lugarId;
            const request = this.$axios.post('lugares/' + lugarId + '/comentarios', this.comment);

            request.then(({ data }) => {
                this.info.comentario = true;
                this.info.lugar.comentarios.splice(0, 0, data);
            });

        },
        approve() {

            const lugarId = this.$route.params.lugarId;
            const request = this.$axios.put('moderador/lugares/' + lugarId + '/aprobar');

            request.then(() => {
                this.alertType = 'success';
                this.alertMessage = '¡Se ha aprobado correctamente este lugar!';
                this.info.lugar.estado = 'APROBADO';
            });

            request.catch(({ response }) => {

                const { data } = response;

                // Problemas de validación
                if (data.status === 1000) {
                    this.alertType = 'danger';
                    this.alertMessage = data.message;
                }

            });

        },
        disapprove() {

            const lugarId = this.$route.params.lugarId;
            const request = this.$axios.put('moderador/lugares/' + lugarId + '/desaprobar');

            request.then(() => {
                this.alertType = 'success';
                this.alertMessage = '¡Se ha desaprobado correctamente este lugar!';
                this.info.lugar.estado = 'DESAPROBADO';
            });

            request.catch(({ response }) => {

                const { data } = response;

                // Problemas de validación
                if (data.status === 1000) {
                    this.alertType = 'danger';
                    this.alertMessage = data.message;
                }

            });

        },
        load() {

            const lugarId = this.$route.params.lugarId;
            const request = this.$axios.get('lugares/' + lugarId);

            request.then(({ data }) => {
                this.info = data;
            });

            request.catch(({ response }) => {
                console.log(response.data.status);
                if (response && response.data && response.data.status === 1000) {
                    this.notification = response.data.message;
                }
            });

        }
    },
    created() {
        this.load();
    }
}
</script>

