<template>
  <div class="cartaz">
    <h2>Cartaz de Busca Ativa</h2>
    <div v-if="data">
      <h3>{{ data.pet.petName }}</h3>
      <img :src="data.pet.photo" class="preview-img"/>
      <p> {{ data.phone }}</p>
    </div>
    <div v-else>
      <p>Cadastro não encontrado.</p>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useRegister } from '@/composables/useRegister'

const route = useRoute()
const { findById } = useRegister()
const data = findById(route.params.id)

</script>

