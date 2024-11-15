<template>
  <div class="menu-principal">
    <h1>Bem-vindo!</h1>
    <div class="menu-buttons">
      <router-link to="/estoques" class="button"
        >Cadastrar Compra</router-link
      >
      <router-link to="/cadastro-venda" class="button"
        >Cadastrar Venda</router-link
      >
      <router-link to="/relatorios" class="button">Relatórios</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MenuPrincipal',
}
</script>

<style scoped>
.menu-principal {
  text-align: center;
  margin-top: 20px;
}
.menu-buttons .button {
  display: inline-block;
  margin: 10px;
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  text-decoration: none;
  border-radius: 5px;
}
.menu-buttons .button:hover {
  background-color: #45a049;
}
</style>
