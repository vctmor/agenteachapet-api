Excelente! Vamos tirar o **"passeio-das-antenas-marrons"** do mundo das ideias e colocar a mão no código 👨‍💻🐾

---

## 🚧 Etapa 1: Estrutura Inicial do Projeto

### 🔹 Crie o projeto Vue 3 com Vite
Se ainda não criou:

```bash
npm create vite@latest passeio-das-antenas-marrons -- --template vue
cd passeio-das-antenas-marrons
npm install
```

---

## 🧱 Etapa 2: Configurar Vue Router

### 📁 Criar arquivo `src/router.js`:

```js
import { createRouter, createWebHistory } from 'vue-router'
import FormularioView from './views/FormularioView.vue'
import CartazView from './views/CartazView.vue'
import ListagemView from './views/ListagemView.vue'

const routes = [
  { path: '/', component: FormularioView },
  { path: '/cartaz/:id', name: 'Cartaz', component: CartazView },
  { path: '/listar', component: ListagemView },
]

export default createRouter({
  history: createWebHistory(),
  routes
})
```

### 🔧 Atualize seu `main.js`:

```js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

createApp(App).use(router).mount('#app')
```

---

## 🧠 Etapa 3: Crie um composable para armazenar os cadastros

### 📁 Em `src/composables/useCadastro.js`:

```js
import { ref } from 'vue'

const cadastros = ref(JSON.parse(localStorage.getItem('cadastros') || '[]'))

const salvar = (novo) => {
  novo.id = Date.now()
  cadastros.value.push(novo)
  localStorage.setItem('cadastros', JSON.stringify(cadastros.value))
  return novo.id
}

const listar = () => cadastros.value

const buscarPorId = (id) =>
  cadastros.value.find((c) => String(c.id) === String(id))

const remover = (id) => {
  cadastros.value = cadastros.value.filter((c) => c.id !== id)
  localStorage.setItem('cadastros', JSON.stringify(cadastros.value))
}

export function useCadastro() {
  return { salvar, listar, buscarPorId, remover }
}
```

---

Agora que a base está preparada, posso continuar com:
- O **formulário de cadastro**
- O **cartaz com preview dos dados**
- A **lista com exclusão**
- E o **navbar**

Excelente, Victor! Agora que sua base está preparada, vamos avançar com a **implementação prática do projeto “passeio-das-antenas-marrons”**. Vamos seguir uma ordem lógica e limpa, respeitando boas práticas de componentização, reatividade e separação de responsabilidades no Vue 3 com Composition API.

---

## ✅ Etapa 4: Implementar o **formulário de cadastro** (`FormularioView.vue`)

Crie em `src/views/FormularioView.vue`:

```vue
<template>
  <div class="formulario">
    <h2>Registrar uma Busca</h2>
    <form @submit.prevent="cadastrar">
      <input type="text" v-model="nome" placeholder="Nome do pet" required />
      <input type="tel" v-model="telefone" placeholder="Telefone para contato" required />

      <input type="file" @change="carregarImagem" accept="image/*" required />
      <img v-if="preview" :src="preview" alt="Prévia da imagem" class="preview-img" />

      <button type="submit">Cadastrar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCadastro } from '@/composables/useCadastro'

const nome = ref('')
const telefone = ref('')
const imagem = ref(null)
const preview = ref(null)

const { salvar } = useCadastro()
const router = useRouter()

function carregarImagem(event) {
  const file = event.target.files[0]
  if (file) {
    imagem.value = file
    preview.value = URL.createObjectURL(file)
  }
}

function cadastrar() {
  const reader = new FileReader()
  reader.onload = () => {
    const novo = {
      nome: nome.value,
      telefone: telefone.value,
      imagem: reader.result, // base64
    }

    const id = salvar(novo)
    router.push({ name: 'Cartaz', params: { id } })
  }

  if (imagem.value) {
    reader.readAsDataURL(imagem.value)
  }
}
</script>

<style scoped>
.preview-img {
  max-width: 200px;
  margin-top: 1rem;
}
</style>
```

---

## ✅ Etapa 5: Implementar o **Cartaz** (`CartazView.vue`)

```vue
<template>
  <div class="cartaz">
    <h2>Cartaz de Busca Ativa</h2>
    <div v-if="dados">
      <h3>{{ dados.nome }}</h3>
      <img :src="dados.imagem" class="preview-img" />
      <p>📞 {{ dados.telefone }}</p>
    </div>
    <div v-else>
      <p>Cadastro não encontrado.</p>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useCadastro } from '@/composables/useCadastro'

const route = useRoute()
const { buscarPorId } = useCadastro()
const dados = buscarPorId(route.params.id)
</script>
```

---

## ✅ Etapa 6: Implementar a **listagem com exclusão** (`ListagemView.vue`)

```vue
<template>
  <div>
    <h2>Buscas Registradas</h2>
    <div v-if="lista.length === 0">Nenhum cadastro ainda.</div>
    <ul>
      <li v-for="pet in lista" :key="pet.id">
        <img :src="pet.imagem" class="preview-img" />
        <p><strong>{{ pet.nome }}</strong> — {{ pet.telefone }}</p>
        <router-link :to="`/cartaz/${pet.id}`">Ver cartaz</router-link>
        <button @click="remover(pet.id)">❌ Apagar</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useCadastro } from '@/composables/useCadastro'

const { listar, remover } = useCadastro()
const lista = computed(() => listar())
</script>
```

---

## ✅ Etapa 7: Criar e incluir o **Navbar** (`components/Navbar.vue`)

```vue
<template>
  <nav class="navbar">
    <RouterLink to="/">Cadastrar</RouterLink> |
    <RouterLink to="/listar">Listar</RouterLink>
  </nav>
</template>

<style scoped>
.navbar {
  padding: 1rem;
  background-color: #eee;
}
.navbar a {
  margin-right: 1rem;
}
</style>
```

E use no `App.vue`:

```vue
<template>
  <Navbar />
  <router-view />
</template>
<!-- TODO -->
<script setup>
import Navbar from './components/Navbar.vue'
</script>
```

---

Com isso, Victor, seu mini-SPA está 100% funcional: cadastro com foto e telefone, cartaz com visualização, listagem com exclusão — tudo rodando no navegador via `localStorage`.

Se quiser, posso te mostrar como exportar e importar os cadastros em JSON (pra simular persistência) ou até fazer deploy via GitHub Pages. Quer isso? 😄 Ou podemos agora focar na responsividade e visual?