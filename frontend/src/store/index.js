import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    general: {
      ciudades: [],
      categorias: []
    }
  },
  mutations: {
    loadGeneral(state, payload) {
      state.general = payload;
    }
  },
  actions: {
    loadGeneral(context, payload) {
      context.commit('loadGeneral', payload);
    }
  },
  getters: {
    ciudades(state) {
      return state.general.ciudades;
    },
    categorias(state) {
      return state.general.categorias;
    }
  },
  modules: {
  }
})
