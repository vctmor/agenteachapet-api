<template>
  <div class="form">
    <h2>
      Registrar uma bussca
    </h2>
    <form @submit.prevent="register">
      <input type="text" v-model="name" placeholder="Nome do pet" required />
      <input type="tel" v-model="phone" placeholder="Telefone para contato" required />

      <input type="file" @change="loadImage" accept="image/*"  />
      <img v-if="preview" :src="preview" alt="Prévie da imagem" class="preview-img" />

      <button type="submit">Cadastrar</button>
    </form>
  </div>
</template>

<script setup>

import {ref} from 'vue'
import { useRouter } from 'vue-router'
import { useRegister } from '@/composables/useRegister'

const name = ref('')
const phone = ref('')
const image = ref(null)
const preview = ref(null)

const { save } = useRegister()
const router = useRouter()

function loadImage(event) {

  const file = event.target.files[0]

  if (file){
    image.value = file
    preview.value = URL.createObjectURL(file)
  }
}

function register() {

  const reader = new FileReader()
  reader.onload = () => {

    const newRegister = {
      name: name.value,
      phone: phone.value,
      image: reader.result,
    }

    const id = save(newRegister)
    router.push({name: 'cartaz', params: { id }})
  }

  if (image.value){

    reader.readAsDataURL(image.value)
  }
}
</script>

<style scoped>
.preview-img {
  max-width: 200px;
  margin-top: 1rem;
}
</style>
