<template>
    <div class="app-form-files app-form-input" :class="{'error': error}">
        <input @change="changeFiles" ref="input" type="file" name="name" style="display: none;" multiple/>
        <div class="app-form-files__title">
            <div class="app-form-files__title-text">
                <label class="app-form-input__name">Imagenes</label>
                <div class="app-form-input__error" v-if="error">
                    <p><i class="fab fa-fort-awesome-alt"></i> {{ error }}</p>
                </div>
            </div>
            <AppButton @click="selectFiles" type="purple" inline>Seleccionar Imagenes</AppButton>
            <AppButton @click="uploadFiles" v-if="pending > 0" inline>Subir Imagenes</AppButton>
        </div>
        <div class="app-form-files__content app-form-input__value" @drop.prevent="onDragEvent" @dragover.prevent>
            <template v-if="Object.keys(files).length > 0">
                <div class="app-form-files__list">
                    <div class="app-form-file" v-for="(file, index) in files" :key="file.name">
                        <img :src="displayImage(file.data)" alt="">
                        <div class="app-form-extra">
                            <div class="app-form-file__data">
                                <p class="app-form-file__name">{{ file.data.name }}</p>
                                <p>{{ file.data.size }}</p>
                            </div>
                            <p class="app-form-file__error">{{ file.error }}</p>
                            <div class="app-form-file__progress">
                                <div class="progress-current" :style="{width: file.progress + '%'}"></div>
                                <div class="progress-total"></div>
                            </div>
                        </div>
                        <div class="app-form-remove">
                            <AppButton @click="() => remove(index)" type="danger"><i class="fas fa-times"></i></AppButton>
                        </div>
                    </div>
                </div>
            </template>
            <template v-else>
                <div class="notification">
                    <div class="notification-icon">
                        <p><i class="fas fa-dove fa-5x"></i></p>
                    </div>
                    <p>Suelta aquí los archivos o dale al botón de <strong>Seleccionar Archivos</strong></p>
                </div>
            </template>
        </div>
    </div>
</template>

<style scoped>
.app-form-files__title {
    margin-bottom: 0.8rem;
    display: flex;
    align-items: center;
}

.app-form-files p {
    margin: 0;
}

.app-form-files__content {
    padding: 0 !important;
}

.app-form-files__list {
    padding: 1rem;
}

.app-form-files__title-text {
    flex: 1;
}

.notification {
    text-align: center;
    padding: 3rem;
}

.notification-icon {
    margin-bottom: 1rem;
}

.app-form-file {
    display: flex;
    gap: 1rem;
    align-items: center;
}

.app-form-file__error {
    margin-bottom: 0.5rem !important;
    color: red;
}

.app-form-file:not(:last-child) {
    margin-bottom: 2rem;
}

.app-form-file img {
    max-width: 150px;
    max-height: 150px;
    min-height: 150px;
    min-width: 150px;
}

.app-form-extra {
    flex: 1;
}

.app-form-file__data {
    display: flex;
}

.app-form-file__name {
    flex: 1;
}

.app-form-file__progress {
    position: relative;
}

.progress-total {
    border-radius: 0.5rem;
    width: 100%;
    height: 10px;
    background: #ddd;
}

.progress-current {
    position: absolute;
    border-radius: 0.5rem;
    width: 100%;
    height: 10px;
    background: #9B59B6;
}
</style>

<script>
import AppButton from "../AppButton";

export default {
    name: "AppFormFiles",
    components: {AppButton},
    computed: {
        error() {
            if (this.errors !== null) {
                return this.errors[this.name] || undefined;
            }
            return undefined;
        }
    },
    props: {
        name: {
            type: String,
            default: 'General'
        },
        errors: {
            type: Object,
            default() {
                return null;
            }
        }
    },
    data() {
        return {
            files: [],
            pending: 0
        }
    },
    methods: {
        remove(index) {
            const newArray = [...this.files];
            newArray.splice(index, 1);
            this.files = newArray;
            this.emitFiles();
        },
        emitFiles() {
            const array = [];
            this.files.forEach((file) => {
               if (file.url) {
                   array.push(file.url);
               }
            });
            this.$emit('input', array);
        },
        selectFiles() {
            this.$refs.input.click();
        },
        changeFiles(event) {
            if (event.target.files) {
                this.onFilesChange(event.target.files);
            }
        },
        uploadFiles() {
            this.files.forEach((file, index) => {
                (function (file, axios, self, index) {
                    let formData = new FormData();
                    formData.append('archivo', file.data);
                    axios.post('imagenes',
                        formData,
                        {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        }
                    ).then(function ({ data }) {
                        self.$set(self, 'pending', self.pending - 1);
                        self.$set(self.files[index], 'url', data.nombreArchivoURL);
                        self.$set(self.files[index], 'progress', 100);
                        self.emitFiles();
                    }).catch(function ({response}) {
                        self.$set(self.files[index], 'error', response.data ? (response.data.message && response.data.message.length > 0 ? response.data.message : response.data.error) : "Error Interno");
                    });
                })(file, this.$axios, this, index);
            });
        },
        displayImage(file) {
            return URL.createObjectURL(file);
        },

        onDragEvent(event) {
            let droppedFiles = event.dataTransfer.files;
            if (!droppedFiles) {
                return;
            }
            this.onFilesChange(droppedFiles);
        },
        onFilesChange(droppedFiles) {

            const cloneFiles = [...droppedFiles];
            cloneFiles.forEach(file => {
                if (file.type && file.type.startsWith('image')) {
                    this.pending += 1;
                    this.files.push({
                        data: file,
                        progress: 0
                    });
                }
            });

        }
    }
}
</script>

