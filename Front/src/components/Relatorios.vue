<template>
  <div class="relatorios">
    <h1>Relatórios</h1>

    <!-- Estoque -->
    <div class="relatorio-card" v-for="produto in relatorio.porProdutos" :key="produto.produto">
      <h2>Estoque</h2>
      <p>Produto: {{ produto.produto }}</p>
      <p>Quantidade: {{ produto.quantidade }}</p>
      <p>Total em Compras: R$ {{ produto.totalCompras }}</p>
      <p>Total em Vendas: R$ {{ produto.totalVendas }}</p>
    </div>

    <!-- Financeiro -->
    <div class="relatorio-card">
      <h2>Financeiro</h2>
      <p>Total em Vendas: R$ {{ totalVendas }}</p>
      <p>Total em Compras: R$ {{ totalCompras }}</p>
    </div>
  </div>
</template>

<script>
import api from '../axiosConfig';

export default {
  name: 'Relatorios',
  data() {
    return {
      relatorio: {
        porProdutos: [],
        totalVendas: 0,
        totalCompras: 0,
      },
    };
  },
  computed: {
    totalVendas() {
      return this.relatorio.totalVendas.toFixed(2);
    },
    totalCompras() {
      return this.relatorio.totalCompras.toFixed(2);
    },
  },
  methods: {
    async fetchRelatorio() {
      try {
        const response = await api.get('/relatorios', {
          headers: {
            Authorization: 'Basic ZnVsYW5vQGVtYWlsLmNvbToxMjM0',
            'Content-Type': 'application/json',
          },
        });
        this.relatorio = response.data;
      } catch (error) {
        console.error('Erro ao buscar o relatório:', error);
        alert('Erro ao buscar o relatório. Tente novamente.');
      }
    },
  },
  mounted() {
    this.fetchRelatorio();
  },
};
</script>

<style scoped>
.relatorio-card {
  border: 1px solid #ddd;
  padding: 10px;
  margin: 10px 0;
  border-radius: 5px;
}
</style>
