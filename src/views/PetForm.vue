<template>
  <form @submit.prevent="submitForm" class="form-container">

    <h2>🎯 Comece sua Jornada de Busca ou apoio</h2>
    <p>Vamos torcer para que ele tenha ido dormir dentro de alguma gaveta, ou esteja dentro do sofá.</p>
    <p>Mas enquanto não  temos certeza, <strong>vamos dar início à jornada</strong></p>

    <h3>👤 Etapa 1: Quem está convocando a travessia!?</h3>
    <input v-model="person.name" placeholder="Como prefere que te chame" required />
    <select v-model="search.reporterRole" required>
      <option disabled value="" >Qual vai ser seu papel nesta jornada? </option>
      <option value="TUTOR">Tutor</option>
      <option value="BASTIAN">Bastiã: estou contigo, mobilizado na busca ativa </option>
      <option value="SENTINEL">Sentinela: este bichinho parece perdido, não posso acolher, estou vigilante!</option>
      <option value="RESCUER">Guardião: ele está comigo, morrendo de saudades da sua pessoa Tutora</option>
    </select>

    <input v-model="person.phone" placeholder="Seu melhor contato celular" required />

    <h3>🐾 Etapa 2: Quem estamos buscando?</h3>
    <input v-model="pet.name" placeholder="atende por..." required />
    <input v-model="pet.color" placeholder="Cor" required />
    <input v-model="pet.breed" placeholder="Raça" required />
    <input v-model.number="pet.age" placeholder="Idade" type="number" required />

    <h3>📍 Etapa 3: Onde foi visto pela última vez?</h3>
    <input v-model="search.disappearanceDate" type="datetime-local" required />
    <input v-model="search.location" placeholder="Visto pela última vez em..." required />
    <textarea v-model="search.additionalNotes" placeholder="Observações adicionais"></textarea>
    <div>
        <label for="photo">Foto do Pet:</label>
        <input id="photo" type="file" accept="image/*" @change="previewImage" required />
    </div>

    <div v-if="preview" class="image-preview">
      <img :src="preview" alt="Prévia da imagem" />
    </div>

    <button type="submit">O caminho se faz ao caminhar</button>
  </form>
</template>

<script>

export default {
  data() {
    return {
      person: {
        name: "",
        phone: ""
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
        additionalNotes: ""
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
      async submitForm() {
        const formData = new FormData();

        const data = {
          person: { ...this.person },
          pet: { ...this.pet },
          search: {
          reporterRole: this.search.reporterRole,
          disappearanceDate: this.search.disappearanceDate,
          location: this.search.location,
          additionalNotes: this.search.additionalNotes
  }
};

      formData.append("data", new Blob([JSON.stringify(data)], {
        type: "application/json"
      }));

      formData.append("photo", this.image);

      try {
        const response = await fetch("http://localhost:8080/api/pet-searches", {
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

        alert("Anúncio cadastrado com sucesso!");
      } catch (error) {
        console.error(error);
        alert("Erro ao cadastrar anúncio.");
      }
    }
  }
};
</script>


<style scoped>
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
