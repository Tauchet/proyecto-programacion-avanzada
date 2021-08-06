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
                            <div class="lugar-page__header-options">
                                <template v-if="info.lugar.estado === 'APROBADO'">
                                    <AppButton @click="markFavorite" type="orange" inline>
                                        <template v-if="info.favorito">
                                            <i class="far fa-heart"></i> QUITAR FAVORITO
                                        </template>
                                        <template v-else>
                                            <i class="fas fa-heart"></i> MARCAR FAVORITO
                                        </template>
                                    </AppButton>
                                    <AppButton @click="goRouteExplorer" type="purple" inline>
                                        <i class="fas fa-map-marker-alt"></i> RUTA EXPLORADORA
                                    </AppButton>
                                </template>
                                <AppButton :type="isOpen ? 'orange' : 'danger'" inline>
                                    <template v-if="isOpen">
                                        <i class="fas fa-door-open"></i> ABIERTO
                                    </template>
                                    <template v-else>
                                        <i class="fas fa-door-closed"></i> CERRADO
                                    </template>
                                </AppButton>
                                <template v-if="info.lugar.estado === 'ESPERANDO' && isModerator">
                                    <AppButton type="success" @click="approve" inline>APROBAR</AppButton>
                                    <AppButton type="danger" @click="disapprove" inline>DESAPROBAR</AppButton>
                                </template>
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
                        <div class="lugar-page__images-contet">
                            <div class="lugar-page__images">
                                <template v-if="this.info.lugar.imagenes">
                                    <transition name="fade" mode="out-in">
                                        <div class="image" v-for="image in [currentImage]" :key="image">
                                            <img v-if="info.lugar.imagenes[image] && info.lugar.imagenes[image].url" width="100%" height="100%" :src="info.lugar.imagenes[image].url" alt="">
                                        </div>
                                    </transition>
                                </template>
                            </div>
                            <div class="lugar-pages__controls" v-if="this.info.lugar.imagenes">
                                <AppButton v-if="currentImage - 1 >= 0" @click="() => changeImage(-1)" small>Anterior</AppButton>
                                <AppButton v-if="currentImage + 1 < this.info.lugar.imagenes.length" @click="() => changeImage(+1)" small>Siguiente</AppButton>
                            </div>
                        </div>

                    </AppMultipleLine>
                    <AppMultipleLine columns="2">
                        <div class="lugar-page__horarios">
                            <FormSection>
                                <template slot="title">Horarios</template>
                                <div class="horarios" slot="content" v-if="info.lugar.horarios.length > 0">
                                    <table>
                                        <tr>
                                            <th>Horario</th>
                                            <th>Lunes</th>
                                            <th>Martes</th>
                                            <th>Miércoles</th>
                                            <th>Jueves</th>
                                            <th>Viernes</th>
                                            <th>Sábado</th>
                                            <th>Domingo</th>
                                        </tr>
                                        <tr v-for="horario in info.lugar.horarios" :key="horario.id">
                                            <td>De {{ horario.inicioHoras }}:{{ horario.inicioMinutos }} a {{  horario.finalHoras }}:{{ horario.finalMinutos }}</td>
                                            <td>{{ horario.lunes ? 'Abierto' : 'Cerrado' }}</td>
                                            <td>{{ horario.martes ? 'Abierto' : 'Cerrado' }}</td>
                                            <td>{{ horario.miercoles ? 'Abierto' : 'Cerrado' }}</td>
                                            <td>{{ horario.jueves ? 'Abierto' : 'Cerrado' }}</td>
                                            <td>{{ horario.viernes ? 'Abierto' : 'Cerrado' }}</td>
                                            <td>{{ horario.sabado ? 'Abierto' : 'Cerrado' }}</td>
                                            <td>{{ horario.domingo ? 'Abierto' : 'Cerrado' }}</td>
                                        </tr>
                                    </table>
                                </div>
                            </FormSection>
                        </div>
                        <div class="lugar-page__telefonos">
                            <FormSection>
                                <template slot="title">Telefonos</template>
                                <div class="telefonos" slot="content" v-if="info.lugar.telefonos.length > 0">
                                    <ul>
                                        <li v-for="telefono in info.lugar.telefonos" :key="telefono.id">{{ telefono.numero }}</li>
                                    </ul>
                                </div>
                            </FormSection>
                        </div>
                    </AppMultipleLine>
                </div>
            </div>
            <div class="lugar-page__comments" v-if="this.info.lugar.estado === 'APROBADO'">
                <div class="container">
                    <AppTitle v-if="isLogged || info.lugar.comentarios.length > 0" space><i class="fas fa-comments"></i> Comentarios</AppTitle>
                    <div class="create-comment" v-if="isLogged && !info.comentario">
                        <div class="app-form-input app-form-stars">
                            <label class="app-form-input__name">Calificación</label>
                            <div class="stars">
                                <div class="star" v-for="number in 5" :key="number" @click.prevent="() => changeCalification(number)">
                                    <template v-if="number <= comment.calificacion">
                                        <i class="fas fa-star"></i>
                                    </template>
                                    <template v-else>
                                        <i class="far fa-star"></i>
                                    </template>
                                </div>
                            </div>
                        </div>
                        <AppFormInput name="texto" :errors="commentErrors" @enter="sendComment" v-model="comment.texto" extra-type="textarea" label="Ingresa tu comentario aquí." />
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

