<template>
  <form @submit.prevent="submitForm" class="form-container">

    <h2>🎯 Ative uma Jornada de Busca</h2>

      <p>Seguimos no encalço! - <strong>AgenteAchaPet!</strong></p>

    <h3>👤 Etapa 1: Quem está convocando a travessia!?</h3>
    <input v-model="person.name" placeholder="Como prefere que te chame" required />

    <select v-model="search.reporterRole" required>
      <option disabled value="" >Qual vai ser seu papel nesta jornada? </option>
      <option value="TUTOR">Tutor</option>
      <!-- <option value="BASTIAN">Bastião: estou contigo, mobilizado ativamente na busca </option> -->
      <option value="SENTINEL">Sentinela: este bichinho que avistei parece perdido, não posso acolher, mas estou vigilante!</option>
      <option value="RESCUER">Guardião: ele está comigo, morrendo de saudades da sua pessoa Tutora</option>
    </select>

    <input v-model="person.phone" placeholder="Seu melhor contato celular" required />
    <input v-model="person.email" placeholder="O e-mail que você mais acessa" required />

    <h3>🐾 Etapa 2: Quem estamos buscando?</h3>
    <input v-model="pet.name" placeholder="atende por..." required />
    <input v-model="pet.color" placeholder="Cor" required />
    <input v-model="pet.breed" placeholder="Raça" required />
    <input v-model.number="pet.age" placeholder="Idade" type="number" required />
    <div>
      <label for="necessidades">Tem necessidades especiais?</label>
      <textarea id="necessidades" v-model="search.specialNeed.description" placeholder="Como comorbidades, se toma remédios..."></textarea>
    </div>
    <h3>📍 Etapa 3: Quando e onde foi visto pela última vez?</h3>
    <input v-model="search.disappearanceDate" type="datetime-local" required />
    <input v-model="search.location" placeholder="Endereço aproximado." required />

    <label for="additionalNotes">Conte-nos mais detalhes que possam ajudar na empreitada</label>
    <textarea id="additionalNotes" v-model="search.additionalNotes" placeholder="Mas com empatia, não vamos impressionar nem angustiars a pessoa tutora, certo!?"></textarea>

    <div>
        <label for="photo">Foto do Pet:</label>
        <input id="photo" type="file" accept="image/*" @change="previewImage" required />
    </div>

    <div v-if="preview" class="image-preview">
      <img :src="preview" alt="Prévia da imagem" />
    </div>

    <div>
  <label for="button">Respira! Vamos juntos?!!?!?</label>
  <button id="button" type="submitForm" onclick="this.innerText='O caminho se faz caminhando!'">JÁ!</button>
</div>
  </form>
</template>

<script>

export default {
  data() {
    return {
      person: {
        name: "",
        phone: "",
        email: ""
      },
      pet: {
        name: "",
        color: "",
        breed: "",
        age: null
      },
      search: {
        reporterRole: "",
        disappearanceDate: "",
        location: "",
        additionalNotes: "",
        specialNeed: {
          description: ""  // novo campo para enviar a necessidade especial
        }
      },
      image: null,
      preview: null
    };
  },
  methods: {
    previewImage(event) {
      const file = event.target.files[0];
      if (file) {
        this.image = file;
        this.preview = URL.createObjectURL(file);
      }
    },
     async openCartaz(response) {

      const result = await response.json();
      const url = `/cartaz/${result.slug}`;

      window.open(url, "_blank");
    },
      async submitForm() {

        document.getElementById('button').innerText = 'O caminho se faz caminhando!';

        const formData = new FormData();

        const data = {
          person: { ...this.person },
          pet: { ...this.pet },
          search: {
          reporterRole: this.search.reporterRole,
          disappearanceDate: this.search.disappearanceDate,
          location: this.search.location,
          specialNeed: this.search.specialNeed,
          additionalNotes: this.search.additionalNotes

          }
};

      formData.append("data", new Blob([JSON.stringify(data)], {
        type: "application/json"
      }));

      formData.append("photo", this.image);

      try {

      const  response = await fetch("http://localhost:8080/pet-searches", {

          method: "POST",
          body: formData,
          headers: {
            Accept: "application/json"
          }
        });

        if (!response.ok) {

          const errText = await response.text();
          throw new Error("Erro ao cadastrar: " + errText);
        }

        alert("Cadastro realizado com sucesso");
        this.openCartaz(response);

      } catch (error) {

        console.error(error);
        alert("Erro ao cadastrar anúncio.");

      }

    }
  }
};
</script>


<style scoped>

  /* Coloca o label acima e estiliza */
  div {
    display: flex;
    flex-direction: column;
    margin-bottom: 1rem;
  }

  label {
    margin-bottom: 0.5rem;
    font-size: 15px;
    font-weight: bold;
  }

  textarea {
    width: 100%;       /* ou defina em px, ex: 400px */
    height: 150px;     /* aumenta a altura */
    padding: 10px;
    margin-bottom: 0.5rem;
    font-size: 16px;
    resize: vertical;  /* permite redimensionamento vertical */
  }


.form-container {
  max-width: 500px;
  margin: auto;
  padding: 20px;
  background-color: #e6f0e6;
  /* verde claro Oxóssi */
  border-radius: 12px;
  box-shadow: 0 0 8px rgba(0, 64, 0, 0.2);
  font-family: sans-serif;
}

input,
select {
  display: block;
  width: 100%;
  padding: 10px;
  margin-top: 8px;
  margin-bottom: 12px;
  border: 1px solid #709775;
  /* tom de folha */
  border-radius: 6px;
  background-color: #f8fff8;
}

button {
  padding: 10px 20px;
  background-color: #49795c;
  /* verde profundo Oxóssi */
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

button:hover {
  background-color: #35684c;
}

.image-preview img {
  max-width: 100px;
  border-radius: 10px;
  margin-top: 10px;
}
</style>
