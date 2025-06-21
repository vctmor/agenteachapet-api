<template>

  <div v-if="data">
    <h1>🧭 Uma Jornada Começa!</h1>
    <h2>🐾 {{ data.petName }} sumiu em {{ data.location }}</h2>
    <p>Convocamos todas as forças para ajudar {{ data.personName }}.</p>
    <img :src="photoUrl" alt="Pet desaparecido" v-if="data.photo"/>
    <p><strong>Está fora do seu lar desde:</strong> {{ formatDate(data.disappearanceDate) }}</p>
    <div class="share-box">
      <p>📣 Compartilhe: <a :href="link" target="_blank">{{ link }}</a></p>
      <button @click="linkCopy">{{ copied ? 'Copiado!' : 'Copiar Link' }}</button>
    </div>
    <button @click="shareOnWhatsApp">Compartilhar via WhatsApp</button>
  </div>
</template>

<script>

import { ref } from 'vue'

export default {
  data() {
    return {
      data: null,
      copied: false
    };
  },
  computed: {
    photoUrl() {
      if (this.data?.photo) {
        const base64 = btoa( //qual precisa ser o conteúdo de  Uint8Array() para poder usar a variavel base64 no lugar de ${this.data.photo}?
          new Uint8Array(this.data.photo.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
        );
        return `data:image/jpeg;base64,${this.data.photo}`;
      }
      return null;
    },
    slug(){
      return this.$route.params.slug
    },
    link(){
      return `${window.location.origin}/cartaz/${this.slug}`
    }
  },
  methods: {
    async fetchData() {
      const slug = this.$route.params.slug;
      const res = await fetch(`/api/pet-searches/slug/${slug}`);
      this.data = await res.json();
      console.log(this.data)
    },
    formatDate(dateString) {
      return new Date(dateString).toLocaleString();
    },
    shareOnWhatsApp() {
      const text = `🧭 Uma jornada começou!\n${this.data.petName} desapareceu em ${this.data.location}.\nAcesse o cartaz completo aqui: http://localhost:5173/cartaz/${this.data.slug}`;
      const link = `https://wa.me/?text=${encodeURIComponent(text)}`;
      window.open(link, '_blank');
    },
    linkCopy(){

      navigator.clipboard.writeText(this.link)
        .then(() => {
          this.copied = true
      setTimeout(() => {
        this.copied = false
      }, 2000)
        })

    }
  },
  mounted() {
    this.fetchData();

    console.log(this.fetchData.slug)
    console.log("Slug recebido:", this.$route.params.slug);
  }
};
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
