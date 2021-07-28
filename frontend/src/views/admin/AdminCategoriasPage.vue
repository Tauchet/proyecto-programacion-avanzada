<template>
    <div id="admin-categorias-page">

        <p><strong>Notificación:</strong> {{ result }}</p>

        <div class="categoria" v-for="(categoria, index) in list" :key="categoria.id">
            <div class="categoria-nombre">
                <p><strong>#{{ categoria.id }}</strong> {{ categoria.nombre }}</p>
            </div>
            <div class="categoria-opcion">
                <button @click="() => remove(categoria.id, index)">Eliminar Categoria</button>
            </div>
        </div>

        <form action="#" @submit.prevent="create">

            <label for="nombre">Nombre</label>
            <input type="text" name="nombre" v-model="form.nombre">

            <button>Crear Categoria</button>

        </form>
    </div>
</template>

<script>
export default {
    methods: {
        remove(ciudadId, index) { 
            this.$axios.delete('categorias/' + ciudadId)
                .then(() => {

                    this.result = "Se ha eliminado correctamente la categoria.";

                    // Eliminar 
                    const list = [...this.list];
                    list.splice(index, 1);
                    this.list = list;
                    
                    // Reiniciar formulario
                    this.form.nombre = "";
                
                })
                .catch(({ response }) => {
                    
                    const { data } = response;
                    
                    // Es un error del servicio.
                    if (data.status === 1000) {
                        this.result = data.message;
                    }

                });
        },
        create() {
            this.$axios.post('categorias', this.form)
                .then((response) => {
                    
                    const { data } = response;
                    this.list.push(data);
                    
                    // Reiniciar formulario
                    this.form.nombre = "";
                
                })
                .catch(({ response }) => {
                    
                    const { data } = response;
                    
                    // Es un error del servicio.
                    if (data.status === 1000) {
                        this.result = data.message;
                    }

                });
        },
        async loadAll() {
            try {
                const response = await this.$axios.get('categorias');
                const { data } = response;
                if (data) {
                    this.list = data;
                }
            } catch (ex) {
                console.log("Encontrado!");
            }
        }
    },
    data() {
        return {
            list: [],
            result: "",
            form: {
                nombre: ""
            }
        }
    },
    created() {
        this.loadAll();
    }
}
</script>