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

    const [key, value] = breedRandom()
    const role = rolesRandon()
    const date_now = dateNow()


    const URL_DOGS = `https://dog.ceo/api/breed/${value}/images/random`

    const res = await fetch('https://randomuser.me/api/')
    const data = await res.json()
    const user = data.results[0]

    const res2 = await fetch(URL_DOGS)
    const data2 = await res2.json()
    const imageUrl = data2.message

    // converte a url em arquivo tipo File
    const blob = await fetch(imageUrl).then(r => r.blob())
    const fileName = `dog-${Date.now()}.jpg`
    const file = new File([blob], fileName, {type: blob.type})

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

    image.value = file
    preview.value = URL.createObjectURL(file)


  } catch (error) {
    console.error('Erro ao buscar dados de exemplo:', error)
    alert('Erro ao preencher automaticamente com dados de exemplo.')

  }

  function breedRandom(){

  const breedListJson =  {
  "affenpinscher": "affenpinscher",
  "african": "african",
  "airedale": "airedale",
  "akita": "akita",
  "appenzeller": "appenzeller",
  "kelpie australian": "australian-kelpie",
  "shepherd australian": "australian-shepherd",
  "indian bakharwal": "bakharwal-indian",
  "basenji": "basenji",
  "beagle": "beagle",
  "bluetick": "bluetick",
  "borzoi": "borzoi",
  "bouvier": "bouvier",
  "boxer": "boxer",
  "brabancon": "brabancon",
  "briard": "briard",
  "norwegian buhund": "buhund-norwegian",
  "boston bulldog": "bulldog-boston",
  "english bulldog": "bulldog-english",
  "french bulldog": "bulldog-french",
  "staffordshire bullterrier": "bullterrier-staffordshire",
  "australian cattledog": "cattledog-australian",
  "cavapoo": "cavapoo",
  "chihuahua": "chihuahua",
  "indian chippiparai": "chippiparai-indian",
  "chow": "chow",
  "clumber": "clumber",
  "cockapoo": "cockapoo",
  "border collie": "collie-border",
  "coonhound": "coonhound",
  "cardigan corgi": "corgi-cardigan",
  "cotondetulear": "cotondetulear",
  "dachshund": "dachshund",
  "dalmatian": "dalmatian",
  "great dane": "dane-great",
  "swedish danish": "danish-swedish",
  "scottish deerhound": "deerhound-scottish",
  "dhole": "dhole",
  "dingo": "dingo",
  "doberman": "doberman",
  "norwegian elkhound": "elkhound-norwegian",
  "entlebucher": "entlebucher",
  "eskimo": "eskimo",
  "lapphund finnish": "finnish-lapphund",
  "bichon frise": "frise-bichon",
  "indian gaddi": "gaddi-indian",
  "germanshepherd": "germanshepherd",
  "indian greyhound": "greyhound-indian",
  "italian greyhound": "greyhound-italian",
  "groenendael": "groenendael",
  "havanese": "havanese",
  "afghan hound": "hound-afghan",
  "basset hound": "hound-basset",
  "blood hound": "hound-blood",
  "english hound": "hound-english",
  "ibizan hound": "hound-ibizan",
  "plott hound": "hound-plott",
  "walker hound": "hound-walker",
  "husky": "husky",
  "keeshond": "keeshond",
  "kelpie": "kelpie",
  "kombai": "kombai",
  "komondor": "komondor",
  "kuvasz": "kuvasz",
  "labradoodle": "labradoodle",
  "labrador": "labrador",
  "leonberg": "leonberg",
  "lhasa": "lhasa",
  "malamute": "malamute",
  "malinois": "malinois",
  "maltese": "maltese",
  "bull mastiff": "mastiff-bull",
  "english mastiff": "mastiff-english",
  "indian mastiff": "mastiff-indian",
  "tibetan mastiff": "mastiff-tibetan",
  "mexicanhairless": "mexicanhairless",
  "mix": "mix",
  "bernese mountain": "mountain-bernese",
  "swiss mountain": "mountain-swiss",
  "indian mudhol": "mudhol-indian",
  "newfoundland": "newfoundland",
  "otterhound": "otterhound",
  "caucasian ovcharka": "ovcharka-caucasian",
  "papillon": "papillon",
  "indian pariah": "pariah-indian",
  "pekinese": "pekinese",
  "pembroke": "pembroke",
  "miniature pinscher": "pinscher-miniature",
  "pitbull": "pitbull",
  "german pointer": "pointer-german",
  "germanlonghair pointer": "pointer-germanlonghair",
  "pomeranian": "pomeranian",
  "medium poodle": "poodle-medium",
  "miniature poodle": "poodle-miniature",
  "standard poodle": "poodle-standard",
  "toy poodle": "poodle-toy",
  "pug": "pug",
  "puggle": "puggle",
  "pyrenees": "pyrenees",
  "indian rajapalayam": "rajapalayam-indian",
  "redbone": "redbone",
  "chesapeake retriever": "retriever-chesapeake",
  "curly retriever": "retriever-curly",
  "flatcoated retriever": "retriever-flatcoated",
  "golden retriever": "retriever-golden",
  "rhodesian ridgeback": "ridgeback-rhodesian",
  "rottweiler": "rottweiler",
  "saluki": "saluki",
  "samoyed": "samoyed",
  "schipperke": "schipperke",
  "giant schnauzer": "schnauzer-giant",
  "miniature schnauzer": "schnauzer-miniature",
  "italian segugio": "segugio-italian",
  "english setter": "setter-english",
  "gordon setter": "setter-gordon",
  "irish setter": "setter-irish",
  "sharpei": "sharpei",
  "english sheepdog": "sheepdog-english",
  "indian sheepdog": "sheepdog-indian",
  "shetland sheepdog": "sheepdog-shetland",
  "shiba": "shiba",
  "shihtzu": "shihtzu",
  "blenheim spaniel": "spaniel-blenheim",
  "brittany spaniel": "spaniel-brittany",
  "cocker spaniel": "spaniel-cocker",
  "irish spaniel": "spaniel-irish",
  "japanese spaniel": "spaniel-japanese",
  "sussex spaniel": "spaniel-sussex",
  "welsh spaniel": "spaniel-welsh",
  "indian spitz": "spitz-indian",
  "japanese spitz": "spitz-japanese",
  "english springer": "springer-english",
  "stbernard": "stbernard",
  "american terrier": "terrier-american",
  "australian terrier": "terrier-australian",
  "bedlington terrier": "terrier-bedlington",
  "border terrier": "terrier-border",
  "cairn terrier": "terrier-cairn",
  "dandie terrier": "terrier-dandie",
  "fox terrier": "terrier-fox",
  "irish terrier": "terrier-irish",
  "kerryblue terrier": "terrier-kerryblue",
  "lakeland terrier": "terrier-lakeland",
  "norfolk terrier": "terrier-norfolk",
  "norwich terrier": "terrier-norwich",
  "patterdale terrier": "terrier-patterdale",
  "russell terrier": "terrier-russell",
  "scottish terrier": "terrier-scottish",
  "sealyham terrier": "terrier-sealyham",
  "silky terrier": "terrier-silky",
  "tibetan terrier": "terrier-tibetan",
  "toy terrier": "terrier-toy",
  "welsh terrier": "terrier-welsh",
  "westhighland terrier": "terrier-westhighland",
  "wheaten terrier": "terrier-wheaten",
  "yorkshire terrier": "terrier-yorkshire",
  "tervuren": "tervuren",
  "vizsla": "vizsla",
  "spanish waterdog": "waterdog-spanish",
  "weimaraner": "weimaraner",
  "whippet": "whippet",
  "irish wolfhound": "wolfhound-irish"
}

  const entries = Object.entries(breedListJson)
  const index = Math.floor(Math.random() * entries.length)

  return entries[index]

  }

  function rolesRandon() {

    const roles = [
      { key: "TUTOR", label: "Tutor" },
      { key: "SENTINEL", label: "Sentinela" },
      { key: "RESCUER", label: "Guardião" }
    ]

    const index = Math.floor(Math.random() * roles.length)

    return roles[index].key
  }

  function dateNow() {

    const now = new Date()
    const pad = (n) => String(n).padStart(2, '0')

    return `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())}T${pad(now.getHours())}:${pad(now.getMinutes())}`
  }

}
