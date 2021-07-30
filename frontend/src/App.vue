<template>
  <div id="app">
    <header v-if="isHeader">
        <div class="container">
            <h1><i class="fas fa-map"></i> Lugares</h1>
        </div>
    </header>
      <main>
          <router-view/>
      </main>
  </div>
</template>

<script>
export default {

    methods: {
        async loadGeneral() {
            const { data } = await this.$axios.get('general');
            this.$store.commit('loadGeneral', data);
        }
    },

    computed: {
        isHeader() {
            return this.$route.meta && !this.$route.meta.disableHeader;
        }
    },

    created() {
        this.loadGeneral();
    }

}
</script>

<style lang="scss">

@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css');
@import url('https://cdn.jsdelivr.net/npm/normalize.css@8.0.1/normalize.css');
@import url('https://fonts.googleapis.com/css2?family=Nunito:wght@400;700&display=swap');

* {
    font-family: 'Nunito', sans-serif;
    box-sizing: border-box;
}

html, body, #app, main {
    height: 100%;
    width: 100%;
}


header {
    background: #131A26;
    color: white;

    h1 {
        margin: 0;
    }

    .container {
        padding: 2rem 0;
    }

}

.app-form-input {

    &:not(:first-child:last-child) {
        margin-bottom: 1rem;
    }

    .app-form-input__name {
        font-weight: bold;
        margin-bottom: 1rem;
        text-transform: uppercase;
        color: #A5A5A5;
        transition: 0.4s;
    }

    .app-form-input__value {
        display: block;
        width: 100%;
        padding: 0.5rem;
        border-radius: 0.5rem;
        border: 1px solid #C1C1C1;
        outline: none;
        transition: 0.4s;
    }

}

.app-form-input.focus {

    .app-form-input__name {
        color: #1976DB;
    }

    .app-form-input__value {
        border-color: #1976DB;
    }

}

.app-form-input.label .app-form-input__value {
    margin-top: 0.3rem;
}

.container {
    max-width: 80%;
    min-width: 80%;
    margin: auto;
    padding: 1rem 0;
}

.box-center {
    display: flex;
    align-items: center;
    justify-content: center;
}

</style>
