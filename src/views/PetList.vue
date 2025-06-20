<template>
  <div class="pet-list">
    <!-- <Header /> -->
    <h2>Pets Cadastrados</h2>
    <div v-if="pets.length === 0">Nenhum pet encontrado.</div>
    <ul>
      <li v-for="pet in pets" :key="pet.id">
        <strong>{{ pet.petName }}</strong> - {{ pet.slug }} - {{ pet.color }}- {{ pet.breed }}<br />
        <img :src="`data:image/jpeg;base64,${pet.photo}`" class="pet-image" />
      </li>
    </ul>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'
// import Header from '@/components/Header.vue'

const pets = ref([])

onMounted(async () => {

   try {
    const res = await axios.get('/api/pet-searches')
    console.log('Resposta completa:', res.data)
  } catch (error) {
    console.error('Erro ao buscar pets:', error)
  }

  try {
    const res = await axios.get('/api/pet-searches')
    console.log('Tipo de res.data:', Array.isArray(res.data))  // deve ser true
console.log('Conteúdo de res.data:', res.data)


    // Teste se é array
    if (Array.isArray(res.data)) {
      pets.value = res.data
    } else if (Array.isArray(res.data.content)) {
      pets.value = res.data.content
    } else {
      console.warn('Formato inesperado:', res.data)
    }

  } catch (error) {
    console.error('Erro ao buscar pets:', error)
  }
})

</script>

<style scoped>
.pet-image {
  max-height: 150px;
  margin-top: 0.5rem;
}
</style>


