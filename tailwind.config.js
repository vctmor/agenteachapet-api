/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}"
  ],
  darkMode: false, // ou 'media' ou 'class', conforme sua preferência
  theme: {
    extend: {
      // aqui você pode estender cores, fontes, etc
    },
  },
  plugins: [],
}
