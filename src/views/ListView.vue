<template>
  <div class="listagem">
    <h2>Buscas Ativas</h2>
    <div v-if="list.length === 0">Nenhum cadastro ainda.</div>

    <div v-for="data in list" :key="data.id" class="registro">
      <img :src="data.pet.photo" class="preview-img" />

      <div class="info">
        <p><strong>Nome do pet:</strong> {{ data.pet.petName }}</p>
        <p><strong>Nome do tutor/relator:</strong> {{ data.person.personName }}</p>
        <p><strong>Papel do relator:</strong> {{ data.search.reporterRole }}</p>
        <p><strong>Data do último avistamento:</strong> {{ formatDate(data.search.disappearanceDate) }}</p>
        <p><strong>Local do último avistamento:</strong> {{ data.search.location }}</p>
      </div>

      <div class="acoes">
        <router-link :to="`/cartaz/${data.id}`">📄 Visualizar Cartaz de Busca</router-link>
        <!-- <button @click="remove(data.id, data.pet.petName)">❌ Apagar</button> -->
      </div>
    </div>
  </div>
</template>


<script setup>
import { computed } from 'vue'
import { useRegister } from '@/composables/useRegister';

const { listing} = useRegister()
const list = computed(() => listing())

function formatDate(dateString) {

  if (!dateString) return ''

  const date = new Date(dateString)
  const day = String(date.getDate()).padStart(2, '0')
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const year = String(date.getFullYear())

  return `${day}/${month}/${year}`
}


</script>

<style scoped>
.listagem {
  max-width: 800px;
  margin: auto;
  font-family: sans-serif;
  padding: 1rem;
  color: #2f4f2f;
}

h2 {
  text-align: center;
  color: #35684c;
  margin-bottom: 1.5rem;
}

.registro {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
  background-color: #f4fbf4;
  padding: 1rem;
  border-radius: 10px;
  box-shadow: 0 0 6px rgba(0, 64, 0, 0.1);
  align-items: center;
}

.preview-img {
  width: 90px;
  height: 90px;
  object-fit: cover;
  border-radius: 8px;
  border: 2px solid #709775;
}

.info {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.acoes {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 0.5rem;
}

button {
  background-color: #49795c;
  color: white;
  padding: 6px 10px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

button:hover {
  background-color: #355e46;
}

a {
  color: #2e6c49;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
