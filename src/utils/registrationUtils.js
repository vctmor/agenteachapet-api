

export async function getRandomDogImageFile(breedValue, tries = 0) {


  if (tries > 5) {
    throw new Error('Falhou após múltiplas tentativas de obter imagem real.')
  }

  const URL_DOGS = `https://dog.ceo/api/breed/${breedValue}/images/random`

  try {
    const res = await fetch(URL_DOGS)
    const data = await res.json()
    const imageUrl = data.message

    if (!imageUrl) {
      console.warn('Nenhuma imagem retornada para ${breedValue}. Tentando outra raça...')

      const[newKey, newValue] = breedRandom()
      return await getRandomDogImageFile(newValue, tries + 1)
    }

    const blobResponse = await fetch(imageUrl)

    if (!blobResponse.ok) {
      console.warn('Falha ao baixar imagem. Tentando novamente...')

      const [newKey, newValue] = breedRandom()
      return await getRandomDogImageFile(newValue, tries + 1)
    }

    const blob = await blobResponse.blob()

    console.log('Blob type:', blob.type)

    if (!blob || blob.size === 0 || !blob.type.startsWith('image/')) {
      console.warn('Blob inválido ou não é imagem. Tentando novamente...')

      const [newKey, newValue] = breedRandom()
      return await getRandomDogImageFile(newValue, tries + 1)

    }

    const fileName = `dog-${Date.now()}.jpg`
    return new File([blob], fileName, { type: blob.type })

  } catch (error) {
    console.error('Erro ao obter imagem de cachorro:', error)

    const [newKey, newValue] = breedRandom()
    return await getRandomDogImageFile(newValue, tries + 1)
  }
}


export function dateNow() {

  const now = new Date()
  const pad = (n) => String(n).padStart(2, '0')

  return `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())}T${pad(now.getHours())}:${pad(now.getMinutes())}`
}

export function rolesRandon() {

  const roles = [
    { key: "TUTOR", label: "Tutor" },
    { key: "SENTINEL", label: "Sentinela" },
    { key: "RESCUER", label: "Guardião" }
  ]

  const index = Math.floor(Math.random() * roles.length)

  return roles[index].key
}

export function breedRandom() {

  const breedListJson = {
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
