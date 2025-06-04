Perfeito. A seguir, farei um **guia passo a passo** para aplicar os conceitos do código **(a)** — componente reutilizável `<InputField>` — dentro da sua aplicação **(b)**. O objetivo é transformar seu formulário em uma estrutura mais escalável, legível e sustentável.

---

## ✅ 1. Criar o componente reutilizável: `InputField.vue`

Crie um novo arquivo chamado `InputField.vue` na pasta `components/` do seu projeto:

```bash
src/
├── components/
│   └── InputField.vue
```

Copie e cole o conteúdo abaixo:

```vue
<!-- src/components/InputField.vue -->
<template>
  <div :class="['input-field', layoutClass]">
    <label :for="id">{{ label }}</label>
    <input
      :id="id"
      v-model="localValue"
      :placeholder="placeholder"
      :required="required"
      :type="type"
      class="input-element"
      @input="updateValue"
    />
  </div>
</template>

<script setup>
import { defineProps, defineEmits, computed, ref, watch } from 'vue'

const props = defineProps({
  modelValue: String,
  label: String,
  placeholder: String,
  required: Boolean,
  type: {
    type: String,
    default: 'text'
  },
  layout: {
    type: String,
    default: 'vertical',
    validator: value => ['vertical', 'horizontal'].includes(value)
  },
  id: {
    type: String,
    default: () => 'input-' + Math.random().toString(36).substr(2, 9)
  }
})

const emit = defineEmits(['update:modelValue'])

const localValue = ref(props.modelValue)

watch(
  () => props.modelValue,
  newVal => localValue.value = newVal
)

function updateValue(event) {
  emit('update:modelValue', event.target.value)
}

const layoutClass = computed(() =>
  props.layout === 'horizontal' ? 'input-horizontal' : 'input-vertical'
)
</script>

<style scoped>
.input-field {
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
}

.input-horizontal {
  flex-direction: row;
  align-items: center;
}

.input-horizontal label {
  margin-right: 0.5rem;
  margin-bottom: 0;
}

.input-element {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
}
</style>
```

---

## ✅ 2. Importar e usar `InputField` no seu formulário

Vamos modificar seu componente para **substituir os `<input>` comuns por `<InputField>`**.

### Antes de tudo:

Assumirei que você está usando Vue 3 com `<script setup>` e que seu componente atual está em `App.vue` ou outro componente pai. No topo do seu `<script setup>`, importe:

```vue
<script setup>
import { ref } from 'vue'
import InputField from './components/InputField.vue' // ajuste o caminho se necessário

const person = ref({
  name: '',
  phone: ''
})

const pet = ref({
  name: '',
  color: '',
  breed: '',
  age: null
})

const search = ref({
  reporterRole: '',
  disappearanceDate: '',
  location: '',
  additionalNotes: ''
})

const preview = ref(null)

function previewImage(event) {
  const file = event.target.files[0]
  if (file) {
    preview.value = URL.createObjectURL(file)
  }
}

function submitForm() {
  console.log("Dados enviados:", { person, pet, search })
}
</script>
```

---

### Agora modifique o template

Substitua os inputs simples pelos componentes `<InputField>`:

```vue
<template>
  <form @submit.prevent="submitForm" class="form-container">
    <h1>O choque inicial vai passando...</h1>
    <p>Vamos torcer para que ele tenha ido dormir dentro de alguma gaveta, ou esteja dentro do sofá.</p>
    <p>Mas enquanto não temos certeza, <strong>vamos dar início à jornada</strong></p>

    <h3>Seus dados</h3>
    <InputField 
      v-model="person.name" 
      label="Nome:" 
      placeholder="Como prefere que te chame" 
      :required="true"
    />
    <InputField 
      v-model="person.phone" 
      label="Telefone:" 
      placeholder="Seu melhor contato" 
      :required="true"
    />

    <h3>Dados do Pet</h3>
    <InputField 
      v-model="pet.name" 
      label="Nome do Pet:" 
      placeholder="Atende por..." 
      :required="true"
    />
    <InputField 
      v-model="pet.color" 
      label="Cor:" 
      placeholder="Cor do pet" 
      :required="true"
    />
    <InputField 
      v-model="pet.breed" 
      label="Raça:" 
      placeholder="Raça do pet" 
      :required="true"
    />
    <InputField 
      v-model="pet.age" 
      label="Idade:" 
      placeholder="Idade, ainda que aproximada" 
      :required="true" 
      type="number"
    />

    <h3>Dados do Desaparecimento</h3>
    <label>Quem está reportando?</label>
    <select v-model="search.reporterRole" required>
      <option disabled value="">Selecione</option>
      <option value="TUTOR">Tutor</option>
      <option value="ADVERTISER">Anunciante</option>
    </select>

    <InputField 
      v-model="search.disappearanceDate" 
      label="Data e hora do desaparecimento:" 
      type="datetime-local"
      :required="true"
    />
    <InputField 
      v-model="search.location" 
      label="Última localização:" 
      placeholder="Visto pela última vez em..." 
      :required="true"
    />

    <label for="notes">Observações adicionais</label>
    <textarea id="notes" v-model="search.additionalNotes" placeholder="Detalhes extras"></textarea>

    <label for="photo">Foto do Pet:</label>
    <input id="photo" type="file" accept="image/*" @change="previewImage" required />

    <div v-if="preview" class="image-preview">
      <img :src="preview" alt="Prévia da imagem" />
    </div>

    <button type="submit">Cadastrar Anúncio</button>
  </form>
</template>
```

---

## ✅ 3. Estilização e melhorias futuras

Você pode ir além:

* Criar um `<TextareaField>` se quiser encapsular também `<textarea>`.
* Criar validações automáticas com `@blur`.
* Usar bibliotecas como [Vuelidate](https://vuelidate-next.netlify.app/) ou [vee-validate](https://vee-validate.logaretm.com/v4/) para validações robustas.
* Criar componentes para `SelectField` e `FileInput`.

---

## 📚 Referências e Leitura Recomendada

1. **Documentação oficial do Vue 3 - v-model:**
   [https://vuejs.org/guide/components/v-model.html](https://vuejs.org/guide/components/v-model.html)

2. **Vue School - Componentes com v-model (vídeo gratuito):**
   [https://vueschool.io/lessons/using-v-model-in-vue-3-components](https://vueschool.io/lessons/using-v-model-in-vue-3-components)

3. **Livro:** *Fullstack Vue 3: The Complete Guide to Vue.js 3*
   (Hassan Djirdeh et al.)

4. **Componente acessível de formulário (WAI-ARIA):**
   [https://developer.mozilla.org/en-US/docs/Web/Accessibility/ARIA/Roles/form\_role](https://developer.mozilla.org/en-US/docs/Web/Accessibility/ARIA/Roles/form_role)

---

Se quiser, posso continuar esse processo e te ajudar a extrair outros elementos (como `select`, `textarea`, `input type="file"`) para componentes reutilizáveis com base nesse mesmo padrão. Deseja isso?
