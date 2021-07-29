<template>
    <div class="lugar-page">
        <p>Lugar</p>
        <p>{{ info }}</p>
        <button @click="markFavorite">Marcar como Favorito</button>
        <button @click="removeFavorite">Quitar Favorito</button>
        
        <form action="#" @submit.prevent="createComment">

            <p>Comentario</p>
            <textarea name="comentario" v-model="form_comentario.texto" id="" cols="30" rows="10"></textarea>

            <p>Reputación</p>
            <input type="number" v-model="form_comentario.calificacion">

            <button>Crear Comentario</button>

            <div class="comentario" v-for="comentario in info.lugar.comentarios" :key="comentario.id">
                <form action="#" @submit.prevent="() => replyComment(comentario.id)">
                    {{comentario}}
                    <textarea v-model="form_respuesta.texto" name="" id="" cols="30" rows="10"></textarea>
                    <button>Responder Comentario</button>
                </form>
            </div>

        </form>

    </div>
</template>

<script>
export default {
    data() {
        return {
            info: {},
            form_comentario: {
                calificacion: 0,
                texto: ""
            },
            form_respuesta: {
                texto: ""
            }
        }
    },
    methods: {
        async replyComment(comentarioId) {
            console.log(comentarioId);
            await this.$axios.post("lugares/comentarios/" + comentarioId, this.form_respuesta);
            
        },
        async createComment() {
            const lugarId = this.$route.params.lugarId;
            await this.$axios.post("lugares/" + lugarId + "/comentarios", {
                ...this.form_comentario
            });
        },
        async markFavorite() {
            await this.$axios.post("lugares/" + this.$route.params.lugarId + "/favorito", {});
            this.info.favorito = true;
        },
        async removeFavorite() {
            await this.$axios.delete("lugares/" + this.$route.params.lugarId + "/favorito");
            this.info.favorito = false;
        },
        async load() {
            const { data } = await this.$axios.get("lugares/" + this.$route.params.lugarId);
            this.info = data;
        }
    },
    created() {
        this.load();
    }
}
</script>