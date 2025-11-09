// src/composables/useFormReset.js
export function useFormReset(fields) {
  function resetForm() {
    for (const key in fields) {
      if (Object.prototype.hasOwnProperty.call(fields, key)) {
        // Se for uma ref com tipo primitivo
        if (typeof fields[key].value !== 'undefined') {
          if (typeof fields[key].value === 'string') {
            fields[key].value = ''
          } else if (typeof fields[key].value === 'number') {
            fields[key].value = ""
          } else if (typeof fields[key].value === 'boolean') {
            fields[key].value = false
          } else {
            fields[key].value = null
          }
        }
      }
    }
  }

  return { resetForm }
}
