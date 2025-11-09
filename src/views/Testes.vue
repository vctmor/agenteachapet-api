<template>
  <div class="share-box">
    <p>📣 Compartilhe: <a :href="link" target="_blank">{{ link }}</a></p>
    <button @click="copiarLink">{{ copiado ? 'Copiado!' : 'Copiar link' }}</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const link = ref('https://exemplo.com/meu-link')
const copiado = ref(false)

function copiarLink() {

  navigator.clipboard.writeText(link.value)
    .then(() => {
      copiado.value = true
      setTimeout(() => copiado.value = false, 2000)
    })
}
</script>

<style scoped>
.share-box {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

button {
  padding: 0.4rem 0.8rem;
  font-size: 0.9rem;
  border: none;
  background-color: #42b983;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}
</style>
