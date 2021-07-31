<template>
<div class="comment-content">
    <div class="comment">
        <div class="comment-title">
            <UsuarioBlock :usuario="info.usuario" divider>
                <template>
                    <div class="comment-info">
                        <div class="stars">
                            <p class="stars-number">{{info.calificacion}}</p>
                            <div class="star" v-for="number in 5" :key="number">
                                <template v-if="number <= info.calificacion">
                                    <i class="fas fa-star"></i>
                                </template>
                                <template v-else>
                                    <i class="far fa-star"></i>
                                </template>
                            </div>
                        </div>
                        <p>{{ info.fechaComentario }}</p>
                    </div>
                    <div class="comment-extra">
                        <AppButton @click="reply" type="purple" v-if="isOwner && (!showReply) && !info.respuesta">RESPONDER</AppButton>
                    </div>
                </template>
            </UsuarioBlock>
        </div>
        <div class="comment-content">
            <p>{{ info.texto }}</p>
        </div>
    </div>
    <div class="comment-reply">
        <div class="comment-reply-form" v-if="showReply">
            <AppFormInput :errors="errors" name="texto" v-model="form.texto" extra-type="textarea" label="Ingresar la respuesta:" />
            <div class="comment-reply-form__buttons">
                <div class="box-center">
                    <AppButton @click="sendReply" type="danger" inline>ENVIAR RESPUESTA</AppButton>
                    <AppButton @click="closeReplay" inline>CANCELAR</AppButton>
                </div>
            </div>
        </div>
        <template v-if="info.respuesta">
            <div class="comment-reply-title">
                <p>RESPUESTA</p>
            </div>
            <div class="comment-reply-content comment">
                <div class="comment-title">
                    <UsuarioBlock :usuario="lugar.usuario" divider></UsuarioBlock>
                </div>
                <div class="comment-content">
                    <p>{{ info.respuesta }}</p>
                </div>
            </div>
        </template>
    </div>
</div>
</template>

<script>
import UsuarioBlock from "../../components/UsuarioBlock";
import AppButton from "../../components/AppButton";
import AppFormInput from "../../components/form/AppFormInput";
import ValidationUtil from "../../libs/ValidationUtil";

export default {
    name: "Comment",
    props: {
        info: Object,
        lugar: Object
    },
    computed: {
        isOwner() {
            return this.$store.state.userId == this.lugar.usuario.id;
        }
    },
    data() {
        return {
            form: {
                texto: ''
            },
            showReply: false,
            errors: {}
        }
    },
    methods: {
        reply() {
            this.showReply = true;
        },
        sendReply() {

            // Validaciones previas --- inicio
            this.errors = {};
            ValidationUtil.validateIfNotEmpty(this.errors, this.form, 'texto');
            if (Object.keys(this.errors).length > 0) {
                return;
            }
            // Validaciones previas --- final

            this.showReply = false;

            const commentId = this.info.id;
            const request = this.$axios.post('lugares/comentarios/' + commentId, this.form);

            request.then(() => {

                this.info.respuesta = this.form.texto;
                this.closeReplay();

            });

        },
        closeReplay() {
            this.form.texto = '';
            this.showReply = false;
        }
    },
    components: {AppFormInput, AppButton, UsuarioBlock}
}
</script>

<style scoped>

.comment {
    padding: 1rem;
    background: #EFEFEF;
    border-radius: 0.5rem;
}

.comment-title {
    margin-bottom: 1rem;
}

.comment p {
    margin: 0;
}

.comment-reply {
    margin: 1rem 0 1rem 5rem;
}

.comment-reply-title {
    font-weight: bold;
    color: #818181;
}

.comment-info {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.stars {
    display: flex;
    font-size: 1.3rem;
    color: #F39C12;
}

.stars-number {
    padding-right: 0.5rem;
}

</style>