<template>
  <div class="pet-list">
    <Header />
    <h2>Pets Cadastrados</h2>
    <div v-if="pets.length === 0">Nenhum pet encontrado.</div>
    <ul>
      <li v-for="pet in pets" :key="pet.id">
        <strong>{{ pet.name }}</strong> - {{ pet.breed }} - {{ pet.color }}<br />
        <img :src="`http://localhost:8080/pets/imagem/${pet.imagePath}`" class="pet-image" />
      </li>
    </ul>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'
import Header from '@/components/Header.vue'

const pets = ref([])

onMounted(async () => {
  const res = await axios.get('http://localhost:8080/pets')
  pets.value = res.data
})
</script>

<style scoped>
.pet-image {
  max-height: 150px;
  margin-top: 0.5rem;
}
</style>
