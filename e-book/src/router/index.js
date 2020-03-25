import Vue from 'vue';
import Router from 'vue-router';
import Home from '../components/Home.vue'
import Login from '../components/login.vue'
import Regist from '../components/regist.vue'
import Subpage from '../components/subpage.vue'
import Cart from '../components/cart.vue'
import Manage from '../components/manage.vue'
import Order from '../components/order.vue'
import index from '../components/index.vue'
import books from '../components/books.vue'
import manager from '../components/manager.vue'
import userorder from '../components/userorder.vue'
import bookmanage from '../components/bookmanage.vue'
import statistics from '../components/statistics.vue'
import talk from '../components/talk.vue'


Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [{
        path: '*',
        redirect: '/index'
    },{
        path: '/index',
        name: 'index',
        component: index
    }, {
        path: '/userorder',
        name: 'userorder',
        component: userorder
    }, {
        path: '/manager',
        name: 'manager',
        component: manager
    }, {
        path: '/books',
        name: 'books',
        component: books
    },{
        path: '/home',
        name: 'Home',
        component: Home
    }, {
        path: '/login',
        name: 'login',
        component: Login
    }, {
        path: '/regist',
        name: 'regist',
        component: Regist
    }, {
        path: '/subpage',
        name: 'subpage',
        component: Subpage
    }, {
        path: '/cart',
        name: 'cart',
        component: Cart
    }, {
        path: '/manage',
        name: 'manage',
        component: Manage
    }, {
        path: '/order',
        name: 'order',
        component: Order
    }, {
        path: '/bookmanage',
        name: 'bookmanage',
        component: bookmanage
    }, {
        path: '/statistics',
        name: 'statistics',
        component: statistics
    }, {
        path: '/talk',
        name: 'talk',
        component: talk
    }]
});
