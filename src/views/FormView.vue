<template>
  <div class="form-container">

    <h2>🎯 Ative uma Jornada de Busca</h2>

      <p>Sigamos no encalço! - <strong>AgenteAchaPet!</strong></p>
    <form @submit.prevent="register">

     <h3>👤 Etapa 1: Quem está convocando a busca!?</h3>

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
          placeholder="Por exemplo comorbidades, se toma remédios..."></textarea>
      </div>

      <h3>📍 Etapa 3: Quando e onde foi visto pela última vez?</h3>
      <input v-model="disappearanceDate" type="datetime-local" required />
      <input v-model="location" placeholder="Endereço aproximado." required />

      <div>
        <label for="additionalNotes">Mais detalhes que possam ajudar na busca</label>
      <textarea id="additionalNotes" v-model="additionalNotes"
        placeholder="Mas com empatia, não vamos impressionar nem angustiars a pessoa tutora, certo!?"></textarea>

      </div>

      <input type="file" @change="loadImage" accept="image/*"  />
      <img v-if="preview" :src="preview" alt="Prévie da imagem" class="preview-img" />

      <button type="button" @click="exampleRegistration">Preencher com Exemplo</button>

      <button type="submit">Cadastrar</button>
    </form>
  </div>
</template>

<script setup>


import { useRouter } from 'vue-router'
import { useRegister } from '@/composables/useRegister'
import {
  personName,
  phone,
  email,
  reporterRole,
  disappearanceDate,
  location,
  additionalNotes,
  specialNeedDescription,
  petName,
  breed,
  color,
  age,
  image,
  preview
} from '@/composables/formState'

import { exampleRegistration } from '@/composables/exampleRegistration'

const { save } = useRegister()
const router = useRouter()

function loadImage(event) {

  let file = event.target.files[0]
  file = image.value

  if (file){
    image.value = file
    preview.value = URL.createObjectURL(file)
  }
}


// exampleRegistration()

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
/* Centraliza o formulário na página */
.form-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 600px;
  margin: 3rem auto;
  padding: 2rem;
  background-color: #e6f0e6; /* Verde claro (Oxóssi) */
  border-radius: 12px;
  box-shadow: 0 0 12px rgba(0, 64, 0, 0.2);
  font-family: sans-serif;
}

h3{
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* Estilização dos blocos de entrada */
.form-container div {
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-bottom: 1rem;
}

/* Label com destaque */
label {
  margin-bottom: 0.5rem;
  font-size: 15px;
  font-weight: bold;
  color: #2f4f2f;
}

/* Estilo unificado para inputs e selects */
input,
select,
textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #709775; /* tom de folha */
  border-radius: 6px;
  background-color: #f8fff8;
  box-sizing: border-box;
}

/* Textarea com altura aumentada */
textarea {
  height: 150px;
  resize: vertical;
}

/* Botão de envio */
button {
  align-self: flex-start;
  padding: 10px 20px;
  background-color: #49795c; /* verde profundo Oxóssi */
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

/* Hover no botão */
button:hover {
  background-color: #35684c;
}

/* Pré-visualização da imagem */
.image-preview img {
  max-width:600px;
  border-radius: 10px;
  margin-top: 10px;

  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>

