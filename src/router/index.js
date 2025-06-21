
import { createRouter, createWebHistory} from 'vue-router'
import FormPet from '@/views/PetForm.vue'
import PetList from '@/views/PetList.vue'
import Cartaz from '@/views/Cartaz.vue'
import Testes from '@/views/Testes.vue'

const routes = [
  { path: '/',
    name: 'FormPet',
    component: FormPet },

  { path: '/pets',
    name: 'PetList',
    component: PetList},

  { path: '/cartaz/:slug',
    name: 'Cartaz',
    component: Cartaz},

  { path: '/testes',
    name: 'testes',
    component: Testes }
]

export default createRouter({

  history: createWebHistory(),
  routes
})
