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

export async function exampleRegistration() {

  try {

    const res = await fetch('https://randomuser.me/api/')
    const data = await res.json()
    const user = data.results[0]

    personName.value = `${user.name.first} ${user.name.last}`
    phone.value = user.cell
    email.value= user.email
    reporterRole.value = 'TUTOR'
    petName.value = user.login.password
    color.value = "Cinza"
    breed.value = 'Boxer'
    age.value = user.registered.age
    specialNeedDescription.value = 'Precisa de cuidado'
    disappearanceDate.value =
    location.value = `${user.location.street.name}, ${user.location.street.number} - ${user.location.city}, ${user.location.state}`
    additionalNotes.value = 'Muito cerelepe'


  } catch (error) {
    console.error('Erro ao buscar dados de exemplo:', error)
    alert('Erro ao preencher automaticamente com dados de exemplo.')

  }

}
