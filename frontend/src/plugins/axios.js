"use strict";

import Vue from 'vue';
import axios from "axios";

import router from '../router';
import store from '../store';

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
    baseURL: process.env.baseURL || process.env.apiUrl || (process.env.NODE_ENV === 'development' ? "http://localhost:8090/api/" : "/api/")
    // timeout: 60 * 1000, // Timeout
    // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
    function (config) {

        if (store.state.user !== null) {
            config.headers["Authorization"] = `Bearer ${store.state.user}`;
        }

        // Do something before request is sent
        return config;
    },
    function (error) {
        // Do something with request error
        return Promise.reject(error);
    }
);

// Add a response interceptor
_axios.interceptors.response.use(
    function (response) {
        // Do something with response data
        return response;
    },
    function (error) {

        if (error && error.response && error.response.data) {
            const statusCode = error.response.data.status;

            // La autentificación ha fallado y debe reiniciar su sesión o token.
            if (statusCode === 1500) {
                store.dispatch('logout').then(() => {
                    router.push('/entrar?error=' + error.response.data.message);
                });
                return;
            }

            if (statusCode === 2500) {
                const currentRoute = window.global_app.$router.currentRoute;
                if (currentRoute) {
                    const instance = currentRoute.matched[currentRoute.matched.length - 1].instances.default;
                    if (instance && instance.errors) {
                        instance.$set(instance.errors, error.response.data.field, error.response.data.message);
                    }
                }
                return;
            }

        }

        // Do something with response error
        return Promise.reject(error);
    }
);

Plugin.install = function (Vue) {
    Vue.axios = _axios;
    window.axios = _axios;
    Object.defineProperties(Vue.prototype, {
        axios: {
            get() {
                return _axios;
            }
        },
        $axios: {
            get() {
                return _axios;
            }
        },
    });
};

Vue.use(Plugin)

export default Plugin;