table {
    width: 100%;
    background: #eee;
    border-radius: 0.5rem;
}

th {
    padding: 1rem 0;
}

tr {
    text-align: center;
}

td {
    padding: 0.5rem;
}

.image,
.image img {
    width: 100%;
    height: 100%;
    border-radius: 1.5rem;
}

.lugar-page__images-contet {
    position: relative;
}

.fade-enter-active,
.fade-leave-active {
    transition: all 0.9s ease;
    overflow: hidden;
    visibility: visible;
    position: absolute;
    width:100%;
    opacity: 1;
}

.fade-enter,
.fade-leave-to {
    visibility: hidden;
    width:100%;
    opacity: 0;
}

.stars {
    margin-top: 0.4rem;
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.star {
    border: none;
    background: transparent;
    cursor: pointer;
    font-size: 1.7rem;
    transition: 0.4s;
    color: #F39C12;
}

.star:hover {
    transform: scale(1.2);
}

.lugar-page__images {
    position: relative;
    background: #eee;
    border-radius: 0.7rem;
    height: 25rem;
    max-height: 25rem;
}

.lugar-page__header {
    position: sticky;
    z-index: 10;
    top: 0;
    background: white;
    border-bottom: 1px solid #ddd;
}

.lugar-pages__controls {
    position: absolute;
    top: 0;
    display: flex;
    width: 100%;
    gap: 0.5rem;
    margin: 1rem;
}

.lugar-page__header .lugar-page__header-container {

    display: flex;
    align-items: center;
    padding: 1rem 0;

    p, h1, h2, h3 {
        margin: 0;
    }

}

.create-comment {
    margin-bottom: 1rem;
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

const DAYS = ['domingo', 'lunes', 'martes', 'miercoles', 'jueves', 'viernes', 'sabado'];

import AppButton from "../components/AppButton";
import Mapbox from "../components/LugarMapbox";
import AppMultipleLine from "../components/AppMultipleLine";
import UsuarioBlock from "../components/UsuarioBlock";
import AppAlert from "../components/AppAlert";
import AppTitle from "../components/AppTitle";
import AppFormInput from "../components/form/AppFormInput";
import Comment from "./LugarPage/Comment";
import AppError from "../components/AppError";
import ValidationUtil from "../libs/ValidationUtil";

import routes from '../routes/routes';
import FormSection from "../components/form/FormSection";

export default {
    name: "LugarPage",
    components: {
        FormSection,
        AppError, Comment, AppFormInput, AppTitle, AppAlert, UsuarioBlock, AppMultipleLine, Mapbox, AppButton},
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
                calificacion: 0,
                texto: ''
            },
            commentErrors: {},
            currentImage: 0,
            isOpen: false
        }
    },
    computed: {
        isModerator() {
            const rolesIndex = routes.roles.indexOf(this.$store.state.rol || "USUARIO");
            return rolesIndex >= 1;
        },
        isLogged() {
            return this.$store.state.user !== null;
        }
    },
    methods: {
        changeImage(position) {
            this.currentImage += position;
        },
        changeCalification(number) {
            this.comment.calificacion = number;
        },
        sendComment() {

            // Validaciones previas --- inicio
            this.commentErrors = {};
            ValidationUtil.validateIfNotEmpty(this.commentErrors, this.comment, 'texto');
            if (Object.keys(this.commentErrors).length > 0) {
                return;
            }
            // Validaciones previas --- final

            const lugarId = this.$route.params.lugarId;
            const request = this.$axios.post('lugares/' + lugarId + '/comentarios', this.comment);

            request.then(({ data }) => {
                this.info.comentario = true;
                this.info.lugar.comentarios.splice(0, 0, data);
            });

        },
        markFavorite() {
            const lugarId = this.$route.params.lugarId;
            const request = this.info.favorito ? this.$axios.delete('lugares/' + lugarId + '/favorito') : this.$axios.post('lugares/' + lugarId + '/favorito');
            request.then(() => {
                this.info.favorito = !this.info.favorito;
            });

        },
        goRouteExplorer() {
            this.$router.push({
                path: '/',
                query: {
                    lugar_ruta: true,
                    lng: this.info.lugar.longitud,
                    lat: this.info.lugar.latitud
                }
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
                if (data.status === 2000) {
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
                if (data.status === 2000) {
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
                if (this.info.lugar.horarios) {
                    this.isOpen = false;
                    const date = new Date();
                    const hours = date.getHours();
                    const minutes = date.getMinutes();
                    const dayOfWeeken = DAYS[date.getDay()];
                    for (let horario of this.info.lugar.horarios) {
                        if (horario[dayOfWeeken]) {
                            if (hours > horario.inicioHoras && hours <= horario.finalHoras) {
                                if (minutes > horario.inicioMinutos && hours <= horario.finalMinutos) {
                                    this.isOpen = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            });

            request.catch(({ response }) => {
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

