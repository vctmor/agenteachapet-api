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
