import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state() {
    const token = localStorage.getItem("token");
    const rol = localStorage.getItem("rol");
    const userId = localStorage.getItem("userId");
    return {
      general: {
        ciudades: [],
        categorias: []
      },
      userId: userId,
      user: token,
      rol: rol
    }
  },
  mutations: {
    loadGeneral(state, payload) {
      state.general = payload;
    },
    signup(state, payload) {
      state.user = payload.token;
      state.userId = payload.usuarioId;
      state.rol = payload.rol;
      localStorage.setItem("token", payload.token);
      localStorage.setItem("userId", payload.usuarioId);
      localStorage.setItem("rol", payload.rol);
    },
    logout(state) {
      state.user = null;
      state.userId = null;
      state.rol = null;
      localStorage.removeItem("userId");
      localStorage.removeItem("token");
      localStorage.removeItem("rol");
    }
  },
  actions: {
    loadGeneral(context, payload) {
      context.commit('loadGeneral', payload);
    },
    signup(context, payload) {
      context.commit('signup', payload);
    },
    logout(context) {
      context.commit('logout');
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
