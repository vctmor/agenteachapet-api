<template>
  <div>
    <nav class="navbar">
      <div class="navbar-container">
        <RouterLink to="/" class="logo">AgenteAchaPet</RouterLink>
        <button class="toggle" @click="toggleMenu">
          ☰
        </button>
        <div :class="['links', { show: menuOpen }]">
          <RouterLink to="/">Registrar uma busca</RouterLink>
          <!-- <RouterLink to="`/cartaz/${id}`">Cartaz de Busca AtivA</RouterLink> -->
          <RouterLink to="/listar">Listar Buscas Ativas</RouterLink>
        </div>
      </div>
    </nav>

    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { ref, } from 'vue';
import { RouterLink, RouterView }  from 'vue-router'


const menuOpen = ref(false);


const toggleMenu = () => {

  menuOpen.value = !menuOpen.value;
};

</script>






<style>
/* Paleta Oxóssi */
:root {
  --verde-oxossi: #2e7d32;      /* verde floresta */
  --azul-oxossi: #0d47a1;       /* azul profundo */
  --dourado-oxossi: #c8b900;    /* dourado natural */
  --marrom-oxossi: #5d4037;     /* terra */
  --fundo-navbar: var(--verde-oxossi);
  --texto-navbar: #ffffff;
}

/* Navbar base */
.navbar {
  background-color: var(--fundo-navbar);
  padding: 1rem;
  color: var(--texto-navbar);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  position: sticky;
  top: 0;
  z-index: 10;
}

/* Container flexível */
.navbar-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* Links */
.links {
  display: flex;
  gap: 1rem;
}

.links a {
  color: var(--texto-navbar);
  text-decoration: none;
  font-weight: bold;
}

.links a:hover {
  text-decoration: underline;
}

/* Logo */
.logo {
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--texto-navbar);
}

/* Botão toggle (mobile) */
.toggle {
  display: none;
  font-size: 1.5rem;
  background: none;
  color: var(--texto-navbar);
  border: none;
  cursor: pointer;
}

/* Responsivo */
@media (max-width: 768px) {
  .links {
    display: none !important;
    flex-direction: column;
    gap: 0.5rem;
    margin-top: 1rem;
  }

  .links.show {
    display: flex !important;
  }

  .toggle {
    display: block;
  }
}


.content {
  padding: 1rem;
}
</style>
