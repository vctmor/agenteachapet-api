
import { createRouter, createWebHistory} from 'vue-router'
import PetForm from '@/views/PetForm.vue'
import PetList from '@/views/PetList.vue'
import Cartaz from '@/views/Cartaz.vue'
import Testes from '@/views/Testes.vue'

import FormView from '@/views/prototipo/FormView.vue'
import CartazView from '@/views/prototipo/CartazView.vue'
import ListView from '@/views/prototipo/ListView.vue'

const routes = [
  { path: '/',
    name: 'PetForm',
    component: PetForm },

  { path: '/pets',
    name: 'PetList',
    component: PetList},

  { path: '/cartaz/:slug',
    name: 'cartaz',
    component: Cartaz,},

  { path: '/testes',
    name: 'testes',
    component: Testes },

  { path: '/prototipo',
    name: 'prototipo',
    component: FormView },

  { path: '/prototipo/cartaz/:id',
    name: 'cartaz',
    component: CartazView },

  { path: '/prototipo/listar',
    name: 'listar',
    component: ListView },
]

export default createRouter({

  history: createWebHistory(),
  routes
})
