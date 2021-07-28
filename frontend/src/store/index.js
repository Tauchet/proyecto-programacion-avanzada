import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    general: {
      categorias: [],
      ciudades: []
    }
  },
  mutations: {
    loadGeneral(state, payload) {
      state.general = payload;
    }
  },
  actions: {
    load(context, payload) {
      context.commit('loadGeneral', payload);
    }
  },
  getters: {
    allCiudades(state) {
      return state.general.ciudades;
    },
    allCategorias(state) {
      return state.general.categorias;
    }
  },
  modules: {
  }
})
