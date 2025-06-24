import { ref } from 'vue'

const register = ref(JSON.parse(localStorage.getItem('register') || '[]'))

const save = (newItem) => {

  newItem.id = Date.now()
  register.value.push(newItem)
  localStorage.setItem('register', JSON.stringify(register.value))

  return newItem.id
}


const listing = () => register.value

const findById = (id) =>
  register.value.find((c) => String(c.id) === String(id))

const remove = (id) => {

  register.value = register.value.filter((c) => c.id !== id)
  localStorage.setItem('register', JSON.stringify(register.value))
}

export function useRegister() {

  return { save, listing, findById, remove }
}
