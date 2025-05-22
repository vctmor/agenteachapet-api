<template>
  <div class="container">
    <h1>BuscaPet</h1>
    <FormPet />
    <hr />
    <PetList />
  </div>
</template>

<script setup>
import FormPet from './components/FormPet.vue'
import PetList from './components/PetList.vue'
</script>

<style>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}
</style>
