
import { createRouter, createWebHistory} from 'vue-router'
import FormPet from '@/views/PetForm.vue'
import PetList from '@/views/PetList.vue'

const routes = [
  { path: '/', name: 'FormPet', component: FormPet },
  { path: '/pets', name: 'PetList', component: PetList}
]

export default createRouter({

  history: createWebHistory(),
  routes
})
