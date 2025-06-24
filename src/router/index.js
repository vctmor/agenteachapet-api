
import { createRouter, createWebHistory} from 'vue-router'

import Testes from '@/views/Testes.vue'

import FormView from '@/views/FormView.vue'
import CartazView from '@/views/CartazView.vue'
import ListView from '@/views/ListView.vue'

const routes = [

  { path: '/testes',
    name: 'testes',
    component: Testes },

  { path: '/',
    name: 'formView',
    component: FormView },

  { path: '/cartaz/:id',
    name: 'cartaz',
    component: CartazView },

  { path: '/listar',
    name: 'listar',
    component: ListView },
]

export default createRouter({

  history: createWebHistory(),
  routes
})
