<template>
  <div class="pet-list">
    <!-- <Header /> -->
    <h2>Pets Cadastrados</h2>
    <div v-if="pets.length === 0">Nenhum pet encontrado.</div>
    <ul>
      <li v-for="pet in pets" :key="pet.id">
        <strong>{{ pet.breed }}</strong> - {{ pet.breed }} - {{ pet.color }}<br />
        <img :src="`/api/${pet.id}/image/`" class="pet-image" />
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

    pets.value = res.data

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
