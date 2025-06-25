<template>
  <div class="form">

    <h2>🎯 Ative uma Jornada de Busca</h2>

      <p>Seguimos no encalço! - <strong>AgenteAchaPet!</strong></p>
    <form @submit.prevent="register">

     <h3>👤 Etapa 1: Quem está convocando a travessia!?</h3>

      <input type="text" v-model="personName" placeholder="Seu nome" required />

      <select v-model="reporterRole" required>
        <option disabled value="">Qual vai ser seu papel nesta jornada? </option>
        <option value="TUTOR">Tutor</option>
        <option value="SENTINEL">Sentinela</option>
        <option value="RESCUER">Guardião</option>
      </select>

      <input type="tel" v-model="phone" placeholder="Seu melhor contato celular" required />
      <input type="email" v-model="email" placeholder="O e-mail que você mais acessa" required />

      <h3>🐾 Etapa 2: Quem estamos buscando?</h3>
      <input v-model="petName" placeholder="Atende por..." required />
      <input v-model="color" placeholder="Cor" required />
      <input v-model="breed" placeholder="Raça" required />
      <input v-model="age" type="number" placeholder="Idade" required />

      <div>
        <label for="necessidades">Tem necessidades especiais?</label>
        <textarea id="necessidades" v-model="specialNeedDescription"
          placeholder="Como comorbidades, se toma remédios..."></textarea>
      </div>

      <h3>📍 Etapa 3: Quando e onde foi visto pela última vez?</h3>
      <input v-model="disappearanceDate" type="datetime-local" required />
      <input v-model="location" placeholder="Endereço aproximado." required />

      <label for="additionalNotes">Mais detalhes que possam ajudar</label>
      <textarea id="additionalNotes" v-model="additionalNotes"
        placeholder="Mas com empatia, não vamos impressionar nem angustiars a pessoa tutora, certo!?"></textarea>

      <input type="file" @change="loadImage" accept="image/*"  />
      <img v-if="preview" :src="preview" alt="Prévie da imagem" class="preview-img" />

      <button type="submit">Cadastrar</button>
    </form>
  </div>
</template>

<script setup>

import {ref} from 'vue'
import { useRouter } from 'vue-router'
import { useRegister } from '@/composables/useRegister'

// Variáveis reativas
const personName = ref('')
const phone = ref('')
const email = ref('')

const reporterRole = ref('')
const disappearanceDate = ref('')
const location = ref('')
const additionalNotes = ref('')
const specialNeedDescription = ref('')

const petName = ref('')
const breed = ref('')
const color = ref('')
const age = ref(null)


const image = ref(null)
const preview = ref(null)

const { save } = useRegister()
const router = useRouter()

function loadImage(event) {

  const file = event.target.files[0]

  if (file){
    image.value = file
    preview.value = URL.createObjectURL(file)
  }
}

function register() {

  const reader = new FileReader()

  reader.onload = () => {
    const newRegister = {
      search: {
      reporterRole: reporterRole.value,
      disappearanceDate: disappearanceDate.value,
      location: location.value,
      additionalNotes: additionalNotes.value,
      specialNeed: {
        description: specialNeedDescription.value
        }
      },
      person: {
        personName: personName.value,
        phone: phone.value,
        email: email.value
      },
      pet: {
        petName: petName.value,
        breed: breed.value,
        color: color.value,
        age: age.value,
        photo: reader.result
      }
    }

    const id = save(newRegister)
    router.push({ name: 'cartaz', params: { id } })
  }

  if (image.value){

    reader.readAsDataURL(image.value)
  }
}



</script>

<style scoped>
.preview-img {
  max-width: 200px;
  margin-top: 1rem;
}
</style>
