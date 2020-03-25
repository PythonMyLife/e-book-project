import Vue from 'vue'
import App from './App.vue'
import './plugins/element.js'
import router from './router';
import './css/style.css'
//import axios from 'axios'

Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
