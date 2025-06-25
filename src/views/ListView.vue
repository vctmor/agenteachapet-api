<template>
<div>
  <h2>Buscas Registradas</h2>
  <div v-if="list.length === 0">Nenhum cadastro ainda.</div>
  <ul>
    <li v-for="data in list" :key="data.id">
      <img :src="data.pet.photo" class="preview-img" />
      <p><strong>{{ data.pet.petName }}</strong> - {{ data.person.personName }}</p>
      <router-link :to="`/cartaz/${data.id}`">Ver Cartaz</router-link>

      <button @click="remove(data.id, data.pet.petName)">❌ Apagar</button>
    </li>
  </ul>
</div>
</template>

<script setup>
import { computed } from 'vue'
import { useRegister } from '@/composables/useRegister';

const { listing, remove } = useRegister()
const list = computed(() => listing())
</script>
