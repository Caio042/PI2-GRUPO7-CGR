<template>
  <div class="relatorios">
    <img
      src="./assets/GLP.png"
      alt="CGR - Controle de Gestão para Revendas"
      class="logo"
    />
    <h1>Relatórios</h1>
    <div class="relatorio-card">
      <h2>Estoque</h2>
      <p>Produto: P13</p>
      <p>Quantidade: 150</p>
    </div>
    <div class="relatorio-card">
      <h2>Financeiro</h2>
      <p>Total em Vendas: R$ 10.000,00</p>
      <p>Total em Compras: R$ 7.500,00</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Relatorios',
}
</script>

<style scoped>
.relatorio-card {
  border: 1px solid #ddd;
  padding: 10px;
  margin: 10px 0;
  border-radius: 5px;
}
</style>
