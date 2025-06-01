<template>
  <form @submit.prevent="submitForm" class="form-container">
    <h2>Anunciar Pet Desaparecido</h2>

    <h3>Dados do Tutor</h3>
    <input v-model="person.name" placeholder="Nome do Tutor" required />
    <input v-model="person.phone" placeholder="Telefone" required />

    <h3>Dados do Pet</h3>
    <input v-model="pet.name" placeholder="Nome do Pet" required />
    <input v-model="pet.color" placeholder="Cor" required />
    <input v-model="pet.breed" placeholder="Raça" required />
    <input v-model.number="pet.age" placeholder="Idade" type="number" required />

    <h3>Dados do Desaparecimento</h3>
    <select v-model="search.reporterRole" required>
      <option disabled value="">Quem está reportando?</option>
      <option value="TUTOR">Tutor</option>
      <option value="ADVERTISER">Anunciante</option>
    </select>

    <input v-model="search.disappearanceDate" type="datetime-local" required />
    <input v-model="search.location" placeholder="Local do desaparecimento" required />
    <textarea v-model="search.additionalNotes" placeholder="Observações adicionais"></textarea>

    <label for="photo">Foto do Pet:</label>
    <input id="photo" type="file" accept="image/*" @change="previewImage" required />

    <div v-if="preview" class="image-preview">
      <img :src="preview" alt="Prévia da imagem" />
    </div>

    <button type="submit">Cadastrar Anúncio</button>
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

      const petSearchDTO = {
        pet: { ...this.pet },
        registeredBy: { ...this.person },
        reporterRole: this.search.reporterRole,
        disappearanceDate: this.search.disappearanceDate,
        location: this.search.location,
        additionalNotes: this.search.additionalNotes
      };

      formData.append("petSearchDTO", new Blob([JSON.stringify(petSearchDTO)], {
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
