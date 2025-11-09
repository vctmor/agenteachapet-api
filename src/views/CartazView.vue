<template>
  <div class="cartaz">
    <div v-if="data" class="cartaz-box">
      <h1>🧭 Uma Jornada Começa!</h1>
      <h2>🐾 {{ data.pet.petName }} foi avistado pela última vez em {{ data.search.location }}</h2>
      <p>Convocamos toda a comunidade para apoiar <strong>{{ data.person.personName }}</strong>.</p>

      <img :src="data.pet.photo" alt="Pet que está sendo procurado" v-if="data" class="pet-image" />

      <p>
        <strong>{{ data.pet.petName }} está fora do seu lar desde:</strong>
        {{ formatDate(data.search.disappearanceDate) }}
      </p>

      <div class="share-row">
        <p class="share-link">
          📣 Compartilhe o cartaz em todas as suas redes: <a :href="link" target="_blank">{{ link }}</a>
        </p>
        <button @click="linkCopy">{{ copied ? 'Copiado!' : 'Copiar Link' }}</button>
      </div>

      <!-- <button @click="shareOnWhatsApp" class="whatsapp-btn">Compartilhar via WhatsApp</button> -->
    </div>

    <div v-else class="not-found">
      <p>Cadastro não encontrado.</p>
    </div>
  </div>
</template>


<script setup>
import { useRoute } from 'vue-router'
import { useRegister } from '@/composables/useRegister'
import { ref, onMounted } from 'vue'

const route = useRoute()
const { findById } = useRegister()
const data = findById(route.params.id)

const link = ref('')
const copied = ref(false)

onMounted(() => {
  link.value = window.location.href
})

function linkCopy() {

  navigator.clipboard.writeText(link.value).then(() => {

    copied.value = true
    setTimeout(() => copied.value = false, 2000)

  }).catch(err => {
    console.error('Erro ao copiar link: ', err)
  })
}


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
.cartaz {
  display: flex;
  justify-content: center;
  padding: 2rem;
  background-color: #e6f0e6; /* verde-claro Oxóssi */
  font-family: sans-serif;
}

.cartaz-box {
  background-color: #f8fff8;
  border: 1px solid #709775;
  border-radius: 12px;
  padding: 2rem;
  max-width: 700px;
  width: 100%;
  box-shadow: 0 0 10px rgba(73, 121, 92, 0.2); /* sombra leve verde */
  text-align: center;
}

.cartaz-box h1, .cartaz-box h2 {
  color: #49795c;
}

.pet-image {
  max-width: 700px;
  margin: 1rem auto;
  display: block;
  border-radius: 10px;
  border: 2px solid #709775;
}

.share-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #dfe6df;
  padding: 0.5rem 1rem;
  margin-top: 1rem;
  border-radius: 6px;
}

.share-link a {
  color: #35684c;
  text-decoration: none;
}

.share-row button {
  background-color: #49795c;
  color: white;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
}

.share-row button:hover {
  background-color: #35684c;
}

.whatsapp-btn {
  margin-top: 1rem;
  background-color: #25D366; /* cor do WhatsApp */
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}

.whatsapp-btn:hover {
  background-color: #1ebe5d;
}

.not-found {
  text-align: center;
  font-style: italic;
  color: #6b4e3d; /* tom terroso de Oxóssi */
}
</style>

