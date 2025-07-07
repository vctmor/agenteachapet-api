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

import { breedRandom, rolesRandon, dateNow, getRandomDogImageFile } from '@/utils/registrationUtils'

export async function exampleRegistration() {

  try {

    const role = rolesRandon()
    const date_now = dateNow()
    const [key, value] = breedRandom()

    const file = await getRandomDogImageFile(value)

    const res = await fetch('https://randomuser.me/api/')
    const data = await res.json()
    const user = data.results[0]

    personName.value = `${user.name.first} ${user.name.last}`
    phone.value = user.cell
    email.value= user.email
    reporterRole.value = role
    petName.value = user.login.password.charAt(0).toUpperCase() + user.login.password.slice(1)
    color.value = " "
    breed.value = key
    age.value = user.registered.age
    specialNeedDescription.value = ''
    disappearanceDate.value = date_now
    location.value = `${user.location.street.name}, ${user.location.street.number} - ${user.location.city}, ${user.location.state}`
    additionalNotes.value = ''

    console.log('File:', file)
    console.log('Preview:', URL.createObjectURL(file))
    image.value = file
    preview.value = URL.createObjectURL(file)


  } catch (error) {
    console.error('Erro ao buscar dados de exemplo:', error)
    alert('Erro ao preencher automaticamente com dados de exemplo.')

  }

}
