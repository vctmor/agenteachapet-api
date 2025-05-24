<template>
  <div>
    <h2>Pets cadastrados</h2>
    <div v-if="pets.length === 0">Nenhum pet encontrado.</div>
    <ul>
      <li v-for="pet in pets" :key="pet.id" style="margin-bottom: 20px">
        <strong>{{ pet.name }}</strong> - {{ pet.breed }} - {{ pet.color }}<br />
        <img :src="`http://localhost:8080/pets/imagem/${pet.image}`" alt="Foto do Pet" />

      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const pets = ref([])

async function loadPets() {
  try {
    const res = await axios.get('http://localhost:8080/pets')
    console.log(res.data)
    pets.value = res.data
  } catch (err) {
    console.error('Erro ao carregar pets', err)
  }
}

onMounted(() => {
  loadPets()
})
</script>
