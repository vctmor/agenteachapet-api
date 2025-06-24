<template>
<div>
  <h2>Buscas Registradas</h2>
  <div v-if="list.length === 0">Nenhum cadastro ainda.</div>
  <ul>
    <li v-for="pet in list" :key="pet.id">
      <img :src="pet.image" class="preview-img" />
      <p><strong>{{ pet.name }}</strong> - {{ pet.phone }}</p>
      <router-link :to="`/cartaz/${pet.id}`">Ver Cartaz</router-link>

      <button @click="remove(pet.id)">❌ Apagar</button>
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
