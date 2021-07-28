<template>
    <div id="admin-ciudades-page">

        <p><strong>Notificación:</strong> {{ result }}</p>

        <div class="ciudad" v-for="(ciudad, index) in list" :key="ciudad.id">
            <div class="ciudad-nombre">
                <p><strong>#{{ ciudad.id }}</strong> {{ ciudad.nombre }}</p>
            </div>
            <div class="ciudad-opcion">
                <button @click="() => remove(ciudad.id, index)">Eliminar Ciudad</button>
            </div>
        </div>

        <form action="#" @submit.prevent="create">

            <label for="nombre">Nombre</label>
            <input type="text" name="nombre" v-model="form.nombre">

            <button>Crear Ciudad</button>

        </form>
    </div>
</template>

<script>
export default {
    methods: {
        remove(ciudadId, index) { 
            this.$axios.delete('ciudades/' + ciudadId)
                .then(() => {

                    this.result = "Se ha eliminado correctamente la ciudad.";

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
            this.$axios.post('ciudades', this.form)
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
                const response = await this.$axios.get('ciudades');
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